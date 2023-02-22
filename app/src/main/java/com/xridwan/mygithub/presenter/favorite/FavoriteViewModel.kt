package com.xridwan.mygithub.presenter.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.xridwan.mygithub.data.local.entity.Favorite
import com.xridwan.mygithub.data.local.room.UserDao
import com.xridwan.mygithub.data.local.room.UserDatabase
import com.xridwan.mygithub.domain.usecase.UserUseCase

class FavoriteViewModel(
    private val userUseCase: UserUseCase
) : ViewModel() {

    fun getFavorite() = userUseCase.getFavorite().asLiveData()

    fun getData() = userUseCase.getData()

//    private var userDao: UserDao?
//    private var userDatabase: UserDatabase? = UserDatabase.getInstance(application)
//
//    init {
//        userDao = userDatabase?.favoriteDao()
//    }
//
//    fun getFavorite(): LiveData<List<Favorite>>? = userDao?.getFavorite()
}