package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.UserMapper
import com.picpay.desafio.android.data.source.cache.dao.UserDAO
import com.picpay.desafio.android.data.source.remote.service.PicPayService
import com.picpay.desafio.android.domain.models.UserModel
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.shared.kotlin.GetCurrentTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.net.UnknownHostException

class UserRepositoryImpl(
    private val service: PicPayService,
    private val dao: UserDAO,
    private val mapper: UserMapper,
    private val getCurrentTime: GetCurrentTime
) : UserRepository {

    override suspend fun getUsers(forceRefresh: Boolean): Flow<List<UserModel>> {
        val currentTime = getCurrentTime()
        val oldestUpdated = dao.getOldestRegisterUpdated() ?: NONE_TIME

        val hasNeedUpdateCache = currentTime - oldestUpdated >= CACHE_TIMEOUT
        if (hasNeedUpdateCache || forceRefresh) {
            try {
                val response = service.getUsers()
                val users = response.map { user -> mapper.mapToDbo(user, updateDate = currentTime) }
                dao.deleteAll()
                dao.insert(users)
            } catch (exception: UnknownHostException) {
                return getUsersDao()
            }
        }
        return getUsersDao()
    }

    private fun getUsersDao() = dao.getUsers().map { users -> users.map(mapper::mapToModel) }

    private companion object {
        const val CACHE_TIMEOUT = 300_000
        const val NONE_TIME = 0L
    }
}
