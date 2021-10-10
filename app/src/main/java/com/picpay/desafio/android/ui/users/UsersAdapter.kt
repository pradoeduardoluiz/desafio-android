package com.picpay.desafio.android.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ListItemUserBinding

class UsersAdapter : ListAdapter<UsersState.User, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val binding = ListItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return UserViewHolder(binding)
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val item = getItem(position) ?: return
    if (holder is UserViewHolder) holder.bind(item)
  }

  private inner class UserViewHolder(
    private val binding: ListItemUserBinding
  ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: UsersState.User) {
      with(binding) {
        name.text = user.name
        username.text = user.username
        picture.load(user.imageUrl) {
          transformations(CircleCropTransformation())
          placeholder(R.drawable.ic_round_account_circle)
          error(R.drawable.ic_round_account_circle)
        }
      }
    }
  }

  companion object {
    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UsersState.User>() {
      override fun areItemsTheSame(
        oldItem: UsersState.User,
        newItem: UsersState.User
      ): Boolean = oldItem.id == newItem.id

      override fun areContentsTheSame(
        oldItem: UsersState.User,
        newItem: UsersState.User
      ): Boolean = oldItem == newItem
    }
  }

}

