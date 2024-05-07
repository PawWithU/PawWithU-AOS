package com.kusitms.connectdog.core.model

data class AnnouncementSearch(
    val imageUrl: String,
    val location: String,
    val date: String,
    val postId: Int,
    val dogName: String,
    val pickUpTime: String,
    val dogSize: String,
    val isKennel: Boolean
)
