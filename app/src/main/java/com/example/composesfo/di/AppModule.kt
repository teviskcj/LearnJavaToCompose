package com.example.composesfo.di

import com.example.composesfo.common.Constants
import com.example.composesfo.data.remote.SFOApi
import com.example.composesfo.data.repository.FoodRepositoryImpl
import com.example.composesfo.data.repository.UserRepositoryImpl
import com.example.composesfo.domain.repository.FoodRepository
import com.example.composesfo.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSFOApi(): SFOApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SFOApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFoodRepository(api: SFOApi): FoodRepository {
        return FoodRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideUserRepository(api: SFOApi): UserRepository {
        return UserRepositoryImpl(api)
    }
}