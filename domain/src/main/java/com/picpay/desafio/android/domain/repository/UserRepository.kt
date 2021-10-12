package com.picpay.desafio.android.domain.repository

import com.picpay.desafio.android.domain.models.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {

  suspend fun getUsers(forceRefresh: Boolean): Flow<List<UserModel>>

}
