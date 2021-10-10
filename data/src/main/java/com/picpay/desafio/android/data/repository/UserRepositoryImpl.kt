package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.UserMapper
import com.picpay.desafio.android.data.source.cache.dao.UserDAO
import com.picpay.desafio.android.data.source.remote.service.PicPayService
import com.picpay.desafio.android.domain.models.UserModel
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.shared.kotlin.GetCurrentTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val service: PicPayService,
    private val dao: UserDAO,
    private val mapper: UserMapper,
    private val getCurrentTime: GetCurrentTime
) : UserRepository {

    override suspend fun getUsers(): Flow<List<UserModel>> {
        val currentTime = getCurrentTime()
        val oldestUpdated = dao.getOldestRegisterUpdated() ?: 0

        val hasNeedUpdateCache = currentTime - oldestUpdated >= CACHE_TIMEOUT
        if (hasNeedUpdateCache) {
            val response = service.getUsers()
            val users = response.map { user -> mapper.mapToDbo(user, updateDate = currentTime) }
            dao.deleteAll()
            dao.insert(users)
        }

        return dao.getUsers().map { users -> users.map(mapper::mapToModel) }
    }

    private companion object {
        const val CACHE_TIMEOUT = 30000
    }
}
