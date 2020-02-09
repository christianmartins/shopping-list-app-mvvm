package br.com.shoppinglistmvvmapp.utils

import java.text.SimpleDateFormat
import java.util.*


object DateUtils {

    fun getDateTime(): String{
        val calendar = Calendar.getInstance(Locale.getDefault())
        val dateFormat = SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    fun getFormatDateTime(time: String): String {
        return try{
            val currentDateFormat = SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault())
            val date = currentDateFormat.parse(time)
             date?.let {
                val format = SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault())
                format.format(date)
            }?: ""
        }catch (e: Exception){
            e.printStackTrace()
            ""
        }
    }

    fun getTimeStamp() = System.currentTimeMillis()
}