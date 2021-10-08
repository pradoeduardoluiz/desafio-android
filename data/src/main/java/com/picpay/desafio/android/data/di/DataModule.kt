package com.picpay.desafio.android.data.di

import com.picpay.desafio.android.data.UserMapper
import com.picpay.desafio.android.data.repository.UserRepositoryImpl
import com.picpay.desafio.android.data.source.cache.dao.UserDAO
import com.picpay.desafio.android.data.source.remote.service.PicPayService
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.shared.kotlin.GetCurrentTime
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideUserMapper() = UserMapper()

    @Provides
    @Singleton
    fun provideUserRepository(
        picPayService: PicPayService,
        userDAO: UserDAO,
        userMapper: UserMapper,
        getCurrentTime: GetCurrentTime
    ): UserRepository = UserRepositoryImpl(
        service = picPayService,
        dao = userDAO,
        mapper = userMapper,
        getCurrentTime = getCurrentTime
    )
}
