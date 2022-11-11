package com.example.explore.api

import retrofit2.Response
import retrofit2.http.GET

interface ExploreApi {
    @GET("v3.1/all")
   suspend fun getExploreResponse(): Response<ExploreResponse>
}