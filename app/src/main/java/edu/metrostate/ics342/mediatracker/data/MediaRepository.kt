package edu.metrostate.ics342.mediatracker.data

import edu.metrostate.ics342.mediatracker.data.model.Media
//API

interface MediaRepository {
    suspend fun searchMedia(
        query: String,
        type: String?,
        cursor: String?
    ): MediaSearchResult

    suspend fun getMediaDetail(id: Int): MediaDetailResult
}

sealed interface MediaSearchResult {
    data class Success(
        val items: List<Media>,
        val nextCursor: String?
    ) : MediaSearchResult
    data object NetworkError : MediaSearchResult
    data object UnknownError : MediaSearchResult
}

sealed interface MediaDetailResult {
    data class Success(val media: Media) : MediaDetailResult
    data object NotFound : MediaDetailResult
    data object NetworkError : MediaDetailResult
    data object UnknownError : MediaDetailResult
}
