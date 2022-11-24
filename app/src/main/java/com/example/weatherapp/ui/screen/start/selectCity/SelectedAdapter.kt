package com.example.weatherapp.ui.screen.start.selectCity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.data.model.search.Search
import com.example.weatherapp.data.model.search.SearchItem
import com.example.weatherapp.databinding.CardSearchRowBinding
import com.example.weatherapp.ui.screen.search.SearchAdapter

class SelectedAdapter(
    val addToDataBase: (weather: Weather) -> Unit
): RecyclerView.Adapter<SelectedAdapter.MyViewHolder>() {

    private var searchResult = emptyList<SearchItem>()

    inner class MyViewHolder(val binding: CardSearchRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(CardSearchRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val positionResult = searchResult[position]
        holder.binding.textLocation.text = positionResult.name
        holder.binding.textCountry.text = positionResult.country

        holder.binding.card.setOnClickListener {
            val weather = Weather(0,0.0,"",positionResult.name, true)
            addToDataBase(weather)
            Navigation.findNavController(holder.itemView).navigate(R.id.action_selectedFragment_to_mainFragment)
        }
    }

    override fun getItemCount(): Int {
        return searchResult.size
    }

    fun setSearch(search: Search) {
        searchResult = search
        notifyDataSetChanged()
    }
}