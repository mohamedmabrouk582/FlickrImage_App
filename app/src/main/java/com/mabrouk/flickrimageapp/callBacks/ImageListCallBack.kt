package com.mabrouk.flickrimageapp.callBacks

import androidx.paging.PagedList
import com.mabrouk.flickrimageapp.data.models.ImageModel
import com.mabrouk.flickrimageapp.ui.activites.ImageActivity


/*
* Created By mabrouk on 28/08/19
* FlickrImage App
*/

interface ImageListCallBack : BaseCallBack {
    fun loadImages(data:PagedList<ImageModel>)
    fun getImageActivity():ImageActivity
}