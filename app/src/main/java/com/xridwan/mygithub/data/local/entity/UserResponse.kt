package com.xridwan.mygithub.data.local.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(

    @field:SerializedName("total_count")
    val total_count: Int,

    @field:SerializedName("items")
    val items: MutableList<UserData>

) : Parcelable

@Parcelize
data class UserData(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("followers")
    val followers: Int? = null,

    @field:SerializedName("following")
    val following: Int? = null,

    @field:SerializedName("public_repos")
    val repository: String? = null,

    @field:SerializedName("company")
    val company: String? = null,

    @field:SerializedName("location")
    val location: String? = null

) : Parcelable
