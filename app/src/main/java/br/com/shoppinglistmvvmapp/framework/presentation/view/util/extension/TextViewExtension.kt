package br.com.shoppinglistmvvmapp.framework.presentation.view.util.extension

import android.graphics.Paint
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import br.com.shoppinglistmvvmapp.utils.extension.nonNullable

fun TextView.changeColor(@ColorRes resColor: Int){
    setTextColor(ContextCompat.getColor(context, resColor))
}

fun TextView.addPaintFlagsStrikeThroughEffect(){
    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}
fun TextView.setPaintFlagsStrikeThroughEffect(isStrikeThrough: Boolean){
    if(isStrikeThrough){
        addPaintFlagsStrikeThroughEffect()
    }else{
        paintFlags = 0
    }
}

fun TextView.getSafeText(): String{
    return if(text.isNullOrBlank() && text.isNullOrEmpty()){
        ""
    }else text.toString()
}

fun TextView?.getSafeTextWithTrim(): String{
    return this?.getSafeText()?.trim().nonNullable()
}
