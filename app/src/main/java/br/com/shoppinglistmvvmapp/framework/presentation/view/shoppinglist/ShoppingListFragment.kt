package br.com.shoppinglistmvvmapp.framework.presentation.view.shoppinglist

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.shoppinglistmvvmapp.R
import br.com.shoppinglistmvvmapp.databinding.ShoppingListLayoutBinding
import br.com.shoppinglistmvvmapp.domain.model.ShoppingList
import br.com.shoppinglistmvvmapp.framework.presentation.view.common.fragment.AbstractCollectionFragment
import br.com.shoppinglistmvvmapp.framework.presentation.view.shoppinglist.model.ShoppingListPresentation
import br.com.shoppinglistmvvmapp.framework.presentation.view.shoppinglist.state.ShoppingListViewState
import br.com.shoppinglistmvvmapp.framework.presentation.view.util.extension.safeRunOnUiThread
import br.com.shoppinglistmvvmapp.framework.presentation.view.util.extension.setEmptyList
import br.com.shoppinglistmvvmapp.framework.util.enum.ActionType
import br.com.shoppinglistmvvmapp.framework.util.interfaces.ShoppingFragmentListClickHandler
import br.com.shoppinglistmvvmapp.framework.util.recognition.RecognitionParams
import br.com.shoppinglistmvvmapp.framework.util.recognition.event.RecognitionOnErrorEvent
import br.com.shoppinglistmvvmapp.framework.util.recognition.event.RecognitionOnResultEvent
import br.com.shoppinglistmvvmapp.utils.extension.yesAnswer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShoppingListFragment : AbstractCollectionFragment<ShoppingListLayoutBinding>(
    R.layout.shopping_list_layout
), ShoppingFragmentListClickHandler{

    private val viewModel: ShoppingListViewModel by viewModel()

    private val adapter: ShoppingListAdapter by lazy {
        ShoppingListAdapter(this)
    }

    override fun initAdapter(){
        binding.shoppingListRecyclerView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onObserveShoppingListState()
    }

    private fun onObserveShoppingListState(){
        viewModel.shoppingListViewState.observe(viewLifecycleOwner, Observer { state ->
            when(state){
                is ShoppingListViewState.Loading -> isRefreshing()
                is ShoppingListViewState.Error -> {
                    isRefreshing(false)
                }
                is ShoppingListViewState.Success -> {
                    addListInAdapter(state.shoppingListPresentationList)
                }
            }
        })
    }

    private fun navigateToItemsShoppingListFragment(shoppingListId: String){
        safeRunOnUiThread {
            findNavController().navigate(
                ShoppingListFragmentDirections.actionShoppingListFragmentToItemShoppingListFragment(
                    shoppingListId
                )
            )
        }
    }

    //TODO CHANGE THAT! -> Put on Observable properties and put in value in layout and BindingAdapter the logic
    private fun isRefreshing(isRefresh: Boolean = true){
        safeRunOnUiThread {
            binding.shoppingListSwipeRefresh.isRefreshing = isRefresh
        }
    }

    //TODO CHANGE THAT! -> Put in AbstractClass?
    private fun addListInAdapter(newList: List<ShoppingListPresentation>){
        safeRunOnUiThread(
            onRun = {
                adapter.clear()
                adapter.addAll(newList)
                binding.shoppingListEmptyList.emptyList.text = getString(R.string.shopping_list_empty_list)
            },
            onCompletion = {
                binding.shoppingListEmptyList.emptyList.setEmptyList(adapter.itemCount)
                isRefreshing(false)
            }
        )
    }

    //TODO CHANGE THAT! -> Insert States
    override fun onRecognitionOnResultEvent(event: RecognitionOnResultEvent) {
        with(event) {
            if (recognitionParams?.actionsType == ActionType.REDIRECT_TO_ITEM_SHOPPING_LIST) {
                if (bestResult.yesAnswer()) {
                    navigateToItemsShoppingListFragment(recognitionParams.currentShoppingListId)
                }
                speakOk()
            } else {
                val shoppingList = viewModel.createShoppingList(bestResult)
                addNewItemListInAdapter(shoppingList)

                val params =
                    RecognitionParams(
                        ActionType.REDIRECT_TO_ITEM_SHOPPING_LIST,
                        shoppingList.id
                    )
                speakOkAndMore(
                    R.string.shopping_list_redirect_to_item_shopping_list,
                    onSpeakDone = { startRecognition(params) }
                )
            }
        }
    }

    //TODO CHANGE THAT! -> CHANGE, CHANGE, PLS CHANGE! XD
    override fun onPause() {
        sendShoppingList()
        super.onPause()
    }

    //TODO CHANGE THAT! -> PUT ON CREATE/DELETE/UPDATE LIST STATE AND ADD IN WORKER!
    private fun sendShoppingList(){
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.sendShoppingList()
        }
    }

    //TODO CHANGE THAT! -> Put on another layer
    override fun onClickEditItemList(shoppingList: ShoppingList) {
        viewUtil.editMessage(
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
    }

    private fun addNewItemListInAdapter(shoppingList: ShoppingList){
        viewModel.add(shoppingList)
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

    override fun onClickDeleteItemList(shoppingList: ShoppingList) {
        viewUtil.yesConfirmMessage(
            resStringMessage = R.string.shopping_list_delete_item,
            onYesClick = {
                viewModel.deleteItem(shoppingList)
            }
        )
    }

    override fun onClickItemList(shoppingListId: String) {
        navigateToItemsShoppingListFragment(shoppingListId)
    }

    override fun onRecognitionOnErrorEvent(event: RecognitionOnErrorEvent) {
        speak(event.errorMessageStringRes)
    }

    override fun initBindingProperties() {
        binding.vm = viewModel
    }

}
