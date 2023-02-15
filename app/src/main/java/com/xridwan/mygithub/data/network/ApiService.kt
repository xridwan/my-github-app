package com.xridwan.mygithub.data.network

import com.xridwan.mygithub.data.local.entity.UserData
import com.xridwan.mygithub.data.local.entity.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun getItems(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20,
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getUser(
        @Path("username") username: String
    ): Call<UserData>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<MutableList<UserData>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<MutableList<UserData>>
}