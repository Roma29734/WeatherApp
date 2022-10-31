package com.example.weatherapp.ui.screen.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.model.getSevenDayCity.Hour
import com.example.weatherapp.databinding.CardWeatherTodayBinding
import com.example.weatherapp.utils.toCelsiusString

class MainAdapter: RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    private var listTodayWeather = emptyList<Hour>()

    inner class MyViewHolder (val binding: CardWeatherTodayBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(CardWeatherTodayBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val todayPositions = listTodayWeather[position]
        holder.binding.textDegree.text = todayPositions.temp_c.toCelsiusString()
        holder.binding.textTime.text = todayPositions.time
    }

    override fun getItemCount(): Int {
        return listTodayWeather.size
    }

    fun setTodayWeather(list: List<Hour>) {
        listTodayWeather = list
        notifyDataSetChanged()
    }
}