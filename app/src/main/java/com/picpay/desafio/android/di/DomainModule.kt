package com.picpay.desafio.android.di

import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.domain.usecases.user.GetUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

  @Provides
  @Singleton
  fun providerGetRepositoriesUseCase(usersRepository: UserRepository) =
    GetUsersUseCase(userRepository = usersRepository)

}
