package com.example.baseproject.model.remote

import com.example.baseproject.model.remote.data.SchoolResponse
import com.example.baseproject.model.remote.data.SchoolSatResponse
import okhttp3.ResponseBody
import java.io.IOException
import javax.inject.Inject

/**
 * Implementation class for School remote data source
 */
class SchoolsRemoteDataSourceImpl @Inject constructor(private val apiService: ApiService): SchoolsRemoteDataSource {

    override suspend fun fetchSchools(): Result<List<SchoolResponse>> {
        val schoolsResponse = apiService.getSchoolList()
        return if (schoolsResponse.isSuccessful) {
            (schoolsResponse.body()?.let { Result.success(it) }
                ?: Result.failure(IOException("No data received.")))
        } else {
            val error: ResponseBody? = schoolsResponse.errorBody()
            Result.failure(IOException(error.toString()))
        }
    }

    override suspend fun fetchSchoolSatList(): Result<List<SchoolSatResponse>> {
        val schoolSatResponse = apiService.getSatScores()
        return if (schoolSatResponse.isSuccessful) {
            (schoolSatResponse.body()?.let { Result.success(it) }
                ?: Result.failure(IOException("No data received.")))
        } else {
            val error: ResponseBody? = schoolSatResponse.errorBody()
            Result.failure(IOException(error.toString()))
        }
    }
}