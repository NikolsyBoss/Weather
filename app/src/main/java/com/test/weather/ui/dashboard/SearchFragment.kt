package com.test.weather.ui.dashboard

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.test.weather.R
import com.test.weather.databinding.FragmentHomeBinding
import com.test.weather.databinding.FragmentSearchBinding
import com.test.weather.ui.home.HomeFactory
import com.test.weather.ui.home.HomeViewModel

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentSearchBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search,
            container,
            false
        )
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        binding.viewModel = searchViewModel
        binding.lifecycleOwner = this


        return binding.root
    }
}