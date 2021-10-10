package com.picpay.desafio.android.ui.users

data class UsersState(
  val isLoading: Boolean = false,
  val users: List<User> = emptyList()
) {
  data class User(
    val id: String,
    val name: String,
    val imageUrl: String,
    val username: String
  )
}
