package com.example.weatherapp.ui.screen.favourite


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.databinding.CardFavouriteWeatherBinding
import com.example.weatherapp.utils.toCelsiusString

class FavouriteAdapter: RecyclerView.Adapter<FavouriteAdapter.MyViewHolder>() {

    private var listFavouriteCity = emptyList<Weather>()

    inner class MyViewHolder (val binding: CardFavouriteWeatherBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(CardFavouriteWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val lisstPositions = listFavouriteCity[position]

        holder.binding.textName.text = lisstPositions.location
        holder.binding.textDegree.text = lisstPositions.degree.toCelsiusString()
    }

    override fun getItemCount(): Int {
        return listFavouriteCity.size
    }

    fun setFavouriteCity(list: List<Weather>) {
        listFavouriteCity = list
        notifyDataSetChanged()
    }
}