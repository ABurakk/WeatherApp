package com.example.weatherapp.api

import com.example.weatherapp.data.CityItem
import com.example.weatherapp.data.ConsolidatedWeather
import com.example.weatherapp.data.NearLocationItem
import com.example.weatherapp.other.Resource
import retrofit2.Response
import java.lang.Exception

class DefaultWeatherApiRepository(private val service : WeatherApiService) : WeatherApiRepository {


    override suspend fun getNearLocations(latlong: String): Resource<List<NearLocationItem>> {
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

    override suspend fun getCityDetailByName(cityName: String): Resource<List<CityItem>> {
        try {
            val response = service.getCityDetailByName(cityName)

            if(response.isSuccessful){

                val body = response.body()
                return Resource.Success(body)

            }

            return Resource.Error(response.message())

        }catch (e:Exception){
            return Resource.Error("Couldn't reach the server")
        }
    }

    override suspend fun getCityFiveDayTempretureDataByWoid(woid: String): Resource<List<ConsolidatedWeather>> {
        try {

            val response = service.getCityTempretureDataByWoid(woid)

            if(response.isSuccessful){
                val body = response.body()
                return Resource.Success(body?.consolidated_weather)
            }

            return Resource.Error(response.message())

        }catch (e:Exception){
            return Resource.Error(e.message.toString())
        }
    }
}