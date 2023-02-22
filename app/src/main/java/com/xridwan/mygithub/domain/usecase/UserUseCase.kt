package com.xridwan.mygithub.domain.usecase

import android.database.Cursor
import com.xridwan.mygithub.data.local.entity.Favorite
import com.xridwan.mygithub.domain.Resource
import com.xridwan.mygithub.domain.model.DetailData
import com.xridwan.mygithub.domain.model.FollowerData
import com.xridwan.mygithub.domain.model.FollowingData
import com.xridwan.mygithub.domain.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    fun getUsers(username: String): Flow<Resource<List<UserData>>>
    fun getFavorite(): Flow<List<UserData>>
    fun getUser(username: String): Flow<Resource<DetailData>>
    fun getFollowers(username: String): Flow<Resource<List<FollowerData>>>
    fun getFollowing(username: String): Flow<Resource<List<FollowingData>>>
    suspend fun addFavorite(favorite: Favorite)
    suspend fun removeFavorite(id: Int)
    suspend fun checkFavorite(id: String): Int
    fun getData(): Cursor
}