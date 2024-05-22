package com.kusitms.connectdog.core.model

data class AnnouncementHome(
    val imageUrl: String,
    val location: String,
    val date: String,
    val postId: Int,
    val dogName: String,
    val pickUpTime: String
)
