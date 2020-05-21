package br.com.shoppinglistmvvmapp.framework.presentation.view.viewholder

import br.com.shoppinglistmvvmapp.databinding.ShoppingListViewHolderMvvmLayoutBinding
import br.com.shoppinglistmvvmapp.framework.presentation.converter.ShoppingListPresentation
import br.com.shoppinglistmvvmapp.framework.util.interfaces.ShoppingFragmentListClickHandler

class ShoppingListViewHolderMVVM(
    private val itemBinding: ShoppingListViewHolderMvvmLayoutBinding,
    private val clickHandler: ShoppingFragmentListClickHandler
): BaseViewHolderMVVM<ShoppingListPresentation>(itemBinding.root) {

    override fun setItem(presentation: ShoppingListPresentation) {
        itemBinding.shoppingListPresentation = presentation
        itemBinding.executePendingBindings()

        itemView.setOnClickListener {
            clickHandler.onClickItemList(presentation.id)
        }
    }
}