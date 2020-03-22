package br.com.shoppinglistmvvmapp.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.shoppinglistmvvmapp.R
import br.com.shoppinglistmvvmapp.data.model.ShoppingList
import br.com.shoppinglistmvvmapp.extensions.setEmptyList
import br.com.shoppinglistmvvmapp.extensions.yesAnswer
import br.com.shoppinglistmvvmapp.presenter.ShoppingListFragmentPresenter
import br.com.shoppinglistmvvmapp.utils.GlobalUtils
import br.com.shoppinglistmvvmapp.utils.LoggedUser
import br.com.shoppinglistmvvmapp.utils.RecognitionParams
import br.com.shoppinglistmvvmapp.utils.enum.ActionType
import br.com.shoppinglistmvvmapp.utils.event.RecognitionOnErrorEvent
import br.com.shoppinglistmvvmapp.utils.event.RecognitionOnResultEvent
import br.com.shoppinglistmvvmapp.utils.interfaces.ShoppingFragmentListClickHandler
import br.com.shoppinglistmvvmapp.presentation.view.adapter.ShoppingListAdapterMVVM
import kotlinx.android.synthetic.main._empty_list_layout.*
import kotlinx.android.synthetic.main.shopping_list_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class ShoppingListFragmentMVVM: BaseCollectionFragment(), ShoppingFragmentListClickHandler{

    private val presenter by lazy { ShoppingListFragmentPresenter() }

    private val adapter by lazy { ShoppingListAdapterMVVM(this) }

    private var jobRefresh: Job? = null

    private fun isRefreshing(isRefresh: Boolean){
        activity?.runOnUiThread {shopping_list_swipe_refresh?.isRefreshing = isRefresh}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        getFab()?.setImageResource(R.drawable.ic_add_white_24dp)
        return inflater.inflate(R.layout.shopping_list_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initDataShoppingList()
        onRefreshListener()
        marginInRecyclerView()
    }

    private fun initDataShoppingList(){
        if(LoggedUser.isLogged)
            lifecycleScope.launch(Dispatchers.IO){ loadListAsync() }
        else
            loadList()
    }

    private suspend fun loadListAsync(){
        isRefreshing(true)
        presenter.loadListByUser()
        loadList()
    }

    private fun loadList(){
        activity?.runOnUiThread {
            try{
                adapter.clear()
                adapter.addAll(presenter.getOrderedItems())
                empty_list.text = getString(R.string.shopping_list_empty_list)
                empty_list.setEmptyList(adapter.itemCount)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
        isRefreshing(false)
    }

    private fun onRefreshListener(){
        activity?.runOnUiThread {
            shopping_list_swipe_refresh?.setOnRefreshListener {
                if(jobRefresh?.isActive == true)
                    jobRefresh?.cancel()

                jobRefresh = lifecycleScope.launch(Dispatchers.IO) {
                    refresh()
                }
            }
        }
    }

    private suspend fun refresh(){
        presenter.sendShoppingList()
        loadListAsync()
    }

    override fun initAdapter(){
        shopping_list_recycler_view?.adapter = adapter
    }

    override fun onRecognitionOnErrorEvent(event: RecognitionOnErrorEvent) {
        speak(event.errorMessageStringRes)
    }

    override fun onRecognitionOnResultEvent(event: RecognitionOnResultEvent) {
        with(event) {
            if (recognitionParams?.actionsType == ActionType.REDIRECT_TO_ITEM_SHOPPING_LIST) {
                if (bestResult.yesAnswer()) {
                    navigateToItemsShoppingListFragment(recognitionParams.currentShoppingListId)
                }
                speakOk()
            } else {
                val shoppingList = presenter.createShoppingList(bestResult)
                addItemInAdapter(shoppingList)

                val params = RecognitionParams(ActionType.REDIRECT_TO_ITEM_SHOPPING_LIST, shoppingList.id)
                speakOkAndMore(
                    R.string.shopping_list_redirect_to_item_shopping_list,
                    onSpeakDone = { startRecognition(params) }
                )
            }
        }
    }

    private fun addItemInAdapter(shoppingList: ShoppingList){
        GlobalUtils.shoppingLists.add(shoppingList)
        adapter.add(shoppingList)
        empty_list.setEmptyList(adapter.itemCount)
    }

    override fun onClickFloatingButton(){
        stopAll()
        speak(
            R.string.text_to_speech_title_shopping_list,
            onSpeakDone = {
                startRecognition()
            }
        )
    }

    private fun navigateToItemsShoppingListFragment(shoppingListId: String){
        activity?.runOnUiThread {
            findNavController().navigate(
                ShoppingListFragmentDirections.actionShoppingListFragmentToItemShoppingListFragment(shoppingListId)
            )
        }
    }

    override fun onClickItemList(shoppingListId: String) {
        navigateToItemsShoppingListFragment(shoppingListId)
    }

    override fun onClickDeleteItemList(shoppingList: ShoppingList) {
        context?.let {
            yesConfirmMessage(
                resStringMessage = R.string.shopping_list_delete_item,
                onYesClick = {
                    presenter.deleteItem(shoppingList)
                    loadList()
                }
            )
        }
    }

    override fun onClickEditItemList(shoppingList: ShoppingList) {
        editMessage(
            resStringTitle = R.string.shopping_list_edit_item,
            resStringHint = R.string.shopping_list_hint_dialog,
            text = shoppingList.title,
            onYesClick = {newValue ->
                updateTitle(newValue, shoppingList)
            }
        )
    }

    private fun updateTitle(newValue: String, shoppingList: ShoppingList){
        presenter.updateTitle(newValue, shoppingList)
        adapter.notifyDataSetChanged()
    }


    override fun onPause() {
        sendShoppingList()
        super.onPause()
    }

    private fun sendShoppingList(){
        lifecycleScope.launch(Dispatchers.IO) {
            presenter.sendShoppingList()
        }
    }

    private fun marginInRecyclerView(){
        shopping_list_recycler_view?.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                shopping_list_swipe_refresh?.let {
                    val totalItemCount = recyclerView.layoutManager?.itemCount?: 0
                    val lastVisibleItem = (recyclerView.layoutManager as? LinearLayoutManager)?.findLastCompletelyVisibleItemPosition()?: 0
                    val marginLayoutParams = FrameLayout.LayoutParams(it.layoutParams)

                    val text = "->> totalItemCount = $totalItemCount -- lastVisibleItem = $lastVisibleItem}"

                    val textTest: String
                    if ((lastVisibleItem + 2) >= totalItemCount) {
                        if(newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                            marginLayoutParams.bottomMargin = 80
                            it.layoutParams = marginLayoutParams

                            textTest = "Apply margin -> "
                            println(textTest + text)
                        }
                    }else {
                        if (newState == RecyclerView.SCROLL_STATE_IDLE){
                            marginLayoutParams.bottomMargin = 0
                            it.layoutParams = marginLayoutParams

                            textTest = "Remove margin"
                            println(textTest + text)
                        }
                    }
                }
            }
        })
    }
}
