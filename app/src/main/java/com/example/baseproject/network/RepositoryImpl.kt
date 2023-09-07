package com.example.baseproject.network

import com.example.baseproject.mapper.toSchoolEntityList
import com.example.baseproject.mapper.toSchoolList
import com.example.baseproject.mapper.toSchoolSat
import com.example.baseproject.mapper.toSchoolSatEntityList
import com.example.baseproject.mapper.toSchoolSatList
import com.example.baseproject.model.local.SchoolsLocalDataSource
import com.example.baseproject.model.remote.SchoolsRemoteDataSource
import com.example.baseproject.view.data.School
import com.example.baseproject.view.data.SchoolSat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Repository implementation class that fetches data from local data source or remote data source
 */
class RepositoryImpl @Inject constructor(
    private val localDataSource: SchoolsLocalDataSource,
    private val remoteDataSource: SchoolsRemoteDataSource
) : Repository {
    override suspend fun getSchoolList(): Flow<Result<List<School>>> = flow<Result<List<School>>> {
        val schoolListFlow = localDataSource.getAllSchools()
            .map { localData -> localData.toSchoolList() }

        schoolListFlow.collect { state ->
            if (state.isEmpty()) {
                try {
                    val schoolsListResponse = remoteDataSource.fetchSchools()
                    if (schoolsListResponse.isSuccess) {
                        val schoolsEntityList =
                            schoolsListResponse.getOrThrow().toSchoolEntityList()
                        localDataSource.insertAllSchools(schoolsEntityList)
                        emit(Result.success(schoolsEntityList.toSchoolList()))
                    }
                } catch (exception: Exception) {
                    emit(Result.failure(exception))
                }
            } else {
                emit(Result.success(state))
            }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getSchoolSatList(): Flow<Result<List<SchoolSat>>> =
        flow<Result<List<SchoolSat>>> {
            val schoolSatListFlow = localDataSource.getAllSchoolSats()
                .map { localData -> localData.toSchoolSatList() }

            schoolSatListFlow.collect { state ->
                if (state.isEmpty()) {
                    try {
                        val schoolsSatListResponse = remoteDataSource.fetchSchoolSatList()
                        if (schoolsSatListResponse.isSuccess) {
                            val schoolsSatEntityList =
                                schoolsSatListResponse.getOrThrow().toSchoolSatEntityList()
                            localDataSource.insertAllSchoolSats(schoolsSatEntityList)
                            emit(Result.success(schoolsSatEntityList.toSchoolSatList()))
                        }
                    } catch (exception: Exception) {
                        emit(Result.failure(exception))
                    }
                } else {
                    emit(Result.success(state))
                }
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun getSchoolSat(id: String): Flow<Result<SchoolSat>> =
        flow<Result<SchoolSat>> {
            val schoolSatFlow = localDataSource.getSchoolSat(id)
                .map { localData -> localData?.toSchoolSat() }

            schoolSatFlow.collect { state ->
                if (state == null)
                    emit(Result.failure(Throwable("No School SAT found")))
                else
                    emit(Result.success(state))
            }
        }.flowOn(Dispatchers.IO)
}