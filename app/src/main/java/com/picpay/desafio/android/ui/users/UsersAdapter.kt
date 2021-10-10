package com.picpay.desafio.android.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import coil.load
import coil.transform.CircleCropTransformation
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ListItemLoadingBinding
import com.picpay.desafio.android.databinding.ListItemUserBinding

class UsersAdapter : ListAdapter<UsersState.Holder, UsersAdapter.ViewHolder>(DIFF_CALLBACK) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return when (viewType) {
      UsersState.Holder.User.VIEW_HOLDER_ID -> {
        val binding =
          ListItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        UserViewHolder(binding)
      }
      UsersState.Holder.Loading.VIEW_HOLDER_ID -> {
        val binding =
          ListItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        LoadingViewHolder(binding)
      }
      else -> throw IllegalArgumentException()
    }
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = getItem(position) ?: return
    when (holder) {
      is UserViewHolder -> holder.bind(item as UsersState.Holder.User)
      is LoadingViewHolder -> holder.bind()
    }
  }

  override fun getItemViewType(position: Int): Int = getItem(position).type

  abstract class ViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

  private inner class UserViewHolder(
    private val binding: ListItemUserBinding
  ) : ViewHolder(binding) {

    fun bind(user: UsersState.Holder.User) {
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

  private inner class LoadingViewHolder(
    private val binding: ListItemLoadingBinding
  ) : ViewHolder(binding) {

    fun bind() {
      with(binding) {
        progressBar.isVisible = true
      }
    }
  }

  companion object {
    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UsersState.Holder>() {
      override fun areItemsTheSame(
        oldItem: UsersState.Holder,
        newItem: UsersState.Holder
      ): Boolean = oldItem.id == newItem.id

      override fun areContentsTheSame(
        oldItem: UsersState.Holder,
        newItem: UsersState.Holder
      ): Boolean = oldItem == newItem
    }
  }

}

