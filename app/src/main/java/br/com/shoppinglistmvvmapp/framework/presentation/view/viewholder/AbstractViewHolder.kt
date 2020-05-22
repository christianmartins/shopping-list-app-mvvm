package br.com.shoppinglistmvvmapp.framework.presentation.view.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.shoppinglistmvvmapp.framework.presentation.model.AbstractPresentation

abstract class AbstractViewHolder<Presentation: AbstractPresentation>(itemView: View): RecyclerView.ViewHolder(itemView){

    abstract fun setItem(presentation: Presentation)
}