package com.example.weatherapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.NearLocationItem
import com.example.weatherapp.databinding.MainFragmentBinding
import com.example.weatherapp.databinding.NearLocationItemBinding

class NearLocationAdapteForHome(var view: View,var nearLocationList : List<NearLocationItem>)
    : RecyclerView.Adapter<NearLocationAdapteForHome.ViewHolder>(){


    class ViewHolder (binding: NearLocationItemBinding) : RecyclerView.ViewHolder(binding.root){

       var cityName = binding.tvLocationName
       var cityDistance  = binding.tvLocationDistance

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(NearLocationItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        nearLocationList.get(position).let {
            holder.cityName.text = it.title
            holder.cityDistance.text = "About "+(it.distance/1000).toString()+" KM from your location"
        }

        holder.itemView.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_nearLocationsFragment)
        }
    }

    override fun getItemCount(): Int {
        return nearLocationList.size
    }

}