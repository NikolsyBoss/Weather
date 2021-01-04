package com.test.weather.ui.home

import android.content.Context.MODE_PRIVATE
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


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )

        homeViewModel = ViewModelProvider(this).get(
            HomeViewModel::class.java
        )
        binding.viewModel = homeViewModel
        binding.lifecycleOwner = this
        return binding.root
    }




}