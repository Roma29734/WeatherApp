package com.example.weatherapp.ui.screen.main

import android.graphics.Color.BLACK
import android.graphics.Color.WHITE
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.weatherapp.R
import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.weatherapp.utils.NetworkState
import com.example.weatherapp.utils.showShackBarNoInternetConnection
import com.example.weatherapp.utils.toCelsiusString
import com.google.android.material.snackbar.Snackbar

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel by viewModels<MainViewModel>()
    lateinit var binding: FragmentMainBinding
    private lateinit var Network: NetworkState
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        Network = context?.let { NetworkState(it) }!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Network.observe(viewLifecycleOwner) {state ->
            if(state) {
                binding.progressBar.visibility = View.INVISIBLE
                viewModel.getOneCity()
                viewModel.oneCity.observe(viewLifecycleOwner) { response ->
                    response?.body()?.let {
                        val weatherLocal =  Weather(1, it.current.temp_c, it.current.condition.text, it.location.name)
                        viewModel.updateLocalData(weatherLocal)
                    }
                }
                viewModel.localCity.observe(viewLifecycleOwner) {response ->
                    if(response != null) {
                        binding.textDegree.text = response.degree.toCelsiusString()
                        binding.textStatus.text = response.condition
                        binding.tollBar.textLocatoin.text = response.location
                    }
                }
            } else {
                binding.progressBar.visibility = View.VISIBLE
                viewModel.localCity.observe(viewLifecycleOwner) {response ->
                    if(response != null) {
                        binding.textDegree.text = response.degree.toCelsiusString()
                        binding.textStatus.text = response.condition
                        binding.tollBar.textLocatoin.text = response.location
                    }
                }
                showShackBarNoInternetConnection(view)
            }
        }
        binding.tollBar.searchImg.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_searchFragment)
        }
    }
}