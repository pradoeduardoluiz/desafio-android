package com.picpay.desafio.android.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.UsersFragmentBinding
import com.picpay.desafio.android.shared.android.extensions.viewBinding
import com.picpay.desafio.android.shared.android.extensions.watch

class UsersFragment : Fragment(R.layout.users_fragment) {

  private val binding by viewBinding(UsersFragmentBinding::bind)
  private val viewModel: UsersContract.ViewModel by viewModels<UsersViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    bindRecyclerView()
    bindInputs()
    bindOutputs()
  }

  private fun bindInputs() = with(binding) {

  }

  private fun bindOutputs() {
    watch(viewModel.state) { state ->

    }
  }

  private fun bindRecyclerView() {

  }

}
