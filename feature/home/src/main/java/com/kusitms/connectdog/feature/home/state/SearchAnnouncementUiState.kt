package com.kusitms.connectdog.feature.home.state

import com.kusitms.connectdog.core.model.AnnouncementSearch

sealed interface SearchAnnouncementUiState {
    object Loading : SearchAnnouncementUiState
    object Empty : SearchAnnouncementUiState
    data class SearchAnnouncements(
        val announcements: List<AnnouncementSearch>
    ) : SearchAnnouncementUiState
}
