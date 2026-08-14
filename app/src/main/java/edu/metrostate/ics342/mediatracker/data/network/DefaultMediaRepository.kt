package edu.metrostate.ics342.mediatracker.data.network

import edu.metrostate.ics342.mediatracker.data.model.Priority
import edu.metrostate.ics342.mediatracker.data.MediaDetailResult
import edu.metrostate.ics342.mediatracker.data.MediaRepository
import edu.metrostate.ics342.mediatracker.data.MediaSearchResult
import edu.metrostate.ics342.mediatracker.data.FakeMediaRepository
import edu.metrostate.ics342.mediatracker.data.SessionRepository
import edu.metrostate.ics342.mediatracker.data.model.FavoriteEntry
import edu.metrostate.ics342.mediatracker.data.model.LibraryEntry
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

    // ── Search ────────────────────────────────────────────────────────────

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

    // ── Media Detail ──────────────────────────────────────────────────────

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

    // ── Library ───────────────────────────────────────────────────────────

    suspend fun getLibrary(status: String? = null): List<LibraryEntry> {
        return try {
            val response = api.getLibrary(status = status)
            if (response.isSuccessful) response.body() ?: emptyList()
            else emptyList()
        } catch (e: IOException) {
            emptyList()
        }
    }

    suspend fun getLibraryEntry(mediaId: Int): LibraryEntry? {
        return try {
            val response = api.getLibraryEntry(mediaId)
            if (response.isSuccessful) response.body()
            else null
        } catch (e: IOException) {
            null
        }
    }

    suspend fun addToLibrary(mediaId: Int, status: String): LibraryEntry? {
        return try {
            val response = api.addToLibrary(AddLibraryRequest(mediaId, status))
            if (response.isSuccessful) response.body()
            else null
        } catch (e: IOException) {
            null
        }
    }

    suspend fun updateLibraryEntry(mediaId: Int, status: String): LibraryEntry? {
        return try {
            val response = api.updateLibraryEntry(mediaId, UpdateLibraryRequest(status))
            if (response.isSuccessful) response.body()
            else null
        } catch (e: IOException) {
            null
        }
    }

    // ── Favorites ─────────────────────────────────────────────────────────

    suspend fun getFavoriteEntry(mediaId: Int): FavoriteEntry? {
        return try {
            val response = api.getFavoriteEntry(mediaId)
            if (response.isSuccessful) response.body()
            else null
        } catch (e: IOException) {
            null
        }
    }

    suspend fun addToFavorites(mediaId: Int): FavoriteEntry? {
        return try {
            val response = api.addToFavorites(AddFavoriteRequest(mediaId))
            if (response.isSuccessful) response.body()
            else null
        } catch (e: IOException) {
            null
        }
    }

    suspend fun removeFromLibrary(mediaId: Int): Boolean {
        return try {
            val response = api.removeFromLibrary(mediaId)
            response.isSuccessful
        } catch (e: IOException) {
            false
        }
    }

    suspend fun getPriorities(): List<Priority> {
        return try {
            val response = api.getPriorities()
            if (response.isSuccessful) response.body() ?: emptyList()
            else emptyList()
        } catch (e: IOException) {
            emptyList()
        }
    }

    suspend fun setPriorities(priorities: List<PriorityRequest>): List<Priority> {
        return try {
            val response = api.setPriorities(SetPrioritiesRequest(priorities))
            if (response.isSuccessful) response.body() ?: emptyList()
            else emptyList()
        } catch (e: IOException) {
            emptyList()
        }
    }
}