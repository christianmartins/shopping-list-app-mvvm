package br.com.shoppinglistmvvmapp.di.module

import br.com.shoppinglistmvvmapp.data.datasource.network.service.UserService
import br.com.shoppinglistmvvmapp.data.datasource.network.util.HttpClient
import br.com.shoppinglistmvvmapp.data.datasource.network.util.NetworkServiceProvider
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    factory { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    factory { provideUserService(get()) }
}

fun provideOkHttpClient(): OkHttpClient{
    return HttpClient().getHttpClient()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit{
    return NetworkServiceProvider().getService(okHttpClient)
}

fun provideUserService(retrofit: Retrofit): UserService{
    return retrofit.create(UserService::class.java)
}

