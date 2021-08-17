package com.example.weatherapp.ui.fragments

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.adapter.NearLocationAdapteForHome
import com.example.weatherapp.data.NearLocation
import com.example.weatherapp.data.NearLocationItem
import com.example.weatherapp.databinding.MainFragmentBinding
import com.google.android.gms.location.*
import com.vmadalin.easypermissions.EasyPermissions
import dagger.hilt.android.AndroidEntryPoint
import java.util.*




@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.main_fragment) {


    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding : MainFragmentBinding
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var geocoder : Geocoder
    private lateinit var recyclerViewAdapter: NearLocationAdapteForHome
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var city : kotlin.String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = MainFragmentBinding.bind(view)

        initilazeRecyclerViewAdapter()

        geocoder = Geocoder(requireContext(),Locale.getDefault())
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())


        var n1 = NearLocationItem(3456,"123","City","Ankara",4432)
        var list = listOf(n1)

        recyclerViewAdapter.nearLocationList = list
        recyclerViewAdapter.notifyDataSetChanged()


        getCurrentLocation()




    }




    @SuppressLint("MissingPermission")
    fun getCurrentLocation(){
        if(checkLocationPermisson()){
          fusedLocationClient.lastLocation.addOnSuccessListener {
              location ->
              //location objesi uygulamada veya başka bir uygulamadadan alınan en son locationa göre çalışıyor.
              if(location !=null){
                  var longitude = location.longitude
                  var latitude = location.latitude
                  city = coordinatToCity(longitude,latitude)

                  Log.d("deneme",city)
              }
              //Eğer en son belirlen konum yoksa null dönüyor örn: Telefonu kapatıp açtığımızda bu sebeple yeniden location isteği atmamız gerekiyor.
              else{
                 getLocationUpdates()
                  startLocationUpdates()

              }


          }





        }


    }



    //
    private fun getLocationUpdates()
    {
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

                   city = coordinatToCity(location.longitude,location.latitude)
                    Log.d("deneme",city)

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


    private fun checkLocationPermisson() = EasyPermissions.
    hasPermissions(requireContext(),android.Manifest.permission.ACCESS_FINE_LOCATION)



    private fun coordinatToCity(longitude : Double, latitude : Double) : kotlin.String{
        val city = geocoder.getFromLocation(latitude,longitude,1)


        city.get(0).apply {
            binding.cityText.text = this.adminArea
            binding.countryText.text = this.countryName
        }

        return city.toString()
    }

    fun initilazeRecyclerViewAdapter(){
        recyclerViewAdapter = NearLocationAdapteForHome(listOf())
        linearLayoutManager = LinearLayoutManager(requireContext())
        binding.nearLocationRecyclerView.layoutManager = linearLayoutManager
        binding.nearLocationRecyclerView.adapter = recyclerViewAdapter
    }

}