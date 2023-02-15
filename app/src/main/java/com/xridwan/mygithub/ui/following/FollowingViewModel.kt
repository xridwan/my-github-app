package com.xridwan.mygithub.ui.following

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xridwan.mygithub.data.local.entity.UserData
import com.xridwan.mygithub.data.network.ApiConfig
import com.xridwan.mygithub.helper.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {

    private val _following = MutableLiveData<MutableList<UserData>>()
    val following: LiveData<MutableList<UserData>> = _following

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private var _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> = _message

    fun getFollowing(username: String) {
        _loading.value = true
        ApiConfig.getApiService().getFollowing(username).enqueue(object :
            Callback<MutableList<UserData>> {
            override fun onResponse(
                call: Call<MutableList<UserData>>,
                response: Response<MutableList<UserData>>
            ) {
                _loading.value = false
                if (response.isSuccessful) {
                    val following = response.body()
                    _following.value = following!!
                } else {
                    _message.value = Event(response.message())
                }
            }

            override fun onFailure(call: Call<MutableList<UserData>>, t: Throwable) {
                _loading.value = false
                _message.value = Event(t.message.toString())
            }
        })
    }
}