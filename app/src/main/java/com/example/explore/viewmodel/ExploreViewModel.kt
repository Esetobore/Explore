package com.example.explore.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities.*
import android.os.Build
import android.provider.Contacts.PhonesColumns.TYPE_MOBILE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.explore.api.ExploreApi
import com.example.explore.api.ExploreResponse
import com.example.explore.util.Constant.Companion.BASE_URL
import com.example.explore.util.ExploreApplication
import com.example.explore.util.Resource
import com.example.explore.util.Retrofit.Companion.api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class ExploreViewModel(
    app : Application
) : AndroidViewModel(app) {

    val exploreAll: MutableLiveData< Resource<ExploreResponse>> = MutableLiveData()
init {
    getExploreAll()
}
    fun getExploreAll() = viewModelScope.launch {
        exploreCall()
    }

        private fun handleExploreResponse( response: Response<ExploreResponse>): Resource<ExploreResponse>{
            if(response.isSuccessful){
                response.body()?.let {
                    return Resource.Success(it)
                }
            }
            return Resource.Error(response.message())
        }

    private suspend fun exploreCall(){
        exploreAll.postValue(Resource.Loading())
        try {
            if(checkInternetConnection()){
                val response = api.getExploreResponse()
                exploreAll.postValue(handleExploreResponse(response))
            }else{
                exploreAll.postValue(Resource.Error("No internet Connection"))
            }
        }catch (t:Throwable){
                when(t){
                    is IOException -> exploreAll.postValue(Resource.Error("Network Failure"))
                    else -> exploreAll.postValue(Resource.Error("Json Conversion Error"))
                }
        }
    }

    private fun checkInternetConnection(): Boolean{
        val connectivityManager = getApplication<ExploreApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activityNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activityNetwork) ?: return false
            return when{
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
        else{
            connectivityManager.activeNetworkInfo?.run {
                return when(type){
                    TYPE_MOBILE -> true
                    TYPE_WIFI -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }


}