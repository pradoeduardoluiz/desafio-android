package com.picpay.desafio.android.domain.models

data class UserModel(
    val id: String,
    val name: String,
    val imageUrl: String,
    val username: String,
    val updateDate: Long
)
