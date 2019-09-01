package com.mabrouk.flickrimageapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mabrouk.flickrimageapp.data.models.ImageModel


/*
* Created By mabrouk on 20/03/19
* KotilnApp
*/
@Database(entities = [ImageModel::class], version = 2, exportSchema = false)
abstract class ImagesDb : RoomDatabase() {
     abstract fun getTestDao(): ImagesDao
}
