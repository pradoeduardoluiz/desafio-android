package com.picpay.desafio.android.ui.users

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.UsersFragmentBinding
import com.picpay.desafio.android.shared.android.extensions.viewBinding
import com.picpay.desafio.android.shared.android.extensions.watch
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UsersFragment : Fragment(R.layout.users_fragment) {

  private val binding by viewBinding(UsersFragmentBinding::bind)
  private val viewModel: UsersContract.ViewModel by viewModels<UsersViewModel>()

  @Inject
  lateinit var adapter: UsersAdapter

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    bindRecyclerView()
    bindInputs()
    bindOutputs()
    viewModel.publish(UsersIntention.Initialize)
  }

  private fun bindInputs() = with(binding) {
    refresh.setOnRefreshListener {
      viewModel.publish(UsersIntention.Refresh)
      refresh.isRefreshing = false
    }
  }

  private fun bindOutputs() {
    watch(viewModel.state) { state ->
      adapter.submitList(state.items)
    }
  }

  private fun bindRecyclerView() {
    binding.users.adapter = adapter
    adapter.onRetryClick = { viewModel.publish(UsersIntention.Refresh) }
  }

}
