package com.example.composesfo.di

import com.example.composesfo.common.Constants
import com.example.composesfo.data.remote.SFOApi
import com.example.composesfo.data.repository.*
import com.example.composesfo.domain.repository.*
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

    @Provides
    @Singleton
    fun provideCartRepository(api: SFOApi): CartRepository {
        return CartRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideOrderRepository(api: SFOApi): OrderRepository {
        return OrderRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideWalletRepository(api: SFOApi): WalletRepository {
        return WalletRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideFoodCategoryRepository(api: SFOApi): FoodCategoryRepository {
        return FoodCategoryRepositoryImpl(api)
    }
}