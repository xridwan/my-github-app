package com.xridwan.mygithub.di

import androidx.room.Room
import com.xridwan.mygithub.data.local.room.UserDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            UserDatabase::class.java,
            "user.db"
        ).build()
    }
    single {
        get<UserDatabase>().userDao()
    }
}