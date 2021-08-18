package com.example.weatherapp.ui.fragments

import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.adapter.NearLocationAdapter
import com.example.weatherapp.databinding.MainFragmentBinding
import com.example.weatherapp.other.Resource
import com.example.weatherapp.other.constant
import com.example.weatherapp.ui.viewmodels.SharedViewModel
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*




@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.main_fragment) {


    lateinit var viewmodel : SharedViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding : MainFragmentBinding
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var geocoder : Geocoder
    private lateinit var recyclerViewAdapter: NearLocationAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var city : String
    private lateinit var coordinat : String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = MainFragmentBinding.bind(view)
        initilazeRecyclerViewAdapter()
        binding.progressBarHome.visibility = View.INVISIBLE

        viewmodel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        geocoder = Geocoder(requireContext(),Locale.getDefault())
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        getCurrentLocation()


        subscribeToLocationListObservers()
        subscribeToTheCityDetail()
        susbcribeToCityTempreture()





    }




    @SuppressLint("MissingPermission")
    fun getCurrentLocation(){
          fusedLocationClient.lastLocation.addOnSuccessListener {
              location ->
              //location objesi uygulamada veya başka bir uygulamadadan alınan en son locationa göre çalışıyor.
              if(location !=null){
                  var longitude = location.longitude
                  var latitude = location.latitude
                  city = coordinatToCity(longitude,latitude)
                  coordinat =latitude.toString()+","+longitude.toString()
                  viewmodel.getLocations(coordinat)
              }
              //Eğer en son belirlen konum yoksa null dönüyor örn: Telefonu kapatıp açtığımızda bu sebeple yeniden location isteği atmamız gerekiyor.
              else{
                 getLocationUpdates()
                  startLocationUpdates()

              }


          }

    }
    private fun getLocationUpdates() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        locationRequest = LocationRequest()
        locationRequest.interval = 50000
        locationRequest.fastestInterval = 50000
        locationRequest.smallestDisplacement = 170f
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return

                if (locationResult.locations.isNotEmpty()) {

                    val location =
                        locationResult.lastLocation

                    coordinat =location.latitude.toString()+","+location.longitude.toString()
                    viewmodel.getLocations(coordinat)
                    city = coordinatToCity(location.longitude,location.latitude)

                }


            }
        }
    }
    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,null
        )
    }
    private fun coordinatToCity(longitude : Double, latitude : Double) : kotlin.String{
        val city = geocoder.getFromLocation(latitude,longitude,1)


        city.get(0).apply {
            binding.cityText.text = this.locality+"-"+this.adminArea
            binding.countryText.text = this.countryName

            viewmodel.getCityDetail(this.locality)
        }

        return city.toString()
    }
    fun initilazeRecyclerViewAdapter(){
        recyclerViewAdapter = NearLocationAdapter(requireView(),listOf(),constant.homeFragmentCode)
        linearLayoutManager = LinearLayoutManager(requireContext())
        binding.nearLocationRecyclerView.layoutManager = linearLayoutManager
        binding.nearLocationRecyclerView.adapter = recyclerViewAdapter
    }



    fun subscribeToLocationListObservers(){
        viewmodel.locationList.observe(requireActivity()){
            when(it){
                is Resource.Loading -> {
                    binding.progressBarHome.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.progressBarHome.visibility = View.INVISIBLE

                }
                is Resource.Success -> {

                    binding.progressBarHome.visibility = View.INVISIBLE

                     it?.data?.let {
                        recyclerViewAdapter.nearLocationList = it
                         recyclerViewAdapter.notifyDataSetChanged()
                    }

                }
            }
        }
    }

    fun subscribeToTheCityDetail(){
        viewmodel.cityDetail.observe(requireActivity()){
            when(it){
                is Resource.Error -> {
                }
                is Resource.Success -> {
                    viewmodel.getCurretTempretureOfGivenCity(it.data?.get(0)?.woeid.toString())

                }
            }
        }
    }

    fun susbcribeToCityTempreture(){
        viewmodel.tempretureList.observe(requireActivity()){
            when(it){
                is Resource.Error -> {
                     Toast.makeText(requireContext(),"Couldn't acces to tempreture data",Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                   binding.temprutureText.text = it.data?.get(0)?.the_temp.toString()+"°C"
                }
            }
        }
    }

}