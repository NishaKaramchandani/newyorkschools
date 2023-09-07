package com.example.baseproject.network

import com.example.baseproject.view.data.School
import com.example.baseproject.view.data.SchoolSat
import kotlinx.coroutines.flow.Flow

/**
 * Interface for repository
 */
interface Repository {
    suspend fun getSchoolList(): Flow<Result<List<School>>>

    suspend fun getSchoolSatList(): Flow<Result<List<SchoolSat>>>

    suspend fun getSchoolSat(id: String): Flow<Result<SchoolSat>>
}