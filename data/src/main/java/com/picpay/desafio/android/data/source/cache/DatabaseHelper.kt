package com.picpay.desafio.android.data.source.cache

import android.content.Context
import androidx.room.Room

class DatabaseHelper(private val context: Context) {

    fun buildDataBase(): PicPayDatabase {
        return Room.databaseBuilder(
            context,
            PicPayDatabase::class.java,
            DatabaseConstants.NAME
        ).apply {
            fallbackToDestructiveMigration()
        }.build()
    }

}
