package com.kusitms.connectdog.core.model

data class Review(
    val profileNum: Int,
    val dogName: String,
    val userName: String,
    val mainImage: String,
    val date: String,
    val location: String,
    val organization: String,
    val content: String,
    val contentImages: List<String>?,
    val intermediaryId: Long? = null,
    val postMainImage: String = "",
    val reviewId: Long? = null
)
