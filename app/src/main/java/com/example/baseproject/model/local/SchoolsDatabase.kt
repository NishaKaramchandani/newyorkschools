package com.example.baseproject.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.baseproject.model.local.data.SchoolEntity
import com.example.baseproject.model.local.data.SchoolSatEntity

const val SCHOOLS_DB = "schools_db"
const val SCHOOLS_SAT_TABLE = "schools_sat"
const val SCHOOLS_TABLE = "schools"

@Database(
    entities = [SchoolEntity::class, SchoolSatEntity::class],
    version = 3
)

/**
 * Database class for school and SAT scores
 */
abstract class SchoolsDatabase : RoomDatabase() {
    abstract fun schoolsDao(): SchoolsDao
    abstract fun schoolsSatDao(): SchoolsSatDao
}
