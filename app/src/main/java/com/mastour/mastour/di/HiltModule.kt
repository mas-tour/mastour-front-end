package com.mastour.mastour.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.mastour.mastour.data.preferences.SessionPreferences
import com.mastour.mastour.data.remote.ImgurApiConfig
import com.mastour.mastour.data.remote.ImgurApiService
import com.mastour.mastour.data.remote.MasTourApiConfig
import com.mastour.mastour.data.remote.MasTourApiService
import com.mastour.mastour.data.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {
    @Provides
    fun provideMasTourApiService(): MasTourApiService = MasTourApiConfig.getApiService()

    @Provides
    fun provideImgurApiService(): ImgurApiService = ImgurApiConfig.getApiService()

    @Provides
    @Singleton
    fun provideRepository(
        @ApplicationContext context: Context,
        masTourApiService: MasTourApiService,
        imgurApiService: ImgurApiService,
        preferences: SessionPreferences
    ) = Repository(preferences, masTourApiService, imgurApiService, context)

    @Provides
    @Singleton
    fun provideDataStorePreferences(@ApplicationContext context: Context): DataStore<Preferences> =
        context.dataStore

    @Provides
    @Singleton
    fun provideSessionPreferences(dataStore: DataStore<Preferences>) = SessionPreferences(dataStore)
}