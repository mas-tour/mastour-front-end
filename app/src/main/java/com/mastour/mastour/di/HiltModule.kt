package com.mastour.mastour.di

import com.mastour.mastour.data.remote.ImgurApiConfig
import com.mastour.mastour.data.remote.ImgurApiService
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
    fun provideImgurApiService() : ImgurApiService = ImgurApiConfig.getApiService()

    @Provides
    @Singleton
    fun provideRepository(
        masTourApiService: MasTourApiService,
        imgurApiService : ImgurApiService
    ) = Repository(masTourApiService, imgurApiService)
}