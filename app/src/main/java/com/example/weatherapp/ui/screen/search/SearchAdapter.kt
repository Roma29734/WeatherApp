package com.example.weatherapp.ui.screen.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.model.search.SearchItem
import com.example.weatherapp.databinding.CardSearchRowBinding

class SearchAdapter: RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

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
            val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(positionResult)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return searchResult.size
    }

    fun setSearch(search: List<SearchItem>) {
        searchResult = search
        notifyDataSetChanged()
    }
}