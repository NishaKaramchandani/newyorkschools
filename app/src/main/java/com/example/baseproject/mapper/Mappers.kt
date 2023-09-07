package com.example.baseproject.mapper

import com.example.baseproject.model.local.data.SchoolEntity
import com.example.baseproject.model.local.data.SchoolSatEntity
import com.example.baseproject.model.remote.data.SchoolResponse
import com.example.baseproject.model.remote.data.SchoolSatResponse
import com.example.baseproject.view.data.School
import com.example.baseproject.view.data.SchoolSat

/**
 * Contains different mappers to convert different layer school data objects into each other.
 */
fun SchoolEntity.toSchool() = School(
    id, name, location, phoneNumber, email
)

fun SchoolSatEntity.toSchoolSat() = SchoolSat(
    id, name, totalSat, readingAvgScore, mathAvgScore, writingAvgScore
)

fun SchoolResponse.toSchoolEntity() = SchoolEntity(
    id, name, location, phoneNumber, email
)

fun SchoolSatResponse.toSchoolSatEntity() = SchoolSatEntity(
    id, name, totalSat, readingAvgScore, mathAvgScore, writingAvgScore
)

fun SchoolResponse.toSchool() = School(
    id, name, location, phoneNumber, email
)

fun List<SchoolResponse>.toSchoolEntityList(): List<SchoolEntity> {
    return this.map { it.toSchoolEntity() }
}

fun List<SchoolSatResponse>.toSchoolSatEntityList(): List<SchoolSatEntity> {
    return this.map { it.toSchoolSatEntity() }
}

fun List<SchoolEntity>.toSchoolList(): List<School> {
    return this.map { it.toSchool() }
}

fun List<SchoolSatEntity>.toSchoolSatList(): List<SchoolSat> {
    return this.map { it.toSchoolSat() }
}