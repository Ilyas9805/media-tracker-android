package edu.metrostate.ics342.mediatracker.data.network

import edu.metrostate.ics342.mediatracker.data.FakeMediaRepository
import edu.metrostate.ics342.mediatracker.data.MediaDetailResult
import edu.metrostate.ics342.mediatracker.data.MediaRepository
import edu.metrostate.ics342.mediatracker.data.MediaSearchResult
import edu.metrostate.ics342.mediatracker.data.SessionRepository
import edu.metrostate.ics342.mediatracker.data.model.Media
import java.io.IOException

data class MediaPage(
    val items: List<Media>,
    val nextCursor: String?,
    val hasMore: Boolean
)

class DefaultMediaRepository(
    private val sessionRepository: SessionRepository
) : MediaRepository {

    private val api = RetrofitInstance.mediaApiService(sessionRepository)

    suspend fun search(query: String, type: String?, after: String?): MediaPage {
        val response = api.searchMedia(
            query = query.ifBlank { null },
            type  = type?.ifBlank { null },
            after = after
        )
        val items      = response.body() ?: emptyList()
        val nextCursor = response.headers()["X-Next-Cursor"]
        val hasMore    = response.headers()["X-Has-More"] == "true"
        return MediaPage(items, nextCursor, hasMore)
    }

    override suspend fun searchMedia(
        query: String,
        type: String?,
        cursor: String?
    ): MediaSearchResult {
        return try {
            val response = api.searchMedia(
                query = query.ifBlank { null },
                type  = type?.ifBlank { null },
                after = cursor
            )
            if (response.isSuccessful) {
                val items      = response.body() ?: emptyList()
                val nextCursor = response.headers()["X-Next-Cursor"]
                MediaSearchResult.Success(items = items, nextCursor = nextCursor)
            } else {
                MediaSearchResult.UnknownError
            }
        } catch (e: IOException) {
            MediaSearchResult.NetworkError
        }
    }

    override suspend fun getMediaDetail(id: Int): MediaDetailResult {
        return try {
            val response = api.getMediaById(id)
            when (response.code()) {
                200  -> {
                    val body = response.body()
                    if (body != null) MediaDetailResult.Success(body)
                    else MediaDetailResult.UnknownError
                }
                404  -> {
                    val fakeMedia = FakeMediaRepository.getMediaById(id)
                    if (fakeMedia != null) MediaDetailResult.Success(fakeMedia)
                    else MediaDetailResult.NotFound
                }
                else -> MediaDetailResult.UnknownError
            }
        } catch (e: IOException) {
            MediaDetailResult.NetworkError
        }
    }
}