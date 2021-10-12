package com.picpay.desafio.android.di

import com.picpay.desafio.android.shared.kotlin.GetCurrentTime
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedModule {

  @Provides
  @Singleton
  fun providerGetCurrentTime(): GetCurrentTime = GetCurrentTime()

}
