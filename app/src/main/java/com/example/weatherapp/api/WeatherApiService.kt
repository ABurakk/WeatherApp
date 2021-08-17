package com.example.weatherapp.api

import com.example.weatherapp.data.CityItem
import com.example.weatherapp.data.LocationTempreture
import com.example.weatherapp.data.NearLocation
import com.example.weatherapp.data.NearLocationItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApiService {


    @GET("location/search/")
    suspend fun getNearLocation(@Query("lattlong") latlong:String) : Response<List<NearLocationItem>>

    @GET("location/search/")
    suspend fun getCityDetailByName(@Query("query") cityName : String) : Response<List<CityItem>>

    @GET("location/{woid}")
    suspend fun getCityTempretureDataByWoid(@Path("woid") woid:String) : Response<LocationTempreture>



}