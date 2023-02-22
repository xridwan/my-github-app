package com.xridwan.mygithub.data.local.room

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xridwan.mygithub.data.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    suspend fun addFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorite_tb")
    fun getFavorite(): Flow<List<Favorite>>

    @Query("SELECT count(*) FROM favorite_tb WHERE favorite_tb.login LIKE :id")
    suspend fun checkFavorite(id: String): Int

    @Query("DELETE FROM favorite_tb WHERE favorite_tb.id = :id")
    suspend fun removeFavorite(id: Int)

    @Query("SELECT * FROM favorite_tb")
    fun getData(): Cursor

    @Query("SELECT * FROM search")
    fun getUsers(): Flow<List<UserEntity>>

    @Query("DELETE FROM search")
    suspend fun clearUsers()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserList(userList: List<UserEntity>)

    @Query("SELECT * FROM detail WHERE login LIKE :username")
    fun getUser(username: String): Flow<DetailEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(detailEntity: DetailEntity)

    @Query("SELECT * FROM follower WHERE loginId LIKE :loginId")
    fun getFollower(loginId: String): Flow<List<FollowerEntity>>

    @Query("DELETE FROM follower")
    suspend fun clearFollower()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFollowerList(userList: List<FollowerEntity>)

    @Query("SELECT * FROM following WHERE loginId LIKE :loginId")
    fun getFollowing(loginId: String): Flow<List<FollowingEntity>>

    @Query("DELETE FROM following")
    suspend fun clearFollowing()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFollowingList(userList: List<FollowingEntity>)
}