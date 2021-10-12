package com.picpay.desafio.android.ui.users

import com.picpay.desafio.android.domain.models.UserModel
import com.picpay.desafio.android.domain.usecases.user.GetUsersUseCase
import com.picpay.desafio.android.shared.android.dispatchers.DispatchersProvider
import com.picpay.desafio.android.shared.test.ViewModelTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Test

class UsersViewModelTest : ViewModelTest() {

  private val getUsersUseCase = mockk<GetUsersUseCase>()
  private val mapper = mockk<UserMapper>()

  private lateinit var viewModel: UsersContract.ViewModel

  override fun setupSubject(dispatchersProvider: DispatchersProvider) {
    viewModel = UsersViewModel(
      getUsersUseCase = getUsersUseCase,
      mapper = mapper,
      dispatchersProvider = dispatchersProvider,
      initialState = UsersState()
    )
    Assert.assertNotNull(viewModel)
  }

  override fun confirmAllVerified() {
    confirmVerified(getUsersUseCase, mapper)
  }

  @Test
  fun `should initialize and get users`() {
    val state = UsersState.Holder.User(
      id = "id",
      name = "name",
      imageUrl = "imageUrl",
      username = "username"
    )

    val expected = listOf(state)

    val model = UserModel(
      id = "id",
      name = "name",
      imageUrl = "imageUrl",
      username = "username"
    )
    val modelFlow = flowOf(listOf(model))

    coEvery { getUsersUseCase(GetUsersUseCase.Params(forceRefresh = false)) } returns modelFlow
    coEvery { mapper.mapToState(model) } returns state

    viewModel.publish(UsersIntention.Initialize)
    val actual = viewModel.state.value.items
    Assert.assertEquals(expected, actual)

    coVerify(exactly = 1) {
      getUsersUseCase(GetUsersUseCase.Params(forceRefresh = false))
      mapper.mapToState(model)
    }

    confirmAllVerified()
  }

  @Test
  fun `should initialize and throw exception`() {
    val exception = IllegalArgumentException()

    val state = UsersState.Holder.Error
    val expected = listOf(state)

    coEvery { getUsersUseCase(GetUsersUseCase.Params(forceRefresh = false)) } throws exception

    viewModel.publish(UsersIntention.Initialize)
    val actual = viewModel.state.value.items
    Assert.assertEquals(expected, actual)

    coVerify(exactly = 1) {
      getUsersUseCase(GetUsersUseCase.Params(forceRefresh = false))
    }

    confirmAllVerified()
  }

  @Test
  fun `should refresh and get users`() {
    val state1 = UsersState.Holder.User(
      id = "id",
      name = "name",
      imageUrl = "imageUrl",
      username = "username"
    )
    val state2 = UsersState.Holder.User(
      id = "id2",
      name = "name2",
      imageUrl = "imageUrl2",
      username = "username2"
    )

    val expected = listOf(state1, state2)

    val model1 = UserModel(
      id = "id",
      name = "name",
      imageUrl = "imageUrl",
      username = "username"
    )

    val model2 = UserModel(
      id = "id2",
      name = "name2",
      imageUrl = "imageUrl2",
      username = "username2"
    )

    val modelFlow = flowOf(listOf(model1, model2))

    coEvery { getUsersUseCase(GetUsersUseCase.Params(forceRefresh = false)) } returns modelFlow
    coEvery { mapper.mapToState(model1) } returns state1
    coEvery { mapper.mapToState(model2) } returns state2

    viewModel.publish(UsersIntention.Initialize)
    val actual = viewModel.state.value.items
    Assert.assertEquals(expected, actual)

    coVerify(exactly = 1) {
      getUsersUseCase(GetUsersUseCase.Params(forceRefresh = false))
      mapper.mapToState(model1)
      mapper.mapToState(model2)
    }

    confirmAllVerified()
  }

}
