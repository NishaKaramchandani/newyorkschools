package com.example.baseproject.model.local.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.baseproject.model.local.SCHOOLS_SAT_TABLE

/**
 * Data class to represent school sat from the database/local data source layer
 */
@Entity(tableName = SCHOOLS_SAT_TABLE)
data class SchoolSatEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val totalSat: String?,
    val readingAvgScore: String?,
    val mathAvgScore: String?,
    val writingAvgScore: String?
)
