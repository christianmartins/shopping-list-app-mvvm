package br.com.shoppinglistmvvmapp.presentation.view.viewholder

import br.com.shoppinglistmvvmapp.databinding.ShoppingListViewHolderMvvmLayoutBinding
import br.com.shoppinglistmvvmapp.presentation.converter.Presentation
import br.com.shoppinglistmvvmapp.presentation.converter.ShoppingListPresentation
import br.com.shoppinglistmvvmapp.utils.interfaces.ShoppingFragmentListClickHandler

class ShoppingListViewHolderMVVM(
    private val itemBinding: ShoppingListViewHolderMvvmLayoutBinding,
    private val clickHandler: ShoppingFragmentListClickHandler
): BaseViewHolderMVVM(itemBinding.root) {

    override fun setItem(presentation: Presentation) {
        itemBinding
        if(presentation is ShoppingListPresentation) {
            itemBinding.shoppingListPresentation?.let {
                with(it) {
                    authorName = presentation.authorName
                    title = presentation.title
                    description = presentation.description
                    authorName = presentation.authorName
                    date = presentation.date
                    itemsForConclusion = presentation.itemsForConclusion
                }
            }
            itemBinding.executePendingBindings()

            itemView.setOnClickListener {
                clickHandler.onClickItemList(presentation.id)
            }
        }
    }
}