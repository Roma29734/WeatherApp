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
import androidx.lifecycle.createSavedStateHandle
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.R
import com.example.weatherapp.base.BaseFragment
import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.databinding.FragmentDetailBinding
import com.example.weatherapp.utils.NetworkState
import com.example.weatherapp.utils.showShackBarNoInternetConnection
import com.example.weatherapp.utils.toCelsiusString
import dagger.hilt.android.AndroidEntryPoint

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
        network?.observe(viewLifecycleOwner) {state ->
            if(state) {
                binding.progressBar.visibility = View.INVISIBLE
                viewModel.getCity(args.searchResult.url)
                viewModel.oneCity.observe(viewLifecycleOwner) {date ->
                    date?.body()?.let {
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

                binding.tollBar.favouriteButton.setOnClickListener {
                    viewModel.oneCity.observe(viewLifecycleOwner) {date ->
                        date?.body()?.let {
                            if(viewModel.getStateCity(it.location.name)) {
                                val local = Weather(0, it.current.temp_c, it.current.condition.text, it.location.name, false)
                                viewModel.deleteFavData(local)
                                Log.d("searchProblemDelete","Вызвал удаление из фрагмента")
                                Toast.makeText(context, "Удалено из избранного", Toast.LENGTH_SHORT).show()
                            } else {
                                val local = Weather(0, it.current.temp_c, it.current.condition.text, it.location.name, false)
                                viewModel.addFavData(local)
                                Toast.makeText(context, "Добавленно в избранное", Toast.LENGTH_SHORT).show()
                            }
                            setFavButtonImage(it.location.name)
                        }
                    }
                }
            } else {
                binding.progressBar.visibility = View.VISIBLE
                showShackBarNoInternetConnection(view)
            }
        }
        binding.tollBar.imageButton.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }
    }

    fun setFavButtonImage(localName: String) {
        if(!viewModel.getStateCity(localName)) {
            binding.tollBar.favouriteButton.visibility = View.VISIBLE
            binding.tollBar.favouriteButton.setImageResource(R.drawable.ic_favourite_non_set)
        } else {
            binding.tollBar.favouriteButton.visibility = View.GONE
        }
    }
}