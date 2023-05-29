package com.example.chatwiseassignment.di

import com.example.chatwiseassignment.data.models.Album
import com.example.chatwiseassignment.data.retrofit.AlbumAPI
import com.example.chatwiseassignment.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun providesAlbumAPI(retrofit: Retrofit) : AlbumAPI {
        return retrofit.create(AlbumAPI::class.java)
    }
}