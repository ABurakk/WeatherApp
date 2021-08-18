package com.example.weatherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.ConsolidatedWeather
import com.example.weatherapp.databinding.DaysItemBinding
import com.example.weatherapp.other.constant
import com.example.weatherapp.other.downloadImage

class DetailFragmentWeeksDayWeatherAdapter(var list : List<ConsolidatedWeather>)
    :RecyclerView.Adapter<DetailFragmentWeeksDayWeatherAdapter.ViewHolder>(){


    class ViewHolder (binding: DaysItemBinding) : RecyclerView.ViewHolder(binding.root){
        var imgWeather = binding.weatherImage
        var txtDate = binding.txtDate
        var txtWeather = binding.txtWeather
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DaysItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list.get(position).let {
            holder.txtDate.text = it.applicable_date
            holder.imgWeather.downloadImage(constant.IMAGE_BASE_URL+it.weather_state_abbr+".png")
            holder.txtWeather.text = it.the_temp.toString()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}