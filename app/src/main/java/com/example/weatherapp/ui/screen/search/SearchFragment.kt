package com.example.weatherapp.ui.screen.search

import android.net.Network
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.base.BaseFragment
import com.example.weatherapp.databinding.FragmentSearchBinding
import com.example.weatherapp.utils.LoadState
import com.example.weatherapp.utils.NetworkState
import com.example.weatherapp.utils.showShackBarNoInternetConnection
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel by viewModels<SearchViewModel>()
    private val adapter = SearchAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Настройка ресайкла
        binding.recyclerSearch.adapter = adapter
        binding.recyclerSearch.layoutManager = LinearLayoutManager(requireContext())

        binding.tabBarSearch.imageButton.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }

        binding.tabBarSearch.SearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        p0?.let { viewModel.searchWeather(it) }
                        return false
                    }
                    override fun onQueryTextChange(p0: String?): Boolean {
                        p0?.let { viewModel.searchWeather(it) }
                        return false
                    }
                })

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchResult.collectLatest { uiState ->
                    when (uiState.loadState) {
                        LoadState.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        LoadState.ERROR -> {
                            binding.progressBar.visibility = View.INVISIBLE
                        }
                        LoadState.SUCCESS -> {
                            binding.progressBar.visibility = View.INVISIBLE
                            uiState.successState?.let { adapter.setSearch(it) }
                        }
                    }
                }
            }
        }
    }
}