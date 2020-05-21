package br.com.shoppinglistmvvmapp.framework.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.shoppinglistmvvmapp.R
import br.com.shoppinglistmvvmapp.data.model.ShoppingList
import br.com.shoppinglistmvvmapp.databinding.ShoppingListMvvmLayoutBinding
import br.com.shoppinglistmvvmapp.framework.presentation.view.util.extension.setEmptyList
import br.com.shoppinglistmvvmapp.utils.extension.yesAnswer
import br.com.shoppinglistmvvmapp.framework.presentation.view.adapter.ShoppingListAdapterMVVM
import br.com.shoppinglistmvvmapp.framework.presentation.viewmodel.ShoppingListFragmentViewModel
import br.com.shoppinglistmvvmapp.utils.GlobalUtils
import br.com.shoppinglistmvvmapp.utils.RecognitionParams
import br.com.shoppinglistmvvmapp.framework.util.enum.ActionType
import br.com.shoppinglistmvvmapp.framework.util.event.RecognitionOnErrorEvent
import br.com.shoppinglistmvvmapp.framework.util.event.RecognitionOnResultEvent
import br.com.shoppinglistmvvmapp.framework.util.interfaces.ShoppingFragmentListClickHandler
import kotlinx.android.synthetic.main._empty_list_layout.*
import kotlinx.android.synthetic.main.shopping_list_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class ShoppingListFragmentMVVM: BaseCollectionFragment(), ShoppingFragmentListClickHandler{

    private val viewModel by lazy {
        ViewModelProvider(this).get(ShoppingListFragmentViewModel::class.java)
    }

    private val adapter by lazy { ShoppingListAdapterMVVM(this) }

    private var jobRefresh: Job? = null

    private lateinit var binding: ShoppingListMvvmLayoutBinding

    private fun isRefreshing(isRefresh: Boolean){
        activity?.runOnUiThread {shopping_list_swipe_refresh?.isRefreshing = isRefresh}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = getFragmentBinding(inflater, container)
        getFab()?.setImageResource(R.drawable.ic_add_white_24dp)
        return binding.root
    }

    private fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ShoppingListMvvmLayoutBinding{
         return ShoppingListMvvmLayoutBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initViewModel()
        onRefreshListener()
    }

    override fun initAdapter(){
        binding.shoppingListRecyclerView.adapter = adapter
    }

    private fun initViewModel(){
        viewModel.getShoppingLists().observe(viewLifecycleOwner, Observer { shoppingLists ->
            loadList(shoppingLists)
        })
    }

    private suspend fun loadListAsync(){
        isRefreshing(true)
        viewModel.fetchShoppingListsByUser()
        loadList()
    }

    private fun loadList(newList: List<ShoppingList> = viewModel.getOrderedItems()){
        activity?.runOnUiThread {
            try{
                adapter.clear()
                adapter.addAll(newList)
                empty_list.text = getString(R.string.shopping_list_empty_list)
                empty_list.setEmptyList(adapter.itemCount)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                isRefreshing(false)
            }
        }
    }

    private fun onRefreshListener(){
        activity?.runOnUiThread {
            binding.shoppingListSwipeRefresh.setOnRefreshListener {
                if(jobRefresh?.isActive == true)
                    jobRefresh?.cancel()

                jobRefresh = lifecycleScope.launch(Dispatchers.IO) {
                    refresh()
                }
            }
        }
    }

    private suspend fun refresh(){
        viewModel.sendShoppingList()
        loadListAsync()
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
                val shoppingList = viewModel.createShoppingList(bestResult)
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
//        activity?.runOnUiThread {
//            findNavController().navigate(
//                ShoppingListFragmentMVVMDirections.actionShoppingListFragmentToItemShoppingListFragment(
//                    shoppingListId
//                )
//            )
//        }
    }

    override fun onClickItemList(shoppingListId: String) {
        navigateToItemsShoppingListFragment(shoppingListId)
    }

    override fun onClickDeleteItemList(shoppingList: ShoppingList) {
        context?.let {
            yesConfirmMessage(
                resStringMessage = R.string.shopping_list_delete_item,
                onYesClick = {
                    viewModel.deleteItem(shoppingList)
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
        viewModel.updateTitle(newValue, shoppingList)
        adapter.notifyDataSetChanged()
    }

    override fun onPause() {
        sendShoppingList()
        super.onPause()
    }

    private fun sendShoppingList(){
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.sendShoppingList()
        }
    }
}
