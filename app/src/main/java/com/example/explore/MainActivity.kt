package com.example.explore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.explore.adapter.ExploreAdapter
import com.example.explore.api.RetrofitInstance
import com.example.explore.api.apidataclass.ExploreResponseItem
import com.example.explore.ui.ExploreViewModel
import com.example.explore.util.Resource

class MainActivity : AppCompatActivity() {
    val TAG = "MAIN ACTIVITY"
    private lateinit var exploreRecyclerView: RecyclerView
    lateinit var exploreAdapter : ExploreAdapter
    lateinit var viewModel: ExploreViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        exploreRecyclerView = findViewById(R.id.country_list)
        setupRecyclerView()
        viewModel.exploreAll.observe(this, Observer {
            when (it){
                is Resource.Success->{
                    it.data?.let {exreponse->
                        exploreAdapter.diff.submitList(exreponse)
                    }
                }
                is Resource.Error->{
                    it.message?.let {message->
                        Log.e(TAG,"an error occures: $message" )
                    }
                }
            }
        })

    }
    private fun setupRecyclerView(){
        exploreAdapter = ExploreAdapter()
        exploreRecyclerView.apply {
            adapter = exploreAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}
