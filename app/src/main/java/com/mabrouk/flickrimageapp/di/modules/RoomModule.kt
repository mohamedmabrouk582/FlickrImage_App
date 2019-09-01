package com.mabrouk.flickrimageapp.di.modules

import android.content.Context
import androidx.room.Room
import com.mabrouk.flickrimageapp.data.db.ImagesDb
import org.koin.dsl.module


/*
* Created By mabrouk on 01/09/19
* FlickrImage App
*/

class RoomModule (val app: Context, val dbName: String) {
        val roomModule = module(override = true) {
            single {
                Room.databaseBuilder(
                    app,
                    ImagesDb::class.java,
                    dbName
                ).fallbackToDestructiveMigration().build().getTestDao()
            }
        }
}