package br.com.shoppinglistmvvmapp.framework.presentation.view.viewholder

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.PopupMenu
import br.com.shoppinglistmvvmapp.R
import br.com.shoppinglistmvvmapp.data.model.BaseModel
import br.com.shoppinglistmvvmapp.data.model.ShoppingList
import br.com.shoppinglistmvvmapp.framework.presentation.view.viewholder.old.BaseViewHolder
import br.com.shoppinglistmvvmapp.utils.DateUtils
import br.com.shoppinglistmvvmapp.framework.util.interfaces.ShoppingFragmentListClickHandler
import com.google.android.material.textview.MaterialTextView

class ShoppingListViewHolder(
    itemView: View,
    private val clickHandler: ShoppingFragmentListClickHandler
): BaseViewHolder(itemView) {
    private val title = itemView.findViewById<MaterialTextView>(R.id.shopping_list_title)
    private val description = itemView.findViewById<MaterialTextView>(R.id.shopping_list_description)
    private val authorName = itemView.findViewById<MaterialTextView>(R.id.shopping_list_author_name)
    private val date = itemView.findViewById<MaterialTextView>(R.id.shopping_list_date)
    private val progress = itemView.findViewById<MaterialTextView>(R.id.shopping_list_items_for_conclusion)
    private val moreMenu = itemView.findViewById<AppCompatImageView>(R.id.shopping_list_more)
    private val context by lazy {
        itemView.context
    }

    private val popupMenu = PopupMenu(context, moreMenu)

    init {
        popupMenu.inflate(R.menu.shopping_list_menu)
    }

    override fun setItem(item: BaseModel) {
        if(item is ShoppingList){
            title?.text = item.title
            description?.text = item.description
            authorName?.text = context.getString(R.string.created_by, item.authorName)
            date?.text = DateUtils.getFormatDateTime(item.createAt)
            progress?.text = context.getString(R.string.shopping_list_current_progress, item.currentItemsToComplete, item.totalItemsToComplete)

            itemView.setOnClickListener {
                clickHandler.onClickItemList(item.id)
            }
            onClickMore()
            onPopupMenuClick(item)
        }
    }

    private fun onClickMore(){
        moreMenu.setOnClickListener {
            popupMenu.show()
        }
    }

    private fun onPopupMenuClick(shoppingList: ShoppingList){
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.shopping_list_delete -> {
                    clickHandler.onClickDeleteItemList(shoppingList)
                    true
                }
                R.id.shopping_list_edit -> {
                    clickHandler.onClickEditItemList(shoppingList)
                    true
                }
                else -> false
            }
        }
    }

}