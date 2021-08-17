package com.example.weatherapp.di

import com.example.weatherapp.api.DefaultWeatherApiRepository
import com.example.weatherapp.api.WeatherApiRepository
import com.example.weatherapp.api.WeatherApiService
import com.example.weatherapp.other.constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }


    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .baseUrl(constant.BASE_URL)
        .build()



    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit) : WeatherApiService = retrofit.create(WeatherApiService::class.java)


    @Singleton
    @Provides
    fun providesRepository(weatherService : WeatherApiService) = DefaultWeatherApiRepository(weatherService) as WeatherApiRepository

}