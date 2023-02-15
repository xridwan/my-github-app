package com.xridwan.mygithub.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.xridwan.mygithub.data.local.entity.Favorite
import com.xridwan.mygithub.data.local.room.FavoriteDao
import com.xridwan.mygithub.data.local.room.FavoriteDb

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var favoriteDao: FavoriteDao?
    private var favoriteDb: FavoriteDb? = FavoriteDb.getInstance(application)

    init {
        favoriteDao = favoriteDb?.favoriteDao()
    }

    fun getFavorite(): LiveData<List<Favorite>>? = favoriteDao?.getFavorite()
}