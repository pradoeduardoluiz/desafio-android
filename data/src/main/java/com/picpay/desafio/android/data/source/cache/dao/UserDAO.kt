package com.picpay.desafio.android.data.source.cache.dao

import androidx.room.Dao
import androidx.room.Query
import com.picpay.desafio.android.data.source.cache.dbo.UserDBO
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UserDAO : BaseDAO<UserDBO>() {

  @Query(value = "SELECT * FROM user ORDER BY id ASC")
  abstract fun getUsers(): Flow<List<UserDBO>>

  @Query(value = "SELECT updateDate FROM user ORDER BY updateDate ASC")
  abstract fun getOldestRegisterUpdated(): Long?

  @Query(value = "DELETE FROM user")
  abstract fun deleteAll()
}
