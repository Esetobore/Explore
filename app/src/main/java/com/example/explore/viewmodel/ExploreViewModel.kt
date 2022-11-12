package com.example.explore.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.explore.api.ExploreApi
import com.example.explore.api.ExploreResponse
import com.example.explore.util.Constant.Companion.BASE_URL
import com.example.explore.util.Resource
import com.example.explore.util.Retrofit.Companion.api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ExploreViewModel() : ViewModel() {

    val exploreAll: MutableLiveData< Resource<ExploreResponse>> = MutableLiveData()
init {
    getExploreAll()
}
    fun getExploreAll() = viewModelScope.launch {
        exploreAll. postValue(Resource.Loading())
        val response = api.getExploreResponse()
        exploreAll.postValue(handleExploreResponse(response))
    }

        private fun handleExploreResponse( response: Response<ExploreResponse>): Resource<ExploreResponse>{
            if(response.isSuccessful){
                response.body()?.let {
                    return Resource.Success(it)
                }
            }
            return Resource.Error(response.message())
        }


//    var job : Job? = null
//    val countries = MutableLiveData<List<ExploreResponse>>()
//    val countryError = MutableLiveData<Boolean>()
//    val countryLoading = MutableLiveData<Boolean>()
//
//
//
//       countryLoading.value = true
//       job = viewModelScope.launch(context = Dispatchers.IO) {
//           val response = retrofit.getExploreResponse()
//           withContext(Dispatchers.Main) {
//               if(response.isSuccessful) {
//                   response.body()?.let {
//                       countries.value = listOf(it)
//                       countryLoading.value = false
//                   }
//               } else {
//                   countryError.value = true
//                   countryLoading.value = false
//               }
//           }
//       }
//}
//    override fun onCleared() {
//        super.onCleared()
//        job?.cancel()
//    }

}