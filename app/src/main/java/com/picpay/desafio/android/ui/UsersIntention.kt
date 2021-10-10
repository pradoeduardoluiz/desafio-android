package com.picpay.desafio.android.ui

sealed class UsersIntention {
  object Initialize : UsersIntention()
  object Refresh : UsersIntention()
}
