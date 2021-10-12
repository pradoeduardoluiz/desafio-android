package com.picpay.desafio.android.ui.users

sealed class UsersIntention {
  object Initialize : UsersIntention()
  object Refresh : UsersIntention()
}
