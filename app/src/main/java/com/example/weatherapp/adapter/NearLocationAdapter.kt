package com.example.weatherapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.NearLocationItem
import com.example.weatherapp.databinding.NearLocationItemBinding
import com.example.weatherapp.other.constant
import com.example.weatherapp.ui.fragments.NearLocationsFragmentDirections

class NearLocationAdapter(var view: View, var nearLocationList : List<NearLocationItem>,var fragmentCode:Int)
    : RecyclerView.Adapter<NearLocationAdapter.ViewHolder>(){


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
            if(fragmentCode==constant.listFragmentcode){
                val action = NearLocationsFragmentDirections.actionNearLocationsFragmentToDetailFragment(nearLocationList.get(position).woeid.toString(),nearLocationList.get(position).title)
                Navigation.findNavController(view).navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        if(fragmentCode==constant.homeFragmentCode)
            return nearLocationList.size/2

        return nearLocationList.size
    }

}