package com.kusitms.connectdog.core.model

data class Application(
    val imageUrl: String,
    val location: String,
    val date: String,
    val organization: String,
    val hasKennel: Boolean,
    val postId: Long,
    val applicationId: Long ? = null,
    val reviewId: Long? = null,
    val dogSize: String? = null,
    val dogName: String? = null,
    val pickUpTime: String? = null
)
