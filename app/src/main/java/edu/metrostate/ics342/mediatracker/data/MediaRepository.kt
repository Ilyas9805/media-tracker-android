package edu.metrostate.ics342.mediatracker.data

import edu.metrostate.ics342.mediatracker.data.model.Media

interface MediaRepository {
    suspend fun searchMedia(
        query: String,
        type: String?,
        cursor: String?
    ): MediaSearchResult
}

sealed interface MediaSearchResult {
    data class Success(
        val items: List<Media>,
        val nextCursor: String?
    ) : MediaSearchResult
    data object NetworkError : MediaSearchResult
    data object UnknownError : MediaSearchResult
}