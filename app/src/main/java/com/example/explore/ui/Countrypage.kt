package com.example.explore.ui

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import kotlinx.android.synthetic.main.filter_view.*
import kotlinx.android.synthetic.main.fragment_countrypage.*
import kotlinx.android.synthetic.main.language_view.*


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
                             Log.e(TAG,"Error: $message")
                             Toast.makeText(activity, "Error: $message",Toast.LENGTH_SHORT).show()
                         }
                     }
                 is Resource.Loading ->{

                 }
             }
         })
                filter_btn.setOnClickListener {
                    viewFilterDialog()
                    cancel_btn.setOnClickListener {
                        dismissDialog()
                    }
                }
                lang_Btn.setOnClickListener {
                        viewLanguageDialog()
                    cancel_btn_lang.setOnClickListener {
                        dismissDialog()
                    }
                }

    }
    private fun setupRecyclerView(){
        exploreAdapter = ExploreAdapter()
        binding.countryList.apply {
            adapter = exploreAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
    private fun viewFilterDialog() {
        val dialog = activity?.let { Dialog(it) }
        if (dialog != null) {
            dialog.setContentView(R.layout.filter_view)
            dialog.show()
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.window!!.setGravity(Gravity.BOTTOM)
        }
    }
    private fun viewLanguageDialog() {
        val dialog = activity?.let { Dialog(it) }
        if (dialog != null) {
            dialog.setContentView(R.layout.language_view)
            dialog.show()
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.window!!.setGravity(Gravity.BOTTOM)
        }
    }
    private fun dismissDialog() {
        activity?.let { Dialog(it) }?.dismiss()
    }

}

