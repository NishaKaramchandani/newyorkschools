package com.example.baseproject.model.local

import com.example.baseproject.model.local.data.SchoolEntity
import com.example.baseproject.model.local.data.SchoolSatEntity
import kotlinx.coroutines.flow.Flow

/**
 * Interface for School and SAT data source
 */
interface SchoolsLocalDataSource {
    fun insertSchool(schoolEntity: SchoolEntity)

    fun insertAllSchools(schoolEntities: List<SchoolEntity>)

    fun getAllSchools(): Flow<List<SchoolEntity>>

    fun insertSchoolSat(schoolSatEntity: SchoolSatEntity)

    fun insertAllSchoolSats(schoolSatEntities: List<SchoolSatEntity>)

    fun getAllSchoolSats(): Flow<List<SchoolSatEntity>>

    fun getSchoolSat(id: String): Flow<SchoolSatEntity?>
}