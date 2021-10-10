package com.picpay.desafio.android.ui.users

data class UsersState(
  val isLoading: Boolean = false,
  val users: List<Holder> = emptyList()
) {
  sealed class Holder(open val id: String, val type: Int) {

    data class User(
      override val id: String,
      val name: String,
      val imageUrl: String,
      val username: String
    ) : Holder(id, VIEW_HOLDER_ID) {
      companion object {
        const val VIEW_HOLDER_ID = 1
      }
    }

    class Loading : Holder("-1", VIEW_HOLDER_ID) {
      companion object {
        const val VIEW_HOLDER_ID = -1
      }
    }
  }
}
