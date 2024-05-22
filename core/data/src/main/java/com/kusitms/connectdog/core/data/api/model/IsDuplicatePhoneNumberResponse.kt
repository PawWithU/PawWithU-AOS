package com.kusitms.connectdog.core.data.api.model

data class IsDuplicatePhoneNumberResponse(
    val isDuplicated: Boolean,
    val socialType: String?,
    val email: String?
)
