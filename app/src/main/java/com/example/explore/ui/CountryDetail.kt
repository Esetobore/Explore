package com.example.explore.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.explore.R
import com.example.explore.databinding.FragmentCountryDetailBinding
import com.example.explore.databinding.FragmentCountrypageBinding
import com.example.explore.viewmodel.ExploreViewModel


class CountryDetail : Fragment() {
    private var _binding: FragmentCountryDetailBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: ExploreViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCountryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}