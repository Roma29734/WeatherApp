package com.example.weatherapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.databinding.FragmentListFeaturedCitiesBinding
import com.example.weatherapp.utils.SwipeToDeleteCallback
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListFeaturedCitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter: ListFeaturedAdapter = ListFeaturedAdapter(object :ListFeaturedAdapter.OnItemClickListener{
            override fun onCityClick(city: Weather) {
                viewModel.setClick(city)
                this@ListFeaturedCities.dismiss()
                update()
            }
        }, viewModel::deleteCity)

        binding.rvCities.adapter = adapter
        binding.rvCities.apply {
            val swipeDelete = object : SwipeToDeleteCallback() {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    adapter.deleteCity(viewHolder.adapterPosition)
                }

                override fun getMovementFlags(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ): Int {
                    val aboba = adapter.dayList[viewHolder.adapterPosition]
                    return if(!aboba.main) {
                        val dragFlags = ItemTouchHelper.LEFT
                        val swipeFlags = ItemTouchHelper.START
                        makeMovementFlags(dragFlags, swipeFlags)
                //                        return super.getMovementFlags(recyclerView, viewHolder)
                    } else {
                        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.RIGHT
                        val swipeFlags = 0
                        makeMovementFlags(dragFlags, swipeFlags)
                    }
                }
            }

            val touchHelper = ItemTouchHelper(swipeDelete)
            touchHelper.attachToRecyclerView(binding.rvCities)
        }
        viewModel.cities.observe(viewLifecycleOwner) {
            adapter.setFavCity(it)
        }
    }

    companion object {
        const val BOTTOM_SHEET_CITIES_TAG = "BOTTOM_SHEET_CITIES"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}