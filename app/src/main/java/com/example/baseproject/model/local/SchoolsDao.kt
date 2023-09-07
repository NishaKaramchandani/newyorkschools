package com.example.baseproject.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.baseproject.model.local.data.SchoolEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO class for School
 */
@Dao
interface SchoolsDao {

    @Query("SELECT * FROM $SCHOOLS_TABLE")
    fun getAllSchools(): Flow<List<SchoolEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(schoolEntities: List<SchoolEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountryEntity(schoolEntity: SchoolEntity)
}