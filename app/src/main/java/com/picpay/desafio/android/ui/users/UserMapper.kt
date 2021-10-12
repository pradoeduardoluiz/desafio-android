package com.picpay.desafio.android.ui.users

import com.picpay.desafio.android.domain.models.UserModel

class UserMapper {
  fun mapToState(user: UserModel) = UsersState.Holder.User(
    id = user.id,
    name = user.name,
    imageUrl = user.imageUrl,
    username = user.username
  )
}
