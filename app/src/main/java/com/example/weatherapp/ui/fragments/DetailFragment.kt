package com.example.weatherapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.R
import com.example.weatherapp.databinding.DetailFragmentBinding
import com.example.weatherapp.other.Resource
import com.example.weatherapp.ui.viewmodels.DetailFragmentViewModel
import com.example.weatherapp.ui.viewmodels.SharedViewModel
import com.vmadalin.easypermissions.EasyPermissions
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime


@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.detail_fragment) {



    private lateinit var binding: DetailFragmentBinding
    lateinit var viewmodel : DetailFragmentViewModel
    private val args : DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = ViewModelProvider(requireActivity()).get(DetailFragmentViewModel::class.java)
        binding = DetailFragmentBinding.bind(view)
        binding.progressBarDetail.visibility = View.INVISIBLE
        Log.d("deneme",args.woid)
        viewmodel.getCurretTempretureOfGivenCity(args.woid)
        binding.tvCityName.text = args.cityName
        subscribeToWeatherList()

    }



    fun subscribeToWeatherList(){
        viewmodel.cityWeatherList.observe(requireActivity()){
            when(it){
                is Resource.Loading -> {
                   binding.progressBarDetail.visibility = View.VISIBLE
                }
                is Resource.Error  -> {
                    binding.progressBarDetail.visibility = View.INVISIBLE
                    Log.d("deneme",it.message.toString())
                }
                is Resource.Success -> {
                    binding.progressBarDetail.visibility = View.INVISIBLE
                    binding.tvDate.text =LocalDateTime.now().dayOfWeek.toString()+", "+LocalDateTime.now().toLocalDate().toString()

                     binding.tvWeather.text = it.data?.get(0)?.the_temp.toString()+"°C"

                }
            }
        }

    }


}