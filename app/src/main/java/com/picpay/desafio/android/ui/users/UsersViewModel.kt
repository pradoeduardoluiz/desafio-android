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

  private var users = mutableListOf<UsersState.Holder>()
  private val loading = mutableListOf(UsersState.Holder.Loading())

  override suspend fun handleIntentions(intention: UsersIntention) {
    when (intention) {
      UsersIntention.Initialize -> initialize()
      UsersIntention.Refresh -> refresh()
    }
  }

  private suspend fun initialize() {
    if (this.users.isEmpty()) getUsers(forceRefresh = false)
  }

  private suspend fun refresh() {
    this.users = mutableListOf()
    getUsers(forceRefresh = true)
  }

  private suspend fun getUsers(forceRefresh: Boolean) {
    loading()
    runCatching { getUsersUseCase(GetUsersUseCase.Params(forceRefresh)) }
      .onSuccess { result ->
        result.collectLatest { users ->
          this.users = users.map(mapper::mapToState).toMutableList()
          update(this.users)
        }
      }
      .onFailure { exception ->
        update(this.users)
        Log.d(TAG, "getUsers: ${exception.localizedMessage}")
      }
  }

  private suspend fun loading() {
    val users = this.users + this.loading
    updateState { copy(users = users) }
  }

  private suspend fun update(users: List<UsersState.Holder>) {
    updateState { copy(users = users) }
  }

  private companion object {
    const val TAG = "UsersViewModel"
  }

}
