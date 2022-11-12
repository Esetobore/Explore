package com.example.explore.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavArgs
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.example.explore.MainActivity
import com.example.explore.R
import com.example.explore.api.ExploreResponse
import com.example.explore.api.ExploreResponseItem
import com.example.explore.databinding.FragmentCountryDetailBinding
import com.example.explore.databinding.FragmentCountrypageBinding
import com.example.explore.viewmodel.ExploreViewModel


class CountryDetailFragment : Fragment() {
    private var _binding: FragmentCountryDetailBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: ExploreViewModel
    private val args : CountryDetailFragmentArgs by navArgs()
    var index = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCountryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        val countryDetail = args.details

        args.details?.let { details ->
            val imageList = mutableListOf<String>()
            details.coatOfArms?.png?.let { imageList.add(it) }
            details.coatOfArms?.svg?.let { imageList.add(it) }
            details.flags?.png?.let { imageList.add(it) }
            details.flags?.svg?.let { imageList.add(it) }
            index = (index+1) % imageList.size

            Log.d("Countries",details.toString())
            binding.apply {
                txtPopulation.text = details.population.toString()
                txtRegion.text = details.region
                txtCapital.text = details.capital.toString()
                txtSubRegion.text = details.subregion
                txtName.text = details.name?.official
                txtCurrency.text = "F: ${details.demonyms?.eng?.f}, M:${details.demonyms?.eng?.m}"
                txtStartOfWeek.text = details.startOfWeek
                txtCoordinates.text = details.latlng.toString()
                txtBorders.text = details.borders?.joinToString(",")
                txtCapitalInfo.text = details.capitalInfo?.latlng?.toString()
                txtDrivingSide.text = details.car?.side
                txtCountryLanguage.text = details.languages.toString()
                txtArea.text = details.area.toString()
            }
        }?: "No data passed"

    }
}