package com.picpay.desafio.android.ui.users

data class UsersState(
  val items: List<Holder> = emptyList()
) {
  sealed class Holder(open val id: String, val type: Int) {

    data class User(
      override val id: String,
      val name: String,
      val imageUrl: String,
      val username: String
    ) : Holder(id, USER_VIEW_HOLDER_TYPE)

    object Loading : Holder("-1", LOADING_VIEW_HOLDER_TYPE)

    object Error : Holder("-2", ERROR_VIEW_HOLDER_TYPE)

    companion object {
      const val USER_VIEW_HOLDER_TYPE = 1
      const val LOADING_VIEW_HOLDER_TYPE = 2
      const val ERROR_VIEW_HOLDER_TYPE = 3
    }
  }

}
