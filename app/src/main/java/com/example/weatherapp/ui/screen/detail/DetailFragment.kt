package com.example.weatherapp.ui.screen.detail


import android.net.Network
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

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<DetailFragmentArgs>()
    private val viewModel by viewModels<DetailViewModel>()
    private val network by lazy { context?.let { NetworkState(it) } }
    private val dayAdapter = DetailDayAdapter()
    private val sevenDayAdapter = DetailSevenDayAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

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
                            val local = Weather(0, it.current.temp_c, it.current.condition.text, it.location.name, false)
                           viewModel.addFavData(local)
                        }
                        Toast.makeText(context, "Добавленно в избранное", Toast.LENGTH_SHORT).show()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}