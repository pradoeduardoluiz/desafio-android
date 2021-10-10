package com.picpay.desafio.android.ui

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
object UsersModule {

  @Provides
  @ViewModelScoped
  @UsersStateQualifier
  fun provideInitialState(): UsersState = UsersState()
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UsersStateQualifier
