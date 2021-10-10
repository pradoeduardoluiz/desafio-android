package com.picpay.desafio.android

import androidx.appcompat.app.AppCompatActivity
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.shared.android.extensions.viewBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

  private val binding by viewBinding(ActivityMainBinding::inflate)

}
