package com.mabrouk.flickrimageapp.data.models


/*
* Created By mabrouk on 28/08/19
* FlickrImage App
*/

data  class ImageListResponse(val page:Int, val pages:Int,val perpage:Int,val total:Int,val photo:ArrayList<ImageModel>)