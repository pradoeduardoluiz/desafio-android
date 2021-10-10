package com.picpay.desafio.android

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.navigation.Navigator
import com.picpay.desafio.android.shared.android.extensions.viewBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {

  private val binding by viewBinding(ActivityMainBinding::inflate)

  @Inject
  lateinit var navigator: Navigator

  override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
    super.onCreate(savedInstanceState, persistentState)
    setupNavigation()
  }

  private fun setupNavigation() {
    val navHostFragment = supportFragmentManager
      .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    navigator.bind(navHostFragment.navController, lifecycleScope)
  }

}
