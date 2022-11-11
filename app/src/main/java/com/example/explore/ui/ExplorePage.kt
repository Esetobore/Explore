package com.example.explore.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.explore.R
import com.example.explore.viewmodel.ExploreViewModel

class ExplorePage : AppCompatActivity() {
    lateinit var viewModel: ExploreViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore_page)

    }
}