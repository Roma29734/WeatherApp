package com.example.weatherapp.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.data.model.getSevenDayCity.Forecastday
import com.example.weatherapp.databinding.CardFavouriteWeatherBinding
import com.example.weatherapp.databinding.SevenDayCardRowBinding
import com.example.weatherapp.ui.screen.main.MainAdapterSevenDeay
import com.example.weatherapp.utils.toCelsiusString

class ListFeaturedAdapter(
    private val clickListener: OnItemClickListener,
    private val deleteClick: (weather: Weather) -> Unit,
): RecyclerView.Adapter<ListFeaturedAdapter.MyViewHolder>() {

    private var dayList = emptyList<Weather>()

    interface OnItemClickListener {
        fun onCityClick(city: Weather)
    }

    inner class MyViewHolder (val binding: CardFavouriteWeatherBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return  MyViewHolder(CardFavouriteWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dayPositionsList = dayList[position]
        holder.binding.textName.text = dayPositionsList.location

        holder.binding.root.setOnClickListener {
            clickListener.onCityClick(dayPositionsList)
        }

        if(dayPositionsList.main)  {
            holder.binding.imageButton2.setImageResource(R.drawable.ic_home)
        } else {
            holder.binding.imageButton2.setOnClickListener {
                deleteClick(dayPositionsList)
            }
        }
    }
    override fun getItemCount(): Int {
        return dayList.size
    }
    fun setFavCity(list: List<Weather>) {
        dayList = list
        notifyDataSetChanged()
    }
}