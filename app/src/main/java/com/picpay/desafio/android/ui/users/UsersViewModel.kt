package com.picpay.desafio.android.ui.users

import android.util.Log
import com.picpay.desafio.android.domain.usecases.user.GetUsersUseCase
import com.picpay.desafio.android.shared.android.dispatchers.DispatchersProvider
import com.picpay.desafio.android.shared.android.mvi.StateViewModelImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
  private val getUsersUseCase: GetUsersUseCase,
  private val mapper: UserMapper,
  dispatchersProvider: DispatchersProvider,
  @UsersStateQualifier initialState: UsersState
) : StateViewModelImpl<UsersState, UsersIntention>(
  initialState = initialState,
  dispatchersProvider = dispatchersProvider
), UsersContract.ViewModel {

  private var users = mutableListOf<UsersState.User>()

  override suspend fun handleIntentions(intention: UsersIntention) {
    when (intention) {
      UsersIntention.Initialize -> initialize()
      UsersIntention.Refresh -> refresh()
    }
  }

  private suspend fun initialize() {
    getUsers(forceRefresh = false)
  }

  private suspend fun refresh() {
    users.clear()
    getUsers(forceRefresh = true)
  }

  private suspend fun getUsers(forceRefresh: Boolean) {
    updateState { copy(isLoading = true) }
    runCatching { getUsersUseCase(GetUsersUseCase.Params(forceRefresh)) }
      .onSuccess { result ->
        result.collectLatest { users ->
          this.users = users.map(mapper::mapToState).toMutableList()
          updateState {
            copy(
              isLoading = false,
              users = this@UsersViewModel.users
            )
          }
        }
      }
      .onFailure { exception ->
        updateState { copy(isLoading = false) }
        Log.d(TAG, "getUsers: ${exception.localizedMessage}")
      }
  }

  private companion object {
    const val TAG = "UsersViewModel"
  }

}
