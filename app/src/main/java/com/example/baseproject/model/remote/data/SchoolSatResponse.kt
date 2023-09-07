package com.example.baseproject.model.remote.data

import com.google.gson.annotations.SerializedName

/**
 * Data class for SAT API response
 */
data class SchoolSatResponse(
    @SerializedName("dbn")
    val id: String,
    @SerializedName("school_name")
    val name: String,
    @SerializedName("num_of_sat_test_takers")
    val totalSat: String?,
    @SerializedName("sat_critical_reading_avg_score")
    val readingAvgScore: String?,
    @SerializedName("sat_math_avg_score")
    val mathAvgScore: String?,
    @SerializedName("sat_writing_avg_score")
    val writingAvgScore: String?
)
