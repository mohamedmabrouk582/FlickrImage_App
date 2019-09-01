package com.mabrouk.flickrimageapp.data.models

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.mabrouk.flickrimageapp.data.api.BaseApi
import com.mabrouk.flickrimageapp.utils.network.RequestCoroutines
import com.mabrouk.flickrimageapp.utils.network.RequestListener


/*
* Created By mabrouk on 28/08/19
* FlickrImage App
*/

class ImageDataSource(val api:BaseApi,val text:String?,val listener: RequestListener<FilckrResponse>,val context: Context) : PageKeyedDataSource<Int,ImageModel>() , RequestCoroutines{
    companion object{
        val FIRST_PAGE=1
        val PAGE_SIZE=100
        val PAGE_SIZE2=99
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ImageModel>) {
        (if (text==null)api.loadImages(FIRST_PAGE)else api.searchImage(FIRST_PAGE,text)).handelEx(context,object : RequestListener<FilckrResponse>{
         override fun onResponse(data: MutableLiveData<FilckrResponse>) {
             if (data.value!=null && data.value?.stat=="ok"){
                 listener.onResponse(data)
                 callback.onResult(data.value?.photos?.photo!!,null, FIRST_PAGE+1)
             }else {
                 onError(data.value?.message!!)
             }
         }

         override fun onEmpty(msg: String) {
             listener.onEmpty(msg)
         }

         override fun onError(msg: String) {
             listener.onError(msg)
         }

         override fun onSessionExpired(msg: String) {
             listener.onSessionExpired(msg)
         }

         override fun onNetWorkError(msg: String) {
             listener.onNetWorkError(msg)
         }

     })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ImageModel>) {
        (if (text==null)api.loadImages(params.key)else api.searchImage(FIRST_PAGE,text)).handelEx(context,object : RequestListener<FilckrResponse>{
            override fun onResponse(data: MutableLiveData<FilckrResponse>) {
                if (data.value!=null && data.value?.stat=="ok"){
                    listener.onResponse(data)
                    callback.onResult(data.value?.photos?.photo!!,params.key+1)
                }else {
                    onError(data.value?.message!!)
                }
            }

            override fun onEmpty(msg: String) {
                listener.onEmpty(msg)
            }

            override fun onError(msg: String) {
                listener.onError(msg)
            }

            override fun onSessionExpired(msg: String) {
                listener.onSessionExpired(msg)
            }

            override fun onNetWorkError(msg: String) {
                listener.onNetWorkError(msg)
            }

        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ImageModel>) {
        (if (text==null)api.loadImages(params.key)else api.searchImage(FIRST_PAGE,text)).handelEx(context,object : RequestListener<FilckrResponse>{
            override fun onResponse(data: MutableLiveData<FilckrResponse>) {
                if (data.value!=null && data.value?.stat=="ok"){
                    listener.onResponse(data)
                    callback.onResult(data.value?.photos?.photo!!,if (params.key>1) params.key-1 else null)
                }else {
                    onError(data.value?.message!!)
                }
            }

            override fun onEmpty(msg: String) {
                listener.onEmpty(msg)
            }

            override fun onError(msg: String) {
                listener.onError(msg)
            }

            override fun onSessionExpired(msg: String) {
                listener.onSessionExpired(msg)
            }

            override fun onNetWorkError(msg: String) {
                listener.onNetWorkError(msg)
            }

        })

    }

}