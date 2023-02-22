package com.xridwan.mygithub.presenter.following

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.xridwan.mygithub.domain.usecase.UserUseCase

class FollowingViewModel(
    private val userUseCase: UserUseCase,
) : ViewModel() {

    fun followings(loginId: String) = userUseCase.getFollowing(loginId).asLiveData()
}