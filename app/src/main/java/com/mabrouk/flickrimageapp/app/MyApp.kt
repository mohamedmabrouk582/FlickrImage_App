package com.mabrouk.flickrimageapp.app

import android.app.Application
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mabrouk.flickrimageapp.data.api.BaseApi
import com.mabrouk.flickrimageapp.data.db.ImagesDb
import com.mabrouk.flickrimageapp.viewModels.base.BaseViewModelFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.mabrouk.flickrimageapp.di.modules.ApiModule
import com.mabrouk.flickrimageapp.di.modules.AppModule
import com.mabrouk.flickrimageapp.di.modules.RoomModule


/*
* Created By mabrouk on 16/03/19
* KotilnApp
*/

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(ApiModule("https://api.flickr.com").apiModule,
                AppModule(this@MyApp).appModule,
                RoomModule(this@MyApp,"Images").roomModule)
        }

    }

    val appModules = module (override = true){

        single {
            get<Retrofit>().create(BaseApi::class.java)

        }

        single {
            Retrofit.Builder().baseUrl("https://api.flickr.com").client(get()).addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addCallAdapterFactory(
                CoroutineCallAdapterFactory()
            ).build()
        }

        single {
            OkHttpClient.Builder().
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).
                build()
        }


        single { BaseViewModelFactory(get(), get(), this@MyApp) }
        single {
            Room.databaseBuilder(
                this@MyApp,
                ImagesDb::class.java,
                "Images"
            ).fallbackToDestructiveMigration().build().getTestDao()
        }

    }

}
