package com.xridwan.mygithub.presenter.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.xridwan.mygithub.data.local.entity.Favorite
import com.xridwan.mygithub.domain.usecase.UserUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(
    private val userUseCase: UserUseCase,
) : ViewModel() {

    fun getUser(username: String) = userUseCase.getUser(username).asLiveData()

    fun addFavorite(favorite: Favorite) {
        CoroutineScope(Dispatchers.IO).launch {
            userUseCase.addFavorite(favorite)
        }
    }
//    fun addFavorite(id: Int, login: String, avatarUrl: String) {
//        CoroutineScope(Dispatchers.IO).launch {
//            favoriteDao?.addFavorite(
//                Favorite(id, login, avatarUrl)
//            )
//        }
//    }

    suspend fun checkFavorite(id: String) = userUseCase.checkFavorite(id)

    fun removeFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userUseCase.removeFavorite(id)
        }
    }
}