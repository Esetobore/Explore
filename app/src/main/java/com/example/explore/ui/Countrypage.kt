package com.example.explore.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.explore.MainActivity
import com.example.explore.R
import com.example.explore.adapter.ExploreAdapter
import com.example.explore.databinding.FragmentCountrypageBinding
import com.example.explore.util.Resource
import com.example.explore.viewmodel.ExploreViewModel
import kotlinx.android.synthetic.main.country.*
import kotlinx.android.synthetic.main.fragment_countrypage.*
import retrofit2.Response


class Countrypage : Fragment() {
    private var _binding: FragmentCountrypageBinding? = null
    private val binding get() = _binding!!
     lateinit var viewModel: ExploreViewModel
     lateinit var exploreAdapter: ExploreAdapter
     val TAG = "CountryPage"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    { _binding = FragmentCountrypageBinding.inflate(inflater, container, false)
        return binding.root }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()

         exploreAdapter.setOnItemClickListener {
             val bundle = Bundle().apply {
                 putSerializable("explore",it)
             }
             findNavController().navigate(
                 R.id.action_countrypage_to_countryDetail,
                 bundle
             )
         }


         viewModel.exploreAll.observe(viewLifecycleOwner, Observer {
             when(it){
                 is Resource.Success ->{
                        it.data?.let {exploreResponse ->
                            exploreAdapter.diff.submitList(exploreResponse)
                        }
                 }
                     is Resource.Error ->{
                         it.message?.let { message ->
                             Log.e(TAG,"Error$message")
                         }
                     }
                 is Resource.Loading ->{

                 }
             }
         })
                filter.setOnClickListener {

                }

    }
    private fun setupRecyclerView(){
        exploreAdapter = ExploreAdapter()
        binding.countryList.apply {
            adapter = exploreAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}

