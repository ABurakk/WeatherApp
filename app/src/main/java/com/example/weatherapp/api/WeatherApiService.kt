package com.example.weatherapp.api

import com.example.weatherapp.data.NearLocation
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {


    @GET("location/search/")
    suspend fun getNearLocation(@Query("lattlong") latlong:String) : Response<NearLocation>

}