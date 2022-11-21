package com.example.weatherapp.ui.screen.start.selectCity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.weatherapp.R
import com.example.weatherapp.base.BaseFragment
import com.example.weatherapp.databinding.FragmentSelectedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectedFragment : BaseFragment<FragmentSelectedBinding>(FragmentSelectedBinding::inflate) {

    private val viewModel: SelectedViewModel by viewModels()
    private val adapter by lazy {  SelectedAdapter(viewModel::addToDataBase) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setVisibility(true)
        binding.recyclerView.adapter = adapter

        network?.observe(viewLifecycleOwner) { connect ->
            if(connect) {
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

                viewModel.searchResult.observe(viewLifecycleOwner) {result ->
                    result?.body()?.let { adapter.setSearch(it) }
                }
            } else {
                Toast.makeText(context, "Проебали интернет", Toast.LENGTH_SHORT).show()
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