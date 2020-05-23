package br.com.shoppinglistmvvmapp.framework.presentation.view.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractViewHolder<Presentation: AbstractPresentation>(itemView: View): RecyclerView.ViewHolder(itemView){

    abstract fun setItem(presentation: Presentation)
}