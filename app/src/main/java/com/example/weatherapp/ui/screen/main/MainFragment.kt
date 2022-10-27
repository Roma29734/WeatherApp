package com.example.weatherapp.ui.screen.main

import android.graphics.Color.BLACK
import android.graphics.Color.WHITE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.weatherapp.R
import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.weatherapp.utils.NetworkState
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
                viewModel.oneCity.observe(viewLifecycleOwner) { responce ->
                    responce?.body()?.let {
                        binding.textDegree.text = "${it.current.temp_c}°"
                        binding.textStatus.text = it.current.condition.text
                        binding.materialToolbar.title = it.location.name
                        val weatherLocal =  Weather(1, it.current.temp_c.toString(), it.current.condition.text, it.location.name)
                        viewModel.updateLocalData(weatherLocal)
                    }
                }
            } else {
                binding.progressBar.visibility = View.VISIBLE
                viewModel.localCity.observe(viewLifecycleOwner) {responce ->
                    if(responce != null) {
                        binding.textDegree.text = responce.degree
                        binding.textStatus.text = responce.condition
                        binding.materialToolbar.title = responce.location
                    }
                }
                Snackbar.make(view, R.string.no_internet, Snackbar.LENGTH_SHORT)
                    .setTextColor(BLACK)
                    .setBackgroundTint(WHITE)
                    .show()
            }
        }


    }
}