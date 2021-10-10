package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.UserMapper
import com.picpay.desafio.android.data.source.cache.dao.UserDAO
import com.picpay.desafio.android.data.source.remote.service.PicPayService
import com.picpay.desafio.android.domain.models.UserModel
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.shared.kotlin.GetCurrentTime
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {
    private val service = mockk<PicPayService>()
    private val dao = mockk<UserDAO>()
    private val mapper = mockk<UserMapper>()
    private val getCurrentTime = mockk<GetCurrentTime>()

    private lateinit var userRepository: UserRepository

    @Before
    fun `setup test`() {
        userRepository = UserRepositoryImpl(
            service = service,
            dao = dao,
            mapper = mapper,
            getCurrentTime = getCurrentTime
        )
    }

    @Test
    fun `should get users and save in local cached`() = runBlocking {
        val expected = listOf(
            UserModel(
                id = "id",
                name = "name",
                imageUrl = "imageUrl",
                username = "username"
            )
        )

        coEvery { dao.getOldestRegisterUpdated() } returns null

        val actualFlow = userRepository.getUsers()
        actualFlow.collect { actual ->
            Assert.assertEquals(expected, actual)
        }

        coVerify(exactly = 1) { dao.getOldestRegisterUpdated() }

        confirmVerified(service, dao, mapper, getCurrentTime)
    }

}
