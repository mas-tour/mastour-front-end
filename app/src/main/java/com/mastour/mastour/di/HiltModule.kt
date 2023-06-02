package com.mastour.mastour.di

import com.mastour.mastour.data.remote.MasTourApiConfig
import com.mastour.mastour.data.remote.MasTourApiService
import com.mastour.mastour.data.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HiltModule {
    @Provides
    fun provideMasTourApiService() : MasTourApiService = MasTourApiConfig.getApiService()

    @Provides
    @Singleton
    fun provideRepository(masTourApiService: MasTourApiService) = Repository(masTourApiService)
}