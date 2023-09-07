package com.example.baseproject.model.local.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.baseproject.model.local.SCHOOLS_DB
import com.example.baseproject.model.local.SCHOOLS_TABLE
import com.google.gson.annotations.SerializedName

/**
 * Data class to represent school from the database/local data source layer
 */
@Entity(tableName = SCHOOLS_TABLE)
data class SchoolEntity (
    @PrimaryKey
    val id: String,
    val name: String,
    val location: String?,
    val phoneNumber: String?,
    val email: String?
)