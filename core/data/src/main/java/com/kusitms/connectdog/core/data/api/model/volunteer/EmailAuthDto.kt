package com.kusitms.connectdog.core.data.api.model.volunteer

data class EmailAuthDto(
    val authCode: String,
    val accessToken: String
)
