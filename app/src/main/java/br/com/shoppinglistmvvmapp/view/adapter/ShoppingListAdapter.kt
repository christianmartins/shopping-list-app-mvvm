package br.com.shoppinglistmvvmapp.view.adapter

import android.view.View
import android.view.ViewGroup
import br.com.shoppinglistmvvmapp.R
import br.com.shoppinglistmvvmapp.data.model.ShoppingList
import br.com.shoppinglistmvvmapp.utils.interfaces.ShoppingFragmentListClickHandler
import br.com.shoppinglistmvvmapp.view.viewholder.BaseViewHolder
import br.com.shoppinglistmvvmapp.view.viewholder.ShoppingListViewHolder

class ShoppingListAdapter(private val clickHandler: ShoppingFragmentListClickHandler): BaseAdapter() {

    private val shoppingLists = ArrayList<ShoppingList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ShoppingListViewHolder(
            View.inflate(parent.context, R.layout.shopping_list_view_holder_layout, null),
            clickHandler
        )
    }

    override fun getItemId(position: Int): Long {
        return shoppingLists[position].id.hashCode().toLong()
    }

    override fun getItemCount(): Int {
        return shoppingLists.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = shoppingLists[position]
        holder.setItem(item)
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