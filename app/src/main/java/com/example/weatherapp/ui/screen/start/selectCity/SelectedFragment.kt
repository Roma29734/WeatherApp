package com.example.weatherapp.ui.screen.start.selectCity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.weatherapp.R
import com.example.weatherapp.base.BaseFragment
import com.example.weatherapp.databinding.FragmentSelectedBinding
import com.example.weatherapp.utils.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectedFragment : BaseFragment<FragmentSelectedBinding>(FragmentSelectedBinding::inflate) {

    private val viewModel: SelectedViewModel by viewModels()
    private val adapter by lazy {  SelectedAdapter(viewModel::addToDataBase) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setVisibility(true)
        binding.recyclerView.adapter = adapter

        binding.SearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let { viewModel.searchWeather(it) }
                setVisibility(false)
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                p0?.let { viewModel.searchWeather(it) }
                setVisibility(false)
                return false
            }
        })
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchResult.collectLatest { result ->
                    when(result.loadState) {
                        LoadState.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        LoadState.ERROR -> {
                            binding.progressBar.visibility = View.INVISIBLE
                        }
                        LoadState.SUCCESS -> {
                            binding.progressBar.visibility = View.INVISIBLE
                            result.successState?.let { adapter.setSearch(it) }
                        }
                    }
                }
            }
        }
    }

    fun setVisibility(status: Boolean) {
        if(status) {
            binding.imageView9.visibility = View.VISIBLE
            binding.textView8.visibility = View.VISIBLE
        } else {
            binding.imageView9.visibility = View.INVISIBLE
            binding.textView8.visibility = View.INVISIBLE
        }
    }
}