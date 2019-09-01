package com.mabrouk.flickrimageapp.data.api

import com.mabrouk.flickrimageapp.data.models.FilckrResponse
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/*
* Created By mabrouk on 16/03/19
* KotilnApp
*/

interface BaseApi {
    @GET("/services/rest/?api_key=a3a200228dd4120b9bdefb9a9af13fae&format=json&nojsoncallback=1&extras=url_s&method=flickr.photos.getRecent")
    fun loadImages(@Query("page") page:Int) : Deferred<FilckrResponse>

    @GET("/services/rest/?api_key=a3a200228dd4120b9bdefb9a9af13fae&format=json&nojsoncallback=1&extras=url_s&method=flickr.photos.search")
    fun searchImage(@Query("page") page: Int , @Query("text") text:String) : Deferred<FilckrResponse>
}
