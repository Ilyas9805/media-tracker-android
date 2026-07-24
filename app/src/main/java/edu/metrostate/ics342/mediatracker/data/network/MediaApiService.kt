package edu.metrostate.ics342.mediatracker.data.network

import edu.metrostate.ics342.mediatracker.data.model.FavoriteEntry
import edu.metrostate.ics342.mediatracker.data.model.LibraryEntry
import edu.metrostate.ics342.mediatracker.data.model.Media
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.DELETE

interface MediaApiService {

    // ── Media ─────────────────────────────────────────────────────────────
    @GET("media")
    suspend fun searchMedia(
        @Query("query") query: String? = null,
        @Query("type") type: String? = null,
        @Query("limit") limit: Int = 20,
        @Query("after") after: String? = null
    ): Response<List<Media>>

    @GET("media/{id}")
    suspend fun getMediaById(@Path("id") id: Int): Response<Media>

    // ── Library ───────────────────────────────────────────────────────────
    @GET("library")
    suspend fun getLibrary(
        @Query("status") status: String? = null
    ): Response<List<LibraryEntry>>

    @GET("library/{mediaId}")
    suspend fun getLibraryEntry(@Path("mediaId") mediaId: Int): Response<LibraryEntry>

    @POST("library")
    suspend fun addToLibrary(@Body body: AddLibraryRequest): Response<LibraryEntry>

    @PATCH("library/{mediaId}")
    suspend fun updateLibraryEntry(
        @Path("mediaId") mediaId: Int,
        @Body body: UpdateLibraryRequest
    ): Response<LibraryEntry>

    // ── Favorites ─────────────────────────────────────────────────────────
    @GET("favorites")
    suspend fun getFavorites(): Response<List<FavoriteEntry>>

    @GET("favorites/{mediaId}")
    suspend fun getFavoriteEntry(@Path("mediaId") mediaId: Int): Response<FavoriteEntry>

    @POST("favorites")
    suspend fun addToFavorites(@Body body: AddFavoriteRequest): Response<FavoriteEntry>

    @DELETE("library/{mediaId}")
    suspend fun removeFromLibrary(@Path("mediaId") mediaId: Int): Response<Unit>
}