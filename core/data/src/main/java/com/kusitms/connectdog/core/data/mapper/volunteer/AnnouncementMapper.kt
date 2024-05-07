package com.kusitms.connectdog.core.data.mapper.volunteer

import com.kusitms.connectdog.core.data.api.model.volunteer.AnnouncementHomeResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.AnnouncementSearchResponseItem
import com.kusitms.connectdog.core.model.Announcement
import com.kusitms.connectdog.core.model.AnnouncementSearch
import com.kusitms.connectdog.core.util.dateRangeFormat

internal fun AnnouncementHomeResponseItem.toData(): Announcement {
    return Announcement(
        imageUrl = this.mainImage,
        location = "${this.departureLoc} → ${this.arrivalLoc}",
        date = dateRangeFormat(startDate, endDate),
        postId = this.postId,
        dogName = this.dogName,
        pickUpTime = this.pickUpTime
    )
}

internal fun AnnouncementSearchResponseItem.toData(): AnnouncementSearch {
    return AnnouncementSearch(
        imageUrl = this.mainImage,
        location = "${this.departureLoc} → ${this.arrivalLoc}",
        date = dateRangeFormat(startDate, endDate),
        dogName = this.dogName,
        pickUpTime = this.pickUpTime,
        postId = this.postId,
        isKennel = this.isKennel,
        dogSize = this.dogSize
    )
}
