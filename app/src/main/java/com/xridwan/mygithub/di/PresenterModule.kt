package com.xridwan.mygithub.di

import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.xridwan.mygithub.data.preference.ReminderPreference
import com.xridwan.mygithub.data.preference.ThemePreferences
import com.xridwan.mygithub.domain.usecase.UserUseCase
import com.xridwan.mygithub.domain.usecase.UserUsecaseImpl
import com.xridwan.mygithub.presenter.detail.DetailViewModel
import com.xridwan.mygithub.presenter.favorite.FavoriteViewModel
import com.xridwan.mygithub.presenter.follower.FollowersViewModel
import com.xridwan.mygithub.presenter.following.FollowingViewModel
import com.xridwan.mygithub.presenter.main.MainViewModel
import com.xridwan.mygithub.presenter.main.ThemeViewModel
import com.xridwan.mygithub.presenter.other.ReminderViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val prefsModule = module {
    single {
        val appContext = androidContext()
        val userPref = "settings"
        PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(appContext, userPref)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = {
                appContext.preferencesDataStoreFile(userPref)
            }
        )
    }
    single {
        ThemePreferences(get())
    }
    single {
        ReminderPreference(androidApplication())
    }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { ThemeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { FollowersViewModel(get()) }
    viewModel { FollowingViewModel(get()) }
    viewModel { ReminderViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
}

val useCaseModule = module {
    single<UserUseCase> {
        UserUsecaseImpl(get())
    }
}