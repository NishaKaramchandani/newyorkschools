package com.example.baseproject.model.remote

import com.example.baseproject.model.remote.data.SchoolResponse
import com.example.baseproject.model.remote.data.SchoolSatResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * API service interface for endpoints
 */
interface ApiService {
    companion object {
        const val BASE_URL = "https://data.cityofnewyork.us/"
    }

    @GET("resource/s3k6-pzi2.json")
    suspend fun getSchoolList(): Response<List<SchoolResponse>>

    @GET("/resource/f9bf-2cp4.json")
    suspend fun getSatScores(): Response<List<SchoolSatResponse>>
}