package com.picpay.desafio.android.data.source.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.picpay.desafio.android.data.source.cache.dao.UserDAO
import com.picpay.desafio.android.data.source.cache.dbo.UserDBO

@Database(
    entities = [UserDBO::class],
    version = DatabaseConstants.VERSION,
    exportSchema = false
)
abstract class PicPayDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO
}
