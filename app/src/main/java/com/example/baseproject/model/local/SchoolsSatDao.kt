package com.example.baseproject.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.baseproject.model.local.data.SchoolSatEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO class for SAT score data
 */
@Dao
interface SchoolsSatDao {

    @Query("SELECT * FROM $SCHOOLS_SAT_TABLE")
    fun getAllSchoolSats(): Flow<List<SchoolSatEntity>>

    @Query("SELECT * FROM $SCHOOLS_SAT_TABLE WHERE id = :id")
    fun getSchoolSats(id: String): Flow<SchoolSatEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(schoolSatEntities: List<SchoolSatEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountryEntity(schoolSatEntity: SchoolSatEntity)
}