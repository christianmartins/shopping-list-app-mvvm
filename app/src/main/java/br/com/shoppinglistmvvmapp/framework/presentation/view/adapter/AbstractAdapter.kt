package br.com.shoppinglistmvvmapp.framework.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import br.com.shoppinglistmvvmapp.framework.presentation.model.AbstractPresentation


abstract class AbstractAdapter<
    VH: RecyclerView.ViewHolder,
    Binding: ViewDataBinding,
    Presentation: AbstractPresentation
> : RecyclerView.Adapter<VH>(){

    protected val list = ArrayList<Presentation>()

    protected abstract val layoutId: Int

    protected fun getItemBinding(parent: ViewGroup): Binding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        )
    }

    override fun getItemId(position: Int): Long {
        return list[position].hashCode().toLong()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    open fun addAll(list: List<Presentation>){
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    open fun add(list: Presentation){
        this.list.add(list)
        notifyItemInserted(itemCount)
    }

    fun clear(){
        this.list.clear()
    }
}