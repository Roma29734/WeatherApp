package com.example.weatherapp.ui.screen.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.model.getSevenDayCity.Forecastday
import com.example.weatherapp.databinding.SevenDayCardRowBinding
import com.example.weatherapp.utils.toCelsiusString

class DetailSevenDayAdapter: RecyclerView.Adapter<DetailSevenDayAdapter.MyViewHolder>() {

    private var dayList = emptyList<Forecastday>()

    inner class MyViewHolder (val binding: SevenDayCardRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return  MyViewHolder(SevenDayCardRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dayPositionsList = dayList[position]
        val textDate = dayPositionsList.date
        val date = "${textDate[8]}${textDate[9]}"
        holder.binding.textDate.text = date
        holder.binding.textMaxDegree.text = dayPositionsList.day.maxtemp_c.toCelsiusString()
        holder.binding.textMinDegree.text = dayPositionsList.day.mintemp_c.toCelsiusString()
    }
    override fun getItemCount(): Int {
        return dayList.size
    }
    fun setSevenDay(list: List<Forecastday>) {
        dayList = list
        notifyDataSetChanged()
    }
}