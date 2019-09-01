package com.mabrouk.flickrimageapp.data.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mabrouk.flickrimageapp.data.models.ImageModel


/*
* Created By mabrouk on 20/03/19
* KotilnApp
 *
*/
@Dao
interface ImagesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item:ImageModel) : Long

    @Query("select * from ImageModel")
    fun getData() : DataSource.Factory<Int,ImageModel>

    @Query("select * from ImageModel")
    fun getDatas() : LiveData<List<ImageModel>>
}
