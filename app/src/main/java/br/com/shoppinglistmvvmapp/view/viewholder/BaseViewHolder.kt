package br.com.shoppinglistmvvmapp.view.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.shoppinglistmvvmapp.data.model.BaseModel

abstract class BaseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    abstract fun setItem(item: BaseModel)
}