package com.example.weatherapp.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.data.model.getSevenDayCity.Forecastday
import com.example.weatherapp.databinding.CardFavouriteWeatherBinding
import com.example.weatherapp.databinding.SevenDayCardRowBinding
import com.example.weatherapp.ui.screen.main.MainAdapterSevenDeay
import com.example.weatherapp.utils.toCelsiusString
import kotlin.coroutines.coroutineContext

class ListFeaturedAdapter(
    private val clickListener: OnItemClickListener,
    private val deleteClick: (weather: Weather) -> Unit,
): RecyclerView.Adapter<ListFeaturedAdapter.MyViewHolder>() {

    var dayList = emptyList<Weather>()

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
        }
    }
    override fun getItemCount(): Int {
        return dayList.size
    }

    fun deleteCity(position: Int) {
        val posDeleteItem = dayList[position]

        if(!posDeleteItem.main) {
            deleteClick(posDeleteItem)
        }
    }

    fun setFavCity(list: List<Weather>) {
        dayList = list
        notifyDataSetChanged()
    }
}