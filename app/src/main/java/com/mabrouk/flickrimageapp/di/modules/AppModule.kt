package com.mabrouk.flickrimageapp.di.modules

import com.mabrouk.flickrimageapp.app.MyApp
import com.mabrouk.flickrimageapp.data.api.BaseApi
import com.mabrouk.flickrimageapp.data.db.ImagesDao
import com.mabrouk.flickrimageapp.utils.SharedManager
import com.mabrouk.flickrimageapp.viewModels.base.BaseViewModelFactory
import org.koin.dsl.module


/*
* Created By mabrouk on 01/09/19
* FlickrImage App
*/

class AppModule (val app:MyApp){
        val appModule = module(override = true) {
            single {
                BaseViewModelFactory(get(), get(), app)
            }

            single { SharedManager(app.applicationContext,"Test") }
        }
}