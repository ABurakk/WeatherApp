package com.example.weatherapp.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.api.WeatherApiRepository
import com.example.weatherapp.data.CityItem
import com.example.weatherapp.data.ConsolidatedWeather
import com.example.weatherapp.data.NearLocationItem
import com.example.weatherapp.other.Resource
import kotlinx.coroutines.launch

class HomeFragmentViewModel @ViewModelInject constructor(
    private val repository: WeatherApiRepository
) : ViewModel() {


    private var _locationsLiveData = MutableLiveData<Resource<List<NearLocationItem>>>()

    var locationList : LiveData<Resource<List<NearLocationItem>>> = _locationsLiveData

    private var _cityDetail = MutableLiveData<Resource<List<CityItem>>>()

    var cityDetail :LiveData<Resource<List<CityItem>>> = _cityDetail

    private var _tempretureList = MutableLiveData<Resource<List<ConsolidatedWeather>>>()

    var tempretureList : LiveData<Resource<List<ConsolidatedWeather>>> = _tempretureList


    fun getLocations(latlong: String){
        _locationsLiveData.postValue(Resource.Loading())

        viewModelScope.launch {

            _locationsLiveData.postValue(repository.getNearLocations(latlong))

        }
    }


    fun getCurretTempretureOfGivenCity(woid:String){

         viewModelScope.launch {
             var tempretureList = repository.getCityFiveDayTempretureDataByWoid(woid)
             _tempretureList.postValue(tempretureList)


         }
    }

    fun getCityDetail(name:String) {
        viewModelScope.launch {
            var cityDatail = repository.getCityDetailByName(name)
            _cityDetail.postValue(cityDatail)
        }

    }

}