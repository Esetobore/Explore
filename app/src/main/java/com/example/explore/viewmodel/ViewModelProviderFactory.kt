package com.example.explore.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelProviderFactory(val appVMPF : Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(moelClass: Class<T>): T {
            return ExploreViewModel(appVMPF) as T
    }
}