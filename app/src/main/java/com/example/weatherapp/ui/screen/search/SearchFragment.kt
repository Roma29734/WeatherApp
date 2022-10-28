package com.example.weatherapp.ui.screen.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.databinding.FragmentSearchBinding
import com.example.weatherapp.utils.NetworkState
import com.example.weatherapp.utils.showShackBarNoInternetConnection
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModels<SearchViewModel>()
    private val adapter = SearchAdapter()
    private lateinit var Network: NetworkState
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.recyclerSearch.adapter = adapter
        binding.recyclerSearch.layoutManager = LinearLayoutManager(requireContext())
        Network = context?.let { NetworkState(it) }!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Network.observe(viewLifecycleOwner) {state->
            if(state) {
                // Обрабатываю запрос в searchView
                binding.progressBar.visibility = View.INVISIBLE
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

                // Вывожу в ресайкл результат
                viewModel.searchResult.observe(viewLifecycleOwner) {result ->
                    result?.body()?.let { adapter.setSearch(it) }
                }

                binding.tabBarSearch.imageButton.setOnClickListener {
                    Navigation.findNavController(view).popBackStack()
                }
            } else {
                binding.progressBar.visibility = View.VISIBLE
                showShackBarNoInternetConnection(view)
            }
        }

    }

}