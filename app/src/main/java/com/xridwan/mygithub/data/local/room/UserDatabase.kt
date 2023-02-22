package com.xridwan.mygithub.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xridwan.mygithub.data.local.entity.*

@Database(
    entities = [
        UserEntity::class,
        DetailEntity::class,
        FollowerEntity::class,
        FollowingEntity::class,
        Favorite::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}