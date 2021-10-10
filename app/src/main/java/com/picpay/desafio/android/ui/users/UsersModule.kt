package com.picpay.desafio.android.ui.users

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Qualifier

@Module
@InstallIn(FragmentComponent::class, ViewModelComponent::class)
object UsersModule {

  @Provides
  @FragmentScoped
  fun provideUsersAdapter() = UsersAdapter()

  @Provides
  @ViewModelScoped
  @UsersStateQualifier
  fun provideInitialState(): UsersState = UsersState()
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UsersStateQualifier
