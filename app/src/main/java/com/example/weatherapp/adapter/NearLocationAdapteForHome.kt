package com.example.weatherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.NearLocationItem
import com.example.weatherapp.databinding.MainFragmentBinding
import com.example.weatherapp.databinding.NearLocationItemBinding

class NearLocationAdapteForHome(var nearLocationList : List<NearLocationItem>)
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
            holder.cityDistance.text = it.distance.toString()
        }
    }

    override fun getItemCount(): Int {
        return nearLocationList.size
    }

}