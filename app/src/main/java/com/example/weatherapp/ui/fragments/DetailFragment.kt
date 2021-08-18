package com.example.weatherapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.R
import com.example.weatherapp.databinding.DetailFragmentBinding
import com.vmadalin.easypermissions.EasyPermissions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.detail_fragment) {



   private lateinit var binding: DetailFragmentBinding
   private val args : DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = DetailFragmentBinding.bind(view)


    }


}