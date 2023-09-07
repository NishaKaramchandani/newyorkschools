package com.example.baseproject.model.local

import com.example.baseproject.model.local.data.SchoolEntity
import com.example.baseproject.model.local.data.SchoolSatEntity
import javax.inject.Inject

/**
 * Implementation class for School and SAT local data source
 */
class SchoolsLocalDataSourceImpl @Inject constructor(
    private val schoolsDao: SchoolsDao, private val schoolsSatDao: SchoolsSatDao
) : SchoolsLocalDataSource {

    override fun insertSchool(schoolEntity: SchoolEntity) {
        schoolsDao.insertCountryEntity(schoolEntity)
    }

    override fun insertAllSchools(schoolEntities: List<SchoolEntity>) {
        schoolsDao.insertAll(schoolEntities)
    }

    override fun getAllSchools() = schoolsDao.getAllSchools()

    override fun insertSchoolSat(schoolSatEntity: SchoolSatEntity) {
        schoolsSatDao.insertCountryEntity(schoolSatEntity)
    }

    override fun insertAllSchoolSats(schoolSatEntities: List<SchoolSatEntity>) {
        schoolsSatDao.insertAll(schoolSatEntities)
    }

    override fun getAllSchoolSats() = schoolsSatDao.getAllSchoolSats()

    override fun getSchoolSat(id: String) = schoolsSatDao.getSchoolSats(id)
}