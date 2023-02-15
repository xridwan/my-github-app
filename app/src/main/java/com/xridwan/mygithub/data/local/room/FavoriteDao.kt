package com.xridwan.mygithub.data.local.room

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.xridwan.mygithub.data.local.entity.Favorite

@Dao
interface FavoriteDao {

    @Insert
    suspend fun addFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorite_tb")
    fun getFavorite(): LiveData<List<Favorite>>

    @Query("SELECT count(*) FROM favorite_tb WHERE favorite_tb.id = :id")
    suspend fun checkFavorite(id: Int): Int

    @Query("DELETE FROM favorite_tb WHERE favorite_tb.id = :id")
    suspend fun removeFavorite(id: Int): Int

    @Query("SELECT * FROM favorite_tb")
    fun getData(): Cursor
}