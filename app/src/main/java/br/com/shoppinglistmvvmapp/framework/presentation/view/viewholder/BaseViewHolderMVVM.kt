package br.com.shoppinglistmvvmapp.framework.presentation.view.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.shoppinglistmvvmapp.framework.presentation.converter.BasePresentation

abstract class BaseViewHolderMVVM<Presentation: BasePresentation>(itemView: View): RecyclerView.ViewHolder(itemView){

    abstract fun setItem(presentation: Presentation)
}