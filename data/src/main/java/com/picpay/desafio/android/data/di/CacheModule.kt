package com.picpay.desafio.android.data.di

import android.content.Context
import com.picpay.desafio.android.data.source.cache.DatabaseHelper
import com.picpay.desafio.android.data.source.cache.PicPayDatabase
import com.picpay.desafio.android.data.source.cache.dao.UserDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Provides
    @Singleton
    fun provideDatabaseHelper(@ApplicationContext context: Context): PicPayDatabase =
        DatabaseHelper(context).buildDataBase()

    @Provides
    @Singleton
    fun provideUserDAO(database: PicPayDatabase): UserDAO = database.userDao()
}
