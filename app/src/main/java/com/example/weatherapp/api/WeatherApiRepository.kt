package com.example.weatherapp.api

import com.example.weatherapp.data.NearLocation
import com.example.weatherapp.other.Resource
import retrofit2.Response
import retrofit2.http.Query

interface WeatherApiRepository {


    suspend fun getNearLocation(latlong:String) : Resource<NearLocation>

}