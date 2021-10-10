package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.UserMapper
import com.picpay.desafio.android.data.source.cache.dao.UserDAO
import com.picpay.desafio.android.data.source.cache.dbo.UserDBO
import com.picpay.desafio.android.data.source.remote.dto.UserDTO
import com.picpay.desafio.android.data.source.remote.service.PicPayService
import com.picpay.desafio.android.domain.models.UserModel
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.shared.kotlin.GetCurrentTime
import io.mockk.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
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

    private val currentTimeMock = 1000000000000

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
    fun `should get users from api and save in local cache`() = runBlocking {
        val model = UserModel(
            id = "id",
            name = "name",
            imageUrl = "imageUrl",
            username = "username"
        )

        val expected = listOf(model)

        val dto = UserDTO(
            id = "id",
            name = "name",
            imageUrl = "imageUrl",
            username = "username"
        )
        val dtos = listOf(dto)

        val dbo = UserDBO(
            id = "id",
            name = "name",
            imageUrl = "imageUrl",
            username = "username",
            updateDate = currentTimeMock
        )
        val dbos = listOf(dbo)

        val usersFlow = flowOf(dbos)

        coEvery { getCurrentTime() } returns currentTimeMock
        coEvery { dao.getOldestRegisterUpdated() } returns null
        coEvery { service.getUsers() } returns dtos
        coEvery { dao.deleteAll() } just runs
        coEvery { dao.insert(dbos) } returns listOf(0)
        coEvery { mapper.mapToDbo(dto, currentTimeMock) } returns dbo
        coEvery { dao.getUsers() } returns usersFlow
        coEvery { mapper.mapToModel(dbo) } returns model

        val actualFlow = userRepository.getUsers()
        actualFlow.collect { actual ->
            Assert.assertEquals(expected, actual)
        }

        coVerify(exactly = 1) {
            getCurrentTime()
            dao.getOldestRegisterUpdated()
            service.getUsers()
            dao.deleteAll()
            dao.insert(dbos)
            mapper.mapToDbo(dto, currentTimeMock)
            dao.getUsers()
            mapper.mapToModel(dbo)
        }

        confirmVerified(service, dao, mapper, getCurrentTime)
    }

    @Test
    fun `should get users from local cache`() = runBlocking {
        val model = UserModel(
            id = "id",
            name = "name",
            imageUrl = "imageUrl",
            username = "username"
        )

        val expected = listOf(model)

        val dbo = UserDBO(
            id = "id",
            name = "name",
            imageUrl = "imageUrl",
            username = "username",
            updateDate = currentTimeMock
        )
        val dbos = listOf(dbo)

        val usersFlow = flowOf(dbos)

        val oldestUpdatedRegister = 999999970001

        coEvery { getCurrentTime() } returns currentTimeMock
        coEvery { dao.getOldestRegisterUpdated() } returns oldestUpdatedRegister
        coEvery { dao.getUsers() } returns usersFlow
        coEvery { mapper.mapToModel(dbo) } returns model

        val actualFlow = userRepository.getUsers()
        actualFlow.collect { actual ->
            Assert.assertEquals(expected, actual)
        }

        coVerify(exactly = 1) {
            getCurrentTime()
            dao.getOldestRegisterUpdated()
            dao.getUsers()
            mapper.mapToModel(dbo)
        }

        confirmVerified(service, dao, mapper, getCurrentTime)
    }

    @Test
    fun `should check cache validity and update local cache`() = runBlocking {
        val model = UserModel(
            id = "id",
            name = "name",
            imageUrl = "imageUrl",
            username = "username"
        )

        val expected = listOf(model)

        val dto = UserDTO(
            id = "id",
            name = "name",
            imageUrl = "imageUrl",
            username = "username"
        )
        val dtos = listOf(dto)

        val dbo = UserDBO(
            id = "id",
            name = "name",
            imageUrl = "imageUrl",
            username = "username",
            updateDate = currentTimeMock
        )
        val dbos = listOf(dbo)

        val usersFlow = flowOf(dbos)

        val oldestUpdatedRegister = 999999970000

        coEvery { getCurrentTime() } returns currentTimeMock
        coEvery { dao.getOldestRegisterUpdated() } returns oldestUpdatedRegister
        coEvery { service.getUsers() } returns dtos
        coEvery { dao.deleteAll() } just runs
        coEvery { dao.insert(dbos) } returns listOf(0)
        coEvery { mapper.mapToDbo(dto, currentTimeMock) } returns dbo
        coEvery { dao.getUsers() } returns usersFlow
        coEvery { mapper.mapToModel(dbo) } returns model

        val actualFlow = userRepository.getUsers()
        actualFlow.collect { actual ->
            Assert.assertEquals(expected, actual)
        }

        coVerify(exactly = 1) {
            getCurrentTime()
            dao.getOldestRegisterUpdated()
            service.getUsers()
            dao.deleteAll()
            dao.insert(dbos)
            mapper.mapToDbo(dto, currentTimeMock)
            dao.getUsers()
            mapper.mapToModel(dbo)
        }

        confirmVerified(service, dao, mapper, getCurrentTime)
    }

}
