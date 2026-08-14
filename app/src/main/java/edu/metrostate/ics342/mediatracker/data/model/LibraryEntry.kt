package edu.metrostate.ics342.mediatracker.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LibraryEntry(
    val userId   : String,
    val mediaId  : Int,
    val status   : String,
    val addedAt  : String,
    val updatedAt: String,
    val media    : Media? = null
)