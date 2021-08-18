package com.example.weatherapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.adapter.DetailFragmentWeeksDayWeatherAdapter
import com.example.weatherapp.adapter.NearLocationAdapter
import com.example.weatherapp.databinding.DetailFragmentBinding
import com.example.weatherapp.other.Resource
import com.example.weatherapp.other.constant
import com.example.weatherapp.other.downloadImage
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
    private lateinit var recyclerViewAdapter: DetailFragmentWeeksDayWeatherAdapter
    private lateinit var linearLayoutManager : LinearLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = ViewModelProvider(requireActivity()).get(DetailFragmentViewModel::class.java)
        binding = DetailFragmentBinding.bind(view)
        binding.progressBarDetail.visibility = View.INVISIBLE
        viewmodel.getCurretTempretureOfGivenCity(args.woid)
        binding.tvCityName.text = args.cityName
        initilazeRecyclerViewAdapter()
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
                    var currentDay = it.data?.get(0)
                    binding.progressBarDetail.visibility = View.INVISIBLE
                    binding.tvDate.text =LocalDateTime.now().dayOfWeek.toString()+", "+LocalDateTime.now().toLocalDate().toString()
                    binding.tvWeather.text = currentDay?.the_temp.toString()+"°C"
                    binding.imageView.downloadImage(constant.IMAGE_BASE_URL+currentDay?.weather_state_abbr+".png")
                    it.data?.let {
                        recyclerViewAdapter.list=it
                        recyclerViewAdapter.notifyDataSetChanged()
                    }

                }
            }
        }

    }


    fun initilazeRecyclerViewAdapter(){
        recyclerViewAdapter = DetailFragmentWeeksDayWeatherAdapter(listOf())
        linearLayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.rvDays.layoutManager = linearLayoutManager
        binding.rvDays.adapter = recyclerViewAdapter
    }


}