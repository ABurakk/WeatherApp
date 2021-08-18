package com.example.weatherapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.adapter.NearLocationAdapter
import com.example.weatherapp.databinding.NearLocationFragmentBinding
import com.example.weatherapp.other.Resource
import com.example.weatherapp.other.constant
import com.example.weatherapp.ui.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NearLocationsFragment : Fragment(R.layout.near_location_fragment) {



    private lateinit var binding : NearLocationFragmentBinding
    private lateinit var recyclerViewAdapter : NearLocationAdapter
    private lateinit var linearLayoutManager : LinearLayoutManager
    private lateinit var viewmodel : SharedViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = NearLocationFragmentBinding.bind(view)
        viewmodel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)





        binding.progressBar2.visibility = View.INVISIBLE

        initilazeRecyclerViewAdapter()

        subscribeToLocationListObservers()


    }


    fun initilazeRecyclerViewAdapter(){
        recyclerViewAdapter = NearLocationAdapter(requireView(),listOf(),constant.listFragmentcode)
        linearLayoutManager = LinearLayoutManager(requireContext())
        binding.rvNearLocations.layoutManager = linearLayoutManager
        binding.rvNearLocations.adapter = recyclerViewAdapter
    }


    fun subscribeToLocationListObservers(){
        viewmodel.locationList.observe(requireActivity()){
            when(it){
                is Resource.Loading -> {
                    binding.progressBar2.visibility = View.VISIBLE
                }
                is Resource.Error -> {

                    binding.progressBar2.visibility = View.INVISIBLE

                }
                is Resource.Success -> {

                    binding.progressBar2.visibility = View.INVISIBLE

                    it?.data?.let {
                        recyclerViewAdapter.nearLocationList = it
                        recyclerViewAdapter.notifyDataSetChanged()
                    }

                }
            }
        }
    }

}