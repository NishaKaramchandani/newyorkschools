package com.example.baseproject.view.data

/**
 * Data class to represent school sat data view layer
 */
data class SchoolSat(
    val id: String,
    val name: String,
    val totalSat: String?,
    val readingAvgScore: String?,
    val mathAvgScore: String?,
    val writingAvgScore: String?
)
