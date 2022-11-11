package com.example.explore.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.explore.api.ExploreApi
import com.example.explore.api.ExploreResponse
import com.example.explore.util.Constant.Companion.BASE_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ExploreViewModel() : ViewModel() {

    var job : Job? = null
    val countries = MutableLiveData<List<ExploreResponse>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

   fun getExploreData(){
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ExploreApi::class.java)

       countryLoading.value = true
       job = viewModelScope.launch(context = Dispatchers.IO) {
           val response = retrofit.getExploreResponse()
           withContext(Dispatchers.Main) {
               if(response.isSuccessful) {
                   response.body()?.let {
                       countries.value = listOf(it)
                       countryLoading.value = false
                   }
               } else {
                   countryError.value = true
                   countryLoading.value = false
               }
           }
       }
}
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}