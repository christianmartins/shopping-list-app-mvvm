package br.com.shoppinglistmvvmapp.data.datasource.network.service.common

import br.com.shoppinglistmvvmapp.data.datasource.network.service.ShoppingListAppApi
import br.com.shoppinglistmvvmapp.utils.GlobalUtils
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkServiceProvider {

    fun getService(): ShoppingListAppApi {
        val url = GlobalUtils.urlApp
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(OkHttpClient().newBuilder().build())
            .build()

        return retrofit.create(ShoppingListAppApi::class.java)
    }
}
