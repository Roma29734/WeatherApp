package com.example.weatherapp.ui.view

import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.weatherapp.R
import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.databinding.FragmentListFeaturedCitiesBinding
import com.example.weatherapp.ui.screen.main.MainFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class ListFeaturedCities(
    private val update: () -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: FragmentListFeaturedCitiesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ListFeaturedCitiesViewModel>()
    private val adapter: ListFeaturedAdapter = ListFeaturedAdapter(object :ListFeaturedAdapter.OnItemClickListener{
        override fun onCityClick(city: Weather) {
            viewModel.setClick(city)
            this@ListFeaturedCities.dismiss()
            update()
        }
    })
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListFeaturedCitiesBinding.inflate(inflater, container, false)
        binding.rvCities.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.cities.observe(viewLifecycleOwner) {
            adapter.setFavCity(it)
        }
    }

    companion object {
        const val BOTTOM_SHEET_CITIES_TAG = "BOTTOM_SHEET_CITIES"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}