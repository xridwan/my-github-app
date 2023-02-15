package com.xridwan.mygithub.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xridwan.mygithub.data.local.entity.Favorite
import com.xridwan.mygithub.data.local.entity.UserData
import com.xridwan.mygithub.data.local.room.FavoriteDao
import com.xridwan.mygithub.data.local.room.FavoriteDb
import com.xridwan.mygithub.data.network.ApiConfig
import com.xridwan.mygithub.helper.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private var _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> = _message

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private var favoriteDao: FavoriteDao?
    private var favoriteDb: FavoriteDb? = FavoriteDb.getInstance(application)

    init {
        favoriteDao = favoriteDb?.favoriteDao()
    }

    private var _detail = MutableLiveData<UserData>()
    val detail: LiveData<UserData> = _detail

    fun setUser(username: String) {
        _loading.value = true
        ApiConfig.getApiService().getUser(username).enqueue(object : Callback<UserData> {
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                _loading.value = false
                if (response.isSuccessful) {
                    val data = response.body()
                    _detail.value = data!!
                } else {
                    _message.value = Event(response.message())
                }
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                _loading.value = false
                _message.value = Event(t.message.toString())
            }
        })
    }

    fun addFavorite(id: Int, login: String, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteDao?.addFavorite(
                Favorite(id, login, avatarUrl)
            )
        }
    }

    suspend fun checkFavorite(id: Int) = favoriteDao?.checkFavorite(id)

    fun removeFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteDao?.removeFavorite(id)
        }
    }
}