package com.kusitms.connectdog.core.data.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApplicationCompletedResponseItem(
    val arrivalLoc: String,
    val departureLoc: String,
    val dogStatusId: Int,
    val endDate: String,
    val intermediaryName: String,
    val isKennel: Boolean,
    val mainImage: String,
    val postId: Long,
    val reviewId: Long,
    val startDate: String
)