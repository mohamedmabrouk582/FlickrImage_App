package com.mabrouk.flickrimageapp.viewModels.base

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mabrouk.flickrimageapp.callBacks.ImageListCallBack
import com.mabrouk.flickrimageapp.data.api.BaseApi
import com.mabrouk.flickrimageapp.data.db.ImagesDao
import com.mabrouk.flickrimageapp.utils.executors.AppExecutors
import com.mabrouk.flickrimageapp.viewModels.ImageListViewModel


/*
* Created By mabrouk on 16/03/19
* KotilnApp
*/

class BaseViewModelFactory(
   val imagesDao: ImagesDao
    , val api: BaseApi, val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImageListViewModel::class.java))
            return ImageListViewModel<ImageListCallBack>(application,api,imagesDao) as T
        throw IllegalArgumentException("Your View Model Not Found")
    }
}
