package com.example.weatherapp.data

data class NearLocationItem(
    val distance: Int,
    val latt_long: String,
    val location_type: String,
    val title: String,
    val woeid: Int
)