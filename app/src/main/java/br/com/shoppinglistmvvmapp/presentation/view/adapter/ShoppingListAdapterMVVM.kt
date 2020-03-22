package br.com.shoppinglistmvvmapp.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.shoppinglistmvvmapp.data.model.ShoppingList
import br.com.shoppinglistmvvmapp.databinding.ShoppingListViewHolderMvvmLayoutBinding
import br.com.shoppinglistmvvmapp.presentation.converter.ShoppingListPresentation
import br.com.shoppinglistmvvmapp.presentation.view.viewholder.BaseViewHolderMVVM
import br.com.shoppinglistmvvmapp.presentation.view.viewholder.ShoppingListViewHolderMVVM
import br.com.shoppinglistmvvmapp.utils.interfaces.ShoppingFragmentListClickHandler

class ShoppingListAdapterMVVM(
    private val clickHandler: ShoppingFragmentListClickHandler
): BaseAdapterMVVM() {

    private val shoppingLists = ArrayList<ShoppingList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolderMVVM {
        return ShoppingListViewHolderMVVM(
            getItemBinding(parent),
            clickHandler
        )
    }

    private fun getItemBinding(parent: ViewGroup): ShoppingListViewHolderMvvmLayoutBinding {
        return ShoppingListViewHolderMvvmLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolderMVVM, position: Int) {
        val presentation = ShoppingListPresentation.convert(shoppingLists[position]) //temp -> (no async conversion)
        holder.setItem(presentation)
    }

    override fun getItemId(position: Int): Long {
        return shoppingLists[position].id.hashCode().toLong()
    }

    override fun getItemCount(): Int {
        return shoppingLists.size
    }

    fun addAll(list: List<ShoppingList>){
        this.shoppingLists.addAll(list)
        notifyDataSetChanged()
    }

    fun add(shoppingList: ShoppingList){
        this.shoppingLists.add(shoppingList)
        this.notifyItemInserted(this.shoppingLists.size)
    }

    fun clear(){
        this.shoppingLists.clear()
    }
}