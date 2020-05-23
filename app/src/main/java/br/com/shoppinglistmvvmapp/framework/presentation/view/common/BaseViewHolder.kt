package br.com.shoppinglistmvvmapp.framework.presentation.view.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.shoppinglistmvvmapp.domain.model.BaseModel

abstract class BaseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    abstract fun setItem(item: BaseModel)
}