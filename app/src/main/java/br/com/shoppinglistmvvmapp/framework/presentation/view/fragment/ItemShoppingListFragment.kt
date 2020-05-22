package br.com.shoppinglistmvvmapp.framework.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import br.com.shoppinglistmvvmapp.R
import br.com.shoppinglistmvvmapp.data.model.ItemShoppingList
import br.com.shoppinglistmvvmapp.framework.presentation.view.util.extension.setEmptyList
import br.com.shoppinglistmvvmapp.framework.presentation.view.old.presenter.ItemShoppingListFragmentPresenter
import br.com.shoppinglistmvvmapp.utils.LoggedUser
import br.com.shoppinglistmvvmapp.framework.util.event.RecognitionOnErrorEvent
import br.com.shoppinglistmvvmapp.framework.util.event.RecognitionOnResultEvent
import br.com.shoppinglistmvvmapp.framework.util.interfaces.ItemShoppingListListeners
import br.com.shoppinglistmvvmapp.framework.presentation.view.adapter.ItemShoppingListAdapter
import br.com.shoppinglistmvvmapp.framework.presentation.view.dialog.RecognitionExplainDialog
import kotlinx.android.synthetic.main._empty_list_layout.*
import kotlinx.android.synthetic.main.item_shopping_list_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ItemShoppingListFragment: AbstractCollectionFragment(), ItemShoppingListListeners {

    private val presenter by lazy {
        ItemShoppingListFragmentPresenter()
    }

    private val adapter by lazy {
        ItemShoppingListAdapter(this)
    }

    private val currentShoppingListId by lazy {
        arguments?.getString("shoppingListId")?: ""
    }

    private var dialogExplainRecognition: RecognitionExplainDialog?  = null

    private var jobRefresh: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getFab()?.setImageResource(R.drawable.ic_mic_black_24dp)
        return inflater.inflate(R.layout.item_shopping_list_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        context?.let {
            dialogExplainRecognition = RecognitionExplainDialog(it, R.layout.recognition_item_shopping_list_layout)
        }
        onRefreshListener()
    }

    override fun initAdapter(){
        item_shopping_list_recycler_view?.adapter = adapter
        initEmptyList()
        initDataShoppingList()
    }

    private fun initDataShoppingList(){
        if(LoggedUser.isLogged)
            lifecycleScope.launch(Dispatchers.IO){ loadListAsync() }
        else
            loadList()
    }

    private suspend fun loadListAsync(){
        isRefreshing(true)
        presenter.loadItemsShoppingListByShoppingListId(currentShoppingListId)
        loadList()
    }

    private fun loadList() {
        activity?.runOnUiThread {
            val list = presenter.getOrderedItems(currentShoppingListId)
            adapter.clear()
            adapter.addAll(list)
            thisListIsEmpty()
            isRefreshing(false)
        }
    }

    private fun initEmptyList(){
        empty_list.text = getString(R.string.item_shopping_list_empty_list)
    }

    private fun thisListIsEmpty(){
        empty_list.setEmptyList(adapter.itemCount)
    }

    private fun onRefreshListener(){
        activity?.runOnUiThread {
            item_shopping_list_swipe_refresh?.setOnRefreshListener {
                if(jobRefresh?.isActive == true)
                    jobRefresh?.cancel()

                jobRefresh = lifecycleScope.launch(Dispatchers.IO) {
                    refresh()
                }
            }
        }
    }

    private fun isRefreshing(isRefresh: Boolean){
        activity?.runOnUiThread {item_shopping_list_swipe_refresh?.isRefreshing = isRefresh}
    }

    private suspend fun refresh(){
        presenter.sendItemsShoppingList(currentShoppingListId)
        loadListAsync()
    }


    override fun deleteItem(item: ItemShoppingList) {
        presenter.markToDeleteItem(item)
        loadList()
        updateTotalItemsToCompleteShoppingList()
    }

    override fun onSelectedItem(item: ItemShoppingList) {
        presenter.updateSelectedItem(item, currentShoppingListId)
        loadList()
    }

    override fun onClickFloatingButton() {
        stopAll()
        dialogExplainRecognition?.show()
        speak(
            R.string.speak_item_shopping_add_item,
            onSpeakDone = {
                startRecognition()
            }
        )
    }

    private fun updateTotalItemsToCompleteShoppingList(){
        presenter.updateShoppingListTotalItems(currentShoppingListId, adapter.itemCount)
        thisListIsEmpty()
    }

    override fun onRecognitionOnResultEvent(event: RecognitionOnResultEvent) {
        hideDialog()
        val results = event.bestResult.split(" e ", ignoreCase = true)
        results.forEach {
            val itemShoppingList = presenter.getData(shoppingListId = currentShoppingListId).copy(
                description = it
            )
            presenter.add(itemShoppingList)
            loadList()
        }
        updateTotalItemsToCompleteShoppingList()
    }

    override fun onRecognitionOnErrorEvent(event: RecognitionOnErrorEvent) {
        hideDialog()
        speak(event.errorMessageStringRes)
    }

    private fun hideDialog(){
        activity?.runOnUiThread {
            dialogExplainRecognition?.dismiss()
        }
    }

    override fun onPause() {
        sendItemsShoppingList()
        super.onPause()
    }

    private fun sendItemsShoppingList(){
        activity?.lifecycleScope?.launch(Dispatchers.IO) {
            presenter.sendItemsShoppingList(currentShoppingListId)
        }
    }
}