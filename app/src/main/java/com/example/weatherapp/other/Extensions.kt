package com.example.weatherapp.other

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.weatherapp.ui.activities.GlideApp

fun ImageView.downloadImage(url:String){

    GlideApp.with(context)
        .load(url)
        .into(this)

}