package com.picpay.desafio.android.ui.users

import com.picpay.desafio.android.domain.models.UserModel
import org.junit.Assert
import org.junit.Test

class UserMapperTest {

  @Test
  fun `should mapper model to state`() {
    val mapper = UserMapper()

    val expected = UsersState.Holder.User(
      id = "id",
      name = "name",
      imageUrl = "imageUrl",
      username = "username"
    )

    val current = UserModel(
      id = "id",
      name = "name",
      imageUrl = "imageUrl",
      username = "username"
    )

    val actual = mapper.mapToState(current)
    Assert.assertEquals(expected, actual)
  }

}
