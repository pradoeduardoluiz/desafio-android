package com.picpay.desafio.android.ui.users

import com.picpay.desafio.android.shared.android.mvi.StateViewModel

interface UsersContract {
  interface ViewModel : StateViewModel<UsersState, UsersIntention>
}
