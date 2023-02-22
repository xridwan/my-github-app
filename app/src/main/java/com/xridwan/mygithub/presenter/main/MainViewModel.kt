package com.xridwan.mygithub.presenter.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.xridwan.mygithub.domain.usecase.UserUseCase

class MainViewModel(
    private val userUseCase: UserUseCase,
) : ViewModel() {

    fun getUsers(username: String) = userUseCase.getUsers(username).asLiveData()
}