package br.com.shoppinglistmvvmapp.framework.presentation.view.shoppinglist

import br.com.shoppinglistmvvmapp.databinding.ShoppingListViewHolderMvvmLayoutBinding
import br.com.shoppinglistmvvmapp.framework.presentation.view.common.AbstractViewHolder
import br.com.shoppinglistmvvmapp.framework.presentation.view.shoppinglist.model.ShoppingListPresentation
import br.com.shoppinglistmvvmapp.framework.util.interfaces.ShoppingFragmentListClickHandler

class ShoppingListViewHolder(
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