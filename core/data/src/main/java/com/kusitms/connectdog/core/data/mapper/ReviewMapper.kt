package com.kusitms.connectdog.core.data.mapper

import com.kusitms.connectdog.core.data.api.model.ReviewResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.ReviewDetailResponse
import com.kusitms.connectdog.core.model.Review
import com.kusitms.connectdog.core.util.dateRangeFormat

internal fun ReviewResponseItem.toData(): Review {
    return Review(
        profileNum = profileImageNum,
        dogName = dogName,
        userName = volunteerNickname,
        mainImage = mainImage,
        contentImages = images,
        date = dateRangeFormat(startDate, endDate),
        location = "$departureLoc → $arrivalLoc",
        organization = intermediaryName,
        content = content
    )
}

fun ReviewDetailResponse.toData(): Review {
    return Review(
        profileNum = profileImageNum,
        dogName = dogName,
        userName = volunteerNickname,
        mainImage = mainImage,
        contentImages = images,
        date = dateRangeFormat(startDate, endDate),
        location = "$departureLoc → $arrivalLoc",
        organization = intermediaryName,
        content = content,
        intermediaryId = intermediaryId,
        postMainImage = postMainImage
    )
}
