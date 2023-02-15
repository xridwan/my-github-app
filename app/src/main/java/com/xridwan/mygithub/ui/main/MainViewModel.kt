package com.xridwan.mygithub.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xridwan.mygithub.data.local.entity.UserData
import com.xridwan.mygithub.data.local.entity.UserResponse
import com.xridwan.mygithub.data.network.ApiConfig
import com.xridwan.mygithub.helper.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> = _message

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    companion object{
        const val QUERY = "Android"
    }

    init {
        userSearch(QUERY)
    }

    private val _userList = MutableLiveData<MutableList<UserData>>()
    val userList: LiveData<MutableList<UserData>> = _userList

    fun userSearch(username: String) {
        _loading.value = true
        ApiConfig.getApiService().getItems(username).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _loading.value = false
                if (response.isSuccessful) {
                    val data = response.body()?.items
                    _userList.value = data!!
                } else {
                    _message.value = Event(response.message())
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _loading.value = false
                _message.value = Event(t.message.toString())
            }
        })
    }
}