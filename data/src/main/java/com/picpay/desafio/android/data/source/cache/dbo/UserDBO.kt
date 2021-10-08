package com.picpay.desafio.android.data.source.cache.dbo


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.picpay.desafio.android.data.source.cache.DatabaseConstants

@Entity(tableName = DatabaseConstants.Table.USER)
data class UserDBO(
    @PrimaryKey
    val id: String,
    val name: String,
    val imageUrl: String,
    val username: String,
    val updateDate: Long
)
