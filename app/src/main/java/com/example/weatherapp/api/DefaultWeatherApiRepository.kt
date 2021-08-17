package com.example.weatherapp.api

import com.example.weatherapp.data.NearLocation
import com.example.weatherapp.data.NearLocationItem
import com.example.weatherapp.other.Resource
import retrofit2.Response
import java.lang.Exception

class DefaultWeatherApiRepository(private val service : WeatherApiService) : WeatherApiRepository {
    override suspend fun getNearLocation(latlong: String): Resource<List<NearLocationItem>> {
        try {

            val response = service.getNearLocation(latlong)

            if(response.isSuccessful){
                val responseBody = response.body()
                return Resource.Success(responseBody)
            }

            return Resource.Error(response.message())

        }catch (e:Exception){
            return Resource.Error("Couldn't reach the server")
        }

    }
}