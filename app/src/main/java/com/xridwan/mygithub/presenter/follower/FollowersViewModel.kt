package com.xridwan.mygithub.presenter.follower

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.xridwan.mygithub.domain.usecase.UserUseCase

class FollowersViewModel(
    private val userUseCase: UserUseCase,
) : ViewModel() {

    fun followers(loginId: String) = userUseCase.getFollowers(loginId).asLiveData()
}