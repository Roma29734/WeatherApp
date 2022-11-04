package com.example.weatherapp.ui.screen.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentMainBinding
import com.example.weatherapp.ui.view.ListFeaturedCities
import com.example.weatherapp.utils.LoadState
import dagger.hilt.android.AndroidEntryPoint
import com.example.weatherapp.utils.NetworkState
import com.example.weatherapp.utils.showShackBarNoInternetConnection
import com.example.weatherapp.utils.toCelsiusString
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val adapter = MainAdapter()
    private val adapterSevenDeay = MainAdapterSevenDeay()
    private lateinit var Network: NetworkState
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        Network = context?.let { NetworkState(it) }!!
        binding.recyclerWeatherToday.adapter = adapter
        binding.recyclerSevenDayWeather.adapter = adapterSevenDeay
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mainState.map { it.selectedCity }.distinctUntilChanged().collectLatest {
                    it?.let { it1 -> setUi(it1.location, it.condition, it.degree) }
                }
            }
        }

        Network.observe(viewLifecycleOwner) {state ->
            if(state) {
                viewModel.getWeatherConnectYes()
                lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.mainState.collectLatest { uiState ->
                            when(uiState.loadState) {
                                LoadState.LOADING -> {
                                    binding.progressBar.visibility = View.VISIBLE
                                }
                                LoadState.SUCCESS ->{
                                    binding.progressBar.visibility = View.INVISIBLE
                                    uiState.successState?.let {
                                        setUiOk(it.location.name, it.current.condition.text,
                                            it.current.temp_c, it.current.feelslike_c,
                                            it.current.wind_kph, it.forecast.forecastday[0].astro.sunrise, it.forecast.forecastday[0].astro.sunset)
                                        adapter.setTodayWeather(it.forecast.forecastday[0].hour)
                                        adapterSevenDeay.setSevenDay(it.forecast.forecastday)
                                    }
                                }
                                LoadState.ERROR -> {
                                    Toast.makeText(context, "Возникла ошибка, перезагрузите приложение", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    } }

            } else {
                _binding!!.progressBar.visibility = View.VISIBLE
                showShackBarNoInternetConnection(view)
            }
        }
        binding.tollBar.searchImg.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_searchFragment)
        }
        binding.tollBar.burgerImg.setOnClickListener {
//            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_favouriteFragment)
            val bottomSheet = ListFeaturedCities(viewModel::getWeatherConnectYes)
            bottomSheet.show(childFragmentManager, ListFeaturedCities.BOTTOM_SHEET_CITIES_TAG)
        }
    }

    fun setUi(textLocation: String, textStatus: String, temp: Double) {
        binding.tollBar.textLocatoin.text = textLocation
        binding.textStatus.text = textStatus
        binding.textDegree.text = temp.toCelsiusString()
    }

    fun setUiOk(
        textLocation: String, textStatus: String,
        temp: Double, liveDegree: Double, wind: Double,
        sunRice: String, sunSet: String,
    ) {
        binding.tollBar.textLocatoin.text = textLocation
        binding.textStatus.text = textStatus
        binding.textDegree.text = temp.toCelsiusString()
        binding.newsOfTheDay.textLive.text = liveDegree.toCelsiusString()
        binding.newsOfTheDay.textWind.text = wind.toString()
        binding.newsOfTheDay.textSunRice.text = sunRice
        binding.newsOfTheDay.textSunSet.text = sunSet
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}