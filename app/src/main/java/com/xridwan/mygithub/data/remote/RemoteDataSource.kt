package com.xridwan.mygithub.data.remote

import com.xridwan.mygithub.data.remote.network.ApiResponse
import com.xridwan.mygithub.data.remote.network.ApiService
import com.xridwan.mygithub.data.remote.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(
    private val apiService: ApiService,
) {
    suspend fun getUsers(username: String): Flow<ApiResponse<List<UserResponse>>> {
        return flow {
            try {
                val response = apiService.getUsers(username).items
                if (response.isNotEmpty()) emit(ApiResponse.Success(response))
                else emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUser(username: String): Flow<ApiResponse<UserResponse>> {
        return flow {
            try {
                val response = apiService.getUser(username)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFollowers(username: String): Flow<ApiResponse<List<UserResponse>>> {
        return flow {
            try {
                val response = apiService.getFollowers(username)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFollowing(username: String): Flow<ApiResponse<List<UserResponse>>> {
        return flow {
            try {
                val response = apiService.getFollowing(username)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}