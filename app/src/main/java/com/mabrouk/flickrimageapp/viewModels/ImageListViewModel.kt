package com.mabrouk.flickrimageapp.viewModels

import android.app.Application
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mabrouk.flickrimageapp.R
import com.mabrouk.flickrimageapp.callBacks.ImageListCallBack
import com.mabrouk.flickrimageapp.data.api.BaseApi
import com.mabrouk.flickrimageapp.data.db.ImagesDao
import com.mabrouk.flickrimageapp.data.models.FilckrResponse
import com.mabrouk.flickrimageapp.data.models.ImageDataSource
import com.mabrouk.flickrimageapp.data.models.ImageModel
import com.mabrouk.flickrimageapp.utils.network.RequestCoroutines
import com.mabrouk.flickrimageapp.utils.network.RequestListener
import com.mabrouk.flickrimageapp.viewModels.base.BaseViewModel
import com.mabrouk.loaderlib.RetryCallBack
import kotlinx.coroutines.*


/*
* Created By mabrouk on 28/08/19
* FlickrImage App
*/

class ImageListViewModel<v : ImageListCallBack>(app:Application,val api:BaseApi,val dao:ImagesDao) : BaseViewModel<v>(app) {
    var loader:ObservableBoolean = ObservableBoolean()
    var error:ObservableField<String> = ObservableField()
    var loadedBefore:ObservableBoolean = ObservableBoolean()

    val searchListner:SearchView.OnQueryTextListener = object : SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            newText?.apply {
                if (isNullOrEmpty()) {
                    loadImages()
                } else loadImages(this)
            }
            return true
        }

    }

    val callBack:RetryCallBack= object :RetryCallBack{
        override fun onRetry(){
          loadImages()
        }

    }



    fun loadImages(quey:String?=null){
        loader.set(true)
        loadedBefore.set(false)
        error.set(null)

        val dataSource = ImageDataSource(api,quey,object : RequestListener<FilckrResponse>{
            override fun onResponse(data: MutableLiveData<FilckrResponse>) {
                loader.set(false)
                loadedBefore.set(true)
                data.value?.photos?.photo?.apply {
                    forEach {
                        GlobalScope.apply {
                            launch (Dispatchers.IO){
                                if (quey==null) Log.d("InsertData","${dao.insert(it)}")
                            }
                        }
                    }
                }
            }

            override fun onEmpty(msg: String) {
                loader.set(false)
                if (!loadedBefore.get()) error.set(msg)
            }

            override fun onError(msg: String) {
                loader.set(false)
                if (!loadedBefore.get()) error.set(msg)
            }

            override fun onSessionExpired(msg: String) {
                loader.set(false)
                if (!loadedBefore.get()) error.set(msg)
            }

            override fun onNetWorkError(msg: String) {
                loader.set(false)
                showOfflineData(dao.getData())
            }

        },getApplication())

        val factory= object : DataSource.Factory<Int,ImageModel>(){
            override fun create(): DataSource<Int, ImageModel> = dataSource
        }

        showNetworkData(factory)
    }

    fun showNetworkData(factory:DataSource.Factory<Int,ImageModel>){
     val config = PagedList.Config.Builder()
         .setPageSize(ImageDataSource.PAGE_SIZE)
         .setEnablePlaceholders(true).build()


        LivePagedListBuilder(factory,config).build()
            .observe(view.getImageActivity(), Observer {
               view.loadImages(it)
            })
    }

    fun showOfflineData(factory:DataSource.Factory<Int,ImageModel>){
        val config = PagedList.Config.Builder()
            .setPageSize(ImageDataSource.PAGE_SIZE2)
            .setEnablePlaceholders(false).build()

        LivePagedListBuilder(factory,config).build()
            .observe(view.getImageActivity(), Observer {
                if (it.isNotEmpty())view.loadImages(it)else error.set(view.getImageActivity().resources.getString(R.string.no_data_found))
            })
    }


}