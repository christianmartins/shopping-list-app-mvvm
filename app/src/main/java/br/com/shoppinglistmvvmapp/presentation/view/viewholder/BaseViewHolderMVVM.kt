package br.com.shoppinglistmvvmapp.presentation.view.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.shoppinglistmvvmapp.presentation.converter.Presentation

abstract class BaseViewHolderMVVM(itemView: View): RecyclerView.ViewHolder(itemView){

    abstract fun setItem(presentation: Presentation)
}