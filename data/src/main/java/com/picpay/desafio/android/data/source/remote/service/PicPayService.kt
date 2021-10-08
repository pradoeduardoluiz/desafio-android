package com.picpay.desafio.android.data.source.remote.service

import com.picpay.desafio.android.data.source.remote.dto.UserDTO
import retrofit2.http.GET

interface PicPayService {
    @GET("users")
    suspend fun getUsers(): List<UserDTO>
}
