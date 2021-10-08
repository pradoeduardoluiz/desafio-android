package com.picpay.desafio.android.data.source.cache.dao

import androidx.room.Dao
import androidx.room.Query
import com.picpay.desafio.android.data.source.cache.dbo.UserDBO
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UserDAO : BaseDAO<UserDBO>() {

    @Query(value = "SELECT * FROM user ORDER BY name ASC")
    abstract fun getUsers(): Flow<List<UserDBO>>
}
