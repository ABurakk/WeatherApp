package com.example.weatherapp.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.api.WeatherApiRepository
import com.example.weatherapp.data.ConsolidatedWeather
import com.example.weatherapp.other.Resource
import kotlinx.coroutines.launch

class DetailFragmentViewModel @ViewModelInject constructor(
    val repository: WeatherApiRepository
): ViewModel() {


    private val _cityWeatherList = MutableLiveData<Resource<List<ConsolidatedWeather>>>()

    val cityWeatherList : LiveData<Resource<List<ConsolidatedWeather>>> = _cityWeatherList


    fun getCurretTempretureOfGivenCity(woid:String){
        _cityWeatherList.postValue(Resource.Loading())
        viewModelScope.launch {
            var tempretureList = repository.getCityFiveDayTempretureDataByWoid(woid)
            _cityWeatherList.postValue(tempretureList)


        }
    }

}