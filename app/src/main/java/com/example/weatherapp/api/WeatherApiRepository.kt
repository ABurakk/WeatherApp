package com.example.weatherapp.api

import com.example.weatherapp.data.*
import com.example.weatherapp.other.Resource
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApiRepository {


    suspend fun getNearLocations(latlong:String) : Resource<List<NearLocationItem>>

    suspend fun getCityDetailByName(cityName : String) : Resource<List<CityItem>>

    suspend fun getCityFiveDayTempretureDataByWoid(woid:String) : Resource<List<ConsolidatedWeather>>

}