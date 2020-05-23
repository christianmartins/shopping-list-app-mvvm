package br.com.shoppinglistmvvmapp.framework.presentation.view.adapter

import android.view.ViewGroup
import br.com.shoppinglistmvvmapp.R
import br.com.shoppinglistmvvmapp.databinding.ShoppingListViewHolderMvvmLayoutBinding
import br.com.shoppinglistmvvmapp.framework.presentation.model.ShoppingListPresentation
import br.com.shoppinglistmvvmapp.framework.presentation.view.viewholder.ShoppingListViewHolderMVVM
import br.com.shoppinglistmvvmapp.framework.util.interfaces.ShoppingFragmentListClickHandler

class ShoppingListAdapter(
    private val clickHandler: ShoppingFragmentListClickHandler
): AbstractAdapter<
    ShoppingListViewHolderMVVM,
    ShoppingListViewHolderMvvmLayoutBinding,
    ShoppingListPresentation
>() {

    override val layoutId: Int = R.layout.shopping_list_view_holder_mvvm_layout

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolderMVVM {
        return ShoppingListViewHolderMVVM(
            getItemBinding(parent),
            clickHandler
        )
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolderMVVM, position: Int) {
        holder.setItem(list[position])
    }

    override fun getItemId(position: Int): Long {
        return list[position].id.hashCode().toLong()
    }
}