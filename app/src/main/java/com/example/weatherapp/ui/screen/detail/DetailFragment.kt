package com.example.weatherapp.ui.screen.detail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.databinding.FragmentDetailBinding
import com.example.weatherapp.utils.NetworkState
import com.example.weatherapp.utils.showShackBarNoInternetConnection
import com.example.weatherapp.utils.toCelsiusString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()
    private val viewModel by viewModels<DetailViewModel>()
    private lateinit var Network: NetworkState
    private val dayAdapter = DetailDayAdapter()
    private val sevenDayAdapter = DetailSevenDayAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        Network = context?.let { NetworkState(it) }!!
        binding.recyclerWeatherDay.adapter = dayAdapter
        binding.recyclerSevenDayWeather.adapter = sevenDayAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Network.observe(viewLifecycleOwner) {state ->
            if(state) {
                binding.progressBar.visibility = View.INVISIBLE
                viewModel.getCity(args.searchResult.url)
                viewModel.oneCity.observe(viewLifecycleOwner) {date ->
                    date?.body()?.let {
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
                            val local = Weather(1, it.current.temp_c, it.current.condition.text, it.location.name)
                           viewModel.updateLocalData(local)
                        }
                        Toast.makeText(context, "Добавленно на главный экран", Toast.LENGTH_SHORT).show()
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
}