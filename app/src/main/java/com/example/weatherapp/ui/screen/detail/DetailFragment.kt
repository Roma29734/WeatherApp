package com.example.weatherapp.ui.screen.detail


import android.net.Network
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.R
import com.example.weatherapp.base.BaseFragment
import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.databinding.FragmentDetailBinding
import com.example.weatherapp.utils.LoadState
import com.example.weatherapp.utils.NetworkState
import com.example.weatherapp.utils.showShackBarNoInternetConnection
import com.example.weatherapp.utils.toCelsiusString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val args by navArgs<DetailFragmentArgs>()
    private val viewModel by viewModels<DetailViewModel>()
    private val dayAdapter = DetailDayAdapter()
    private val sevenDayAdapter = DetailSevenDayAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Установка ресайкла
        binding.recyclerWeatherDay.adapter = dayAdapter
        binding.recyclerSevenDayWeather.adapter = sevenDayAdapter
        binding.progressBar.visibility = View.INVISIBLE
        viewModel.getCity(args.searchResult.url)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.oneCity.collectLatest { uiState ->

                    when(uiState.loadState) {
                        LoadState.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        LoadState.SUCCESS -> {
                            binding.progressBar.visibility = View.INVISIBLE

                            uiState.successState?.let {
                                setFavButtonImage(it.location.name)
                                binding.textDegree.text = it.current.temp_c.toCelsiusString()
                                binding.textStatus.text = it.current.condition.text
                                binding.tollBar.textLocatoin.text = it.location.name
                                binding.newsOfTheDay.textLive.text = it.current.feelslike_c.toCelsiusString()
                                binding.newsOfTheDay.textWind.text = it.current.wind_kph.toString()
                                binding.newsOfTheDay.textSunRice.text = it.forecast.forecastday[0].astro.sunrise
                                binding.newsOfTheDay.textSunSet.text = it.forecast.forecastday[0].astro.sunset
                                dayAdapter.setTodayWeather(it.forecast.forecastday[0].hour)
                                sevenDayAdapter.setSevenDay(it.forecast.forecastday)
                            }
                        }
                        LoadState.ERROR -> {
                            binding.progressBar.visibility = View.INVISIBLE
                        }
                    }
                }
            }
        }

        binding.tollBar.favouriteButton.setOnClickListener {
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.oneCity.collectLatest { response ->
                        if(!viewModel.getStateCity(response.successState!!.location.name)) {
                            response.successState?.let {
                                setFavButtonImage(it.location.name)
                                val local = Weather(0, it.current.temp_c, it.current.condition.text, it.location.name, false)
                                viewModel.addFavData(local)
                                Toast.makeText(context, "Добавленно в избранное", Toast.LENGTH_SHORT).show()
                                setFavButtonImage(it.location.name)
                            }
                        }
                    }
                }
            }
        }

        binding.tollBar.imageButton.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }
    }

    private fun setFavButtonImage(localName: String) {
        if(!viewModel.getStateCity(localName)) {
            binding.tollBar.favouriteButton.visibility = View.VISIBLE
            binding.tollBar.favouriteButton.setImageResource(R.drawable.ic_favourite_non_set)
        } else {
            binding.tollBar.favouriteButton.visibility = View.GONE
        }
    }
}