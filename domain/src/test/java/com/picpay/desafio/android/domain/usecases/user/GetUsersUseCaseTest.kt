package com.picpay.desafio.android.domain.usecases.user

import com.picpay.desafio.android.domain.models.UserModel
import com.picpay.desafio.android.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetUsersUseCaseTest {

    private val userRepository = mockk<UserRepository>()
    private lateinit var getUsersUseCase: GetUsersUseCase

    @Before
    fun `setup test`() {
        getUsersUseCase = GetUsersUseCase(
            userRepository = userRepository
        )
        Assert.assertNotNull(getUsersUseCase)
    }

    @Test
    fun `should get users on repository`() = runBlocking {
        val expected = listOf(
            UserModel(
                id = "id",
                name = "name",
                imageUrl = "imageUrl",
                username = "username"
            ),
            UserModel(
                id = "id2",
                name = "name2",
                imageUrl = "imageUrl2",
                username = "username2"
            )
        )

        val usersFlow = flowOf(expected)

        coEvery { userRepository.getUsers(forceRefresh = false) } returns usersFlow

        getUsersUseCase(GetUsersUseCase.Params(forceRefresh = false)).collect { actual ->
            Assert.assertEquals(expected, actual)
        }

        coVerify(exactly = 1) { userRepository.getUsers(forceRefresh = false) }

        confirmVerified(userRepository)
    }

}
