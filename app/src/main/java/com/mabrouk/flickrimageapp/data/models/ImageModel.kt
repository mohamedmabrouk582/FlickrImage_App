package com.mabrouk.flickrimageapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


/*
* Created By mabrouk on 28/08/19
* FlickrImage App
*/

@Entity
data class ImageModel(@PrimaryKey val  id:Long, val owner:String?, val secret:String?, val server:Long, val farm:Int, val title:String?, val url_s:String?)