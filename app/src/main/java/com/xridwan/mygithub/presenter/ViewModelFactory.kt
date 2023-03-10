package com.xridwan.mygithub.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xridwan.mygithub.data.preference.ThemePreferences
import com.xridwan.mygithub.presenter.main.ThemeViewModel


class ViewModelFactory(
    private val pref: ThemePreferences
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}