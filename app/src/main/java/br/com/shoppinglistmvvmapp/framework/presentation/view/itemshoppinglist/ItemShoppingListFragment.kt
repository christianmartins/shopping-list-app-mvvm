package br.com.shoppinglistmvvmapp.framework.presentation.view.itemshoppinglist

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import br.com.shoppinglistmvvmapp.R
import br.com.shoppinglistmvvmapp.databinding.ItemShoppingListLayoutBinding
import br.com.shoppinglistmvvmapp.domain.model.ItemShoppingList
import br.com.shoppinglistmvvmapp.framework.presentation.view.common.fragment.AbstractCollectionFragment
import br.com.shoppinglistmvvmapp.framework.presentation.view.recognitionexplain.RecognitionExplainDialog
import br.com.shoppinglistmvvmapp.framework.presentation.view.util.extension.safeRunOnUiThread
import br.com.shoppinglistmvvmapp.framework.presentation.view.util.extension.setEmptyList
import br.com.shoppinglistmvvmapp.framework.util.interfaces.ItemShoppingListListeners
import br.com.shoppinglistmvvmapp.framework.util.recognition.event.RecognitionOnErrorEvent
import br.com.shoppinglistmvvmapp.framework.util.recognition.event.RecognitionOnResultEvent
import br.com.shoppinglistmvvmapp.utils.LoggedUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ItemShoppingListFragment: AbstractCollectionFragment<ItemShoppingListLayoutBinding>(
        R.layout.shopping_list_layout
), ItemShoppingListListeners {

    private val viewModel: ItemShoppingListViewModel by viewModel()

    private val adapter by lazy {
        ItemShoppingListAdapter(
            this
        )
    }

    private val currentShoppingListId by lazy {
        arguments?.getString("shoppingListId")?: ""
    }

    private var dialogExplainRecognition: RecognitionExplainDialog?  = null

    private var jobRefresh: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFab()?.setImageResource(R.drawable.ic_mic_black_24dp)

        initAdapter()
        context?.let {
            dialogExplainRecognition =
                RecognitionExplainDialog(
                    it,
                    R.layout.recognition_item_shopping_list_layout
                )
        }
        onRefreshListener()
    }

    override fun initAdapter(){
        binding.itemShoppingListRecyclerView.adapter = adapter
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
        viewModel.loadItemsShoppingListByShoppingListId(currentShoppingListId)
        loadList()
    }

    private fun loadList() {
        activity?.runOnUiThread {
            val list = viewModel.getOrderedItems(currentShoppingListId)
            adapter.clear()
            adapter.addAll(list)
            thisListIsEmpty()
            isRefreshing(false)
        }
    }

    private fun initEmptyList(){
        binding.itemShoppingListEmptyList.emptyList.text = getString(R.string.item_shopping_list_empty_list)
    }

    private fun thisListIsEmpty(){
        binding.itemShoppingListEmptyList.emptyList.setEmptyList(adapter.itemCount)
    }

    private fun onRefreshListener(){
        activity?.runOnUiThread {
            binding.itemShoppingListSwipeRefresh.setOnRefreshListener {
                if(jobRefresh?.isActive == true)
                    jobRefresh?.cancel()

                jobRefresh = lifecycleScope.launch(Dispatchers.IO) {
                    refresh()
                }
            }
        }
    }

    private fun isRefreshing(isRefresh: Boolean){
        safeRunOnUiThread {binding.itemShoppingListSwipeRefresh.isRefreshing = isRefresh}
    }

    private suspend fun refresh(){
        viewModel.sendItemsShoppingList(currentShoppingListId)
        loadListAsync()
    }


    override fun deleteItem(item: ItemShoppingList) {
        viewModel.markToDeleteItem(item)
        loadList()
        updateTotalItemsToCompleteShoppingList()
    }

    override fun onSelectedItem(item: ItemShoppingList) {
        viewModel.updateSelectedItem(item, currentShoppingListId)
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
        viewModel.updateShoppingListTotalItems(currentShoppingListId, adapter.itemCount)
        thisListIsEmpty()
    }

    override fun onRecognitionOnResultEvent(event: RecognitionOnResultEvent) {
        hideDialog()
        val results = event.bestResult.split(" e ", ignoreCase = true)
        results.forEach {
            val itemShoppingList = viewModel.getData(shoppingListId = currentShoppingListId).copy(
                description = it
            )
            viewModel.add(itemShoppingList)
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
            viewModel.sendItemsShoppingList(currentShoppingListId)
        }
    }

    override fun initBindingProperties() {

    }
}