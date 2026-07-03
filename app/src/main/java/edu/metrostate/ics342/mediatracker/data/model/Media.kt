package edu.metrostate.ics342.mediatracker.data.model

import android.content.Context
import edu.metrostate.ics342.mediatracker.R
import kotlinx.serialization.Serializable

@Serializable
data class Media(
    val id: Int,
    val mediaType: String,
    val title: String,
    val author: String? = null,
    val director: String? = null,
    val creator: String? = null,
    val network: String? = null,
    val coverUrl: String? = null,
    val publishedYear: Int? = null,
    val averageRating: Float = 0f,
    val ratingCount: Int = 0,
    val genres: List<String> = emptyList(),
    // ── Detail-only fields ────────────────────────────────────────────
    val description: String? = null,
    val pageCount: Int? = null,
    val runtimeMinutes: Int? = null,
    val seasonCount: Int? = null,
    val episodeCount: Int? = null,
    val isbn: String? = null,
    val reviewCount: Int = 0
)

fun Media.creatorCredit(context: Context): String = when (mediaType) {
    "book"  -> author   ?: context.getString(R.string.media_unknown_author)
    "movie" -> director ?: context.getString(R.string.media_unknown_director)
    "show"  -> creator  ?: context.getString(R.string.media_unknown_creator)
    else    -> ""
}