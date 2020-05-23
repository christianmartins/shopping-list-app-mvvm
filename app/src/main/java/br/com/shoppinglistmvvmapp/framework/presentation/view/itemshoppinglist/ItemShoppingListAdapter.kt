package br.com.shoppinglistmvvmapp.framework.presentation.view.itemshoppinglist

import android.view.View
import android.view.ViewGroup
import br.com.shoppinglistmvvmapp.R
import br.com.shoppinglistmvvmapp.domain.model.ItemShoppingList
import br.com.shoppinglistmvvmapp.framework.presentation.view.common.BaseAdapter
import br.com.shoppinglistmvvmapp.framework.util.interfaces.ItemShoppingListListeners
import br.com.shoppinglistmvvmapp.framework.presentation.view.common.BaseViewHolder

class ItemShoppingListAdapter(
    private val itemShoppingListListeners: ItemShoppingListListeners
): BaseAdapter() {

    private val itemsShoppingList = ArrayList<ItemShoppingList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ItemShoppingListViewHolder(
            View.inflate(parent.context, R.layout.item_shopping_list_view_holder_layout, null),
            itemShoppingListListeners
        )
    }

    override fun getItemId(position: Int): Long {
        return itemsShoppingList[position].id.hashCode().toLong()
    }

    override fun getItemCount(): Int {
        return itemsShoppingList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = itemsShoppingList[position]
        holder.setItem(item)
    }

    fun add(item: ItemShoppingList){
        this.itemsShoppingList.add(item)
        this.notifyItemInserted(this.itemsShoppingList.size)
    }

    fun addAll(items: List<ItemShoppingList>){
        this.itemsShoppingList.addAll(items)
        this.notifyDataSetChanged()
    }

    fun clear(){
        itemsShoppingList.clear()
    }

}