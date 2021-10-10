package com.picpay.desafio.android.di

import com.picpay.desafio.android.navigation.Navigator
import com.picpay.desafio.android.navigation.NavigatorImpl
import com.picpay.desafio.android.shared.android.dispatchers.AppDispatchersProvider
import com.picpay.desafio.android.shared.android.dispatchers.DispatchersProvider
import com.picpay.desafio.android.ui.users.UserMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Provides
  @Singleton
  fun providerDispatchersProvider(): DispatchersProvider = AppDispatchersProvider()

  @Provides
  @Singleton
  fun providerNavigator(
    dispatchersProvider: DispatchersProvider
  ): Navigator = NavigatorImpl(dispatchersProvider)

  @Provides
  @Singleton
  fun provideUserMapper() = UserMapper()

}
