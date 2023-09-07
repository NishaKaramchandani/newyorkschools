package com.example.baseproject.model.remote

import com.example.baseproject.model.remote.data.SchoolResponse
import com.example.baseproject.model.remote.data.SchoolSatResponse

/**
 * Interface for School remote data source
 */
interface SchoolsRemoteDataSource {
    suspend fun fetchSchools(): Result<List<SchoolResponse>>
    suspend fun fetchSchoolSatList(): Result<List<SchoolSatResponse>>
}