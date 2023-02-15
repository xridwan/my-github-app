package com.xridwan.mygithub.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_tb")
data class Favorite(
    @PrimaryKey
    val id: Int,
    val login: String,
    val avatarUrl: String
) : Serializable
