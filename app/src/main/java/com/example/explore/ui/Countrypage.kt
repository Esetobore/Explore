package com.example.explore.ui

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.explore.MainActivity
import com.example.explore.R
import com.example.explore.adapter.ExploreAdapter
import com.example.explore.api.ExploreResponseItem
import com.example.explore.databinding.FragmentCountrypageBinding
import com.example.explore.util.ExploreItem
import com.example.explore.util.Resource
import com.example.explore.viewmodel.ExploreViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_countrypage.*


class Countrypage : Fragment() {
    private var _binding: FragmentCountrypageBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: ExploreViewModel
    lateinit var exploreAdapter: ExploreAdapter
    var systemMode = false
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
             }
         })
                filter_btn.setOnClickListener {
                    viewFilterDialog()
                }

                lang_Btn.setOnClickListener {
                    viewLanguageDialog()
                }
                mode.setOnClickListener {
                    systemMode= !systemMode
                    if (systemMode) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        mode.setImageResource(R.drawable.ic_dark)
                        ic_circular.setImageResource(R.drawable.ic_circular_dark)
                        ic_filter.setImageResource(R.drawable.ic_filter_dark)

                    }
                    else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        mode.setImageResource(R.drawable.ic_light)
                        ic_circular.setImageResource(R.drawable.ic_circular)
                        ic_filter.setImageResource(R.drawable.ic_filter)
                    }
                }
         if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
             mode.setImageResource(R.drawable.ic_dark)
             ic_circular.setImageResource(R.drawable.ic_circular_dark)
             ic_filter.setImageResource(R.drawable.ic_filter_dark)
         }else{
             mode.setImageResource(R.drawable.ic_light)
             ic_circular.setImageResource(R.drawable.ic_circular)
             ic_filter.setImageResource(R.drawable.ic_filter)
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
    private fun setUpSearch(items: List<ExploreResponseItem>) {
       etSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
           androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    val itemQuery = setUpSearchCollector(items, query)
                    Log.d("search query", "$itemQuery")
                    if (itemQuery.isNotEmpty()) {

                        exploreAdapter.submitList(itemQuery)
                    } else {
                        Snackbar.make(
                            requireView(),
                            "ERROR! check your query and retry",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    return true
                } ?: Log.d("search query", "No query entered")
                Snackbar.make(
                    requireView(),
                    "ERROR!No query entered check your query and retry",
                    Snackbar.LENGTH_LONG
                ).show()
                return true

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

    }

    private fun setUpSearchCollector(items: List<ExploreResponseItem>, query: String): List<ExploreResponseItem> {
        return items.filter { mappedItems ->
            mappedItems.name?.common?.lowercase()
                ?.contains(query) ?: false
        }
    }



}



