package br.com.shoppinglistmvvmapp.data.datasource.network.util


import br.com.shoppinglistmvvmapp.utils.GlobalUtils
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkServiceProvider {

    private val baseUrl: String = GlobalUtils.urlApp

    fun getService(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            .client(okHttpClient)
            .build()
    }
}