package com.picpay.desafio.android.ui.users

import com.picpay.desafio.android.shared.android.dispatchers.DispatchersProvider
import com.picpay.desafio.android.shared.android.mvi.StateViewModelImpl
import javax.inject.Inject

class UsersViewModel @Inject constructor(
  dispatchersProvider: DispatchersProvider,
  @UsersStateQualifier initialState: UsersState
) : StateViewModelImpl<UsersState, UsersIntention>(
  initialState = initialState,
  dispatchersProvider = dispatchersProvider
), UsersContract.ViewModel {

  override suspend fun handleIntentions(intention: UsersIntention) {
    TODO("Not yet implemented")
  }

}
