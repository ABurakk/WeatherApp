package com.example.weatherapp.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.api.WeatherApiRepository
import com.example.weatherapp.data.NearLocationItem
import com.example.weatherapp.other.Resource
import kotlinx.coroutines.launch

class HomeFragmentViewModel @ViewModelInject constructor(
    private val repository: WeatherApiRepository
) : ViewModel() {


    private var _locationsLiveData = MutableLiveData<Resource<List<NearLocationItem>>>()

    var locationList : LiveData<Resource<List<NearLocationItem>>> = _locationsLiveData


    fun getLocations(latlong: String){
        _locationsLiveData.postValue(Resource.Loading())

        viewModelScope.launch {

            _locationsLiveData.postValue(repository.getNearLocations(latlong))

        }
    }

}