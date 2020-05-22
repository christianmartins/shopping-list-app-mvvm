package br.com.shoppinglistmvvmapp.framework.presentation.view.viewholder

import br.com.shoppinglistmvvmapp.databinding.ShoppingListViewHolderMvvmLayoutBinding
import br.com.shoppinglistmvvmapp.framework.presentation.model.ShoppingListPresentation
import br.com.shoppinglistmvvmapp.framework.util.interfaces.ShoppingFragmentListClickHandler

class ShoppingListViewHolderMVVM(
    private val itemBinding: ShoppingListViewHolderMvvmLayoutBinding,
    private val clickHandler: ShoppingFragmentListClickHandler
): AbstractViewHolder<ShoppingListPresentation>(itemBinding.root) {

    override fun setItem(presentation: ShoppingListPresentation) {
        itemBinding.shoppingListPresentation = presentation
        itemBinding.executePendingBindings()

        itemView.setOnClickListener {
            clickHandler.onClickItemList(presentation.id)
        }
    }
}