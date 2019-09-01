package com.mabrouk.flickrimageapp.di.modules

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mabrouk.flickrimageapp.data.api.BaseApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/*
* Created By mabrouk on 01/09/19
* FlickrImage App
*/

class ApiModule (val baseUrl:String) {
    val apiModule = module(override = true) {
        single {
            get<Retrofit>().create(BaseApi::class.java)
        }

        single {
            Retrofit.Builder().baseUrl(baseUrl).client(get()).addConverterFactory(
                GsonConverterFactory.create()
            )
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addCallAdapterFactory(
                    CoroutineCallAdapterFactory()
                ).build()
        }

        single {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        }
    }
}