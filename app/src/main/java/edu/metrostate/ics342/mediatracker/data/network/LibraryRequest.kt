package edu.metrostate.ics342.mediatracker.data.network

import kotlinx.serialization.Serializable

@Serializable
data class AddLibraryRequest(
    val mediaId: Int,
    val status : String
)

@Serializable
data class UpdateLibraryRequest(
    val status: String
)

@Serializable
data class AddFavoriteRequest(
    val mediaId: Int
)