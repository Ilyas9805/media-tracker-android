package edu.metrostate.ics342.mediatracker.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import edu.metrostate.ics342.mediatracker.R
import edu.metrostate.ics342.mediatracker.data.model.Media
import edu.metrostate.ics342.mediatracker.data.model.Review
import edu.metrostate.ics342.mediatracker.data.model.UserProfile
import edu.metrostate.ics342.mediatracker.data.model.creatorCredit

// ── Hardcoded test data ───────────────────────────────────────────────────────
private val fakeBook = Media(
    id            = 1,
    mediaType     = "book",
    title         = "Dune",
    author        = "Frank Herbert",
    publishedYear = 1965,
    averageRating = 4.8f,
    ratingCount   = 1847,
    genres        = listOf("Science Fiction", "Epic"),
    description   = "Set in the distant future amidst a feudal interstellar society, Dune tells the story of young Paul Atreides as his family accepts stewardship of the desert planet Arrakis, the only source of the most precious substance in the universe.",
    pageCount     = 688,
    reviewCount   = 42
)

private val fakeMovie = Media(
    id             = 5,
    mediaType      = "movie",
    title          = "Arrival",
    director       = "Denis Villeneuve",
    publishedYear  = 2016,
    averageRating  = 4.5f,
    ratingCount    = 1534,
    genres         = listOf("Science Fiction", "Drama"),
    description    = "When mysterious spacecraft touch down across the globe, an elite team is put together to investigate, including linguistics professor Louise Banks.",
    runtimeMinutes = 116,
    reviewCount    = 18
)

private val fakeShow = Media(
    id            = 8,
    mediaType     = "show",
    title         = "Severance",
    creator       = "Dan Erickson",
    network       = "Apple TV+",
    publishedYear = 2022,
    averageRating = 4.9f,
    ratingCount   = 1432,
    genres        = listOf("Thriller", "Science Fiction", "Drama"),
    description   = "Mark leads a team of office workers whose memories have been surgically divided between their work and personal lives.",
    seasonCount   = 2,
    episodeCount  = 19,
    reviewCount   = 31
)

private val fakeReviews = listOf(
    Review(
        userId     = "user-002",
        mediaId    = 1,
        rating     = 5,
        reviewText = "Absolutely gripping from start to finish. One of the best sci-fi books I've read in years.",
        createdAt  = "2024-01-22",
        user       = UserProfile(
            id          = "user-002",
            email       = "j@example.com",
            username    = "jsmith",
            displayName = "Jordan Smith"
        )
    ),
    Review(
        userId     = "user-003",
        mediaId    = 1,
        rating     = 4,
        reviewText = "Really enjoyed it. The science is surprisingly accessible and the story kept me hooked.",
        createdAt  = "2024-01-20",
        user       = UserProfile(
            id          = "user-003",
            email       = "p@example.com",
            username    = "priya_r",
            displayName = "Priya Patel"
        )
    )
)

// ── Screen ────────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaDetailScreen(
    mediaId: Int,
    onNavigateBack: () -> Unit,
    onWriteReview: (Int) -> Unit
) {
    val media = when (mediaId) {
        5    -> fakeMovie
        8    -> fakeShow
        else -> fakeBook
    }

    val context = LocalContext.current
    var menuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Box {
                        IconButton(onClick = { menuExpanded = true }) {
                            Icon(Icons.Outlined.MoreVert, contentDescription = "More options")
                        }
                        DropdownMenu(
                            expanded         = menuExpanded,
                            onDismissRequest = { menuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text    = { Text("Share") },
                                onClick = { menuExpanded = false }
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ── Cover image ───────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                contentAlignment = Alignment.Center
            ) {
                if (media.coverUrl != null) {
                    AsyncImage(
                        model              = media.coverUrl,
                        contentDescription = media.title,
                        contentScale       = ContentScale.Crop,
                        modifier           = Modifier.fillMaxSize()
                    )
                } else {
                    val containerColor = when (media.mediaType) {
                        "book"  -> MaterialTheme.colorScheme.primaryContainer
                        "movie" -> MaterialTheme.colorScheme.secondaryContainer
                        else    -> MaterialTheme.colorScheme.tertiaryContainer
                    }
                    val iconTint = when (media.mediaType) {
                        "book"  -> MaterialTheme.colorScheme.onPrimaryContainer
                        "movie" -> MaterialTheme.colorScheme.onSecondaryContainer
                        else    -> MaterialTheme.colorScheme.onTertiaryContainer
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surface),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(containerColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(
                                    when (media.mediaType) {
                                        "book"  -> R.drawable.menu_book_
                                        "movie" -> R.drawable.movie
                                        else    -> R.drawable.tv
                                    }
                                ),
                                contentDescription = null,
                                tint               = iconTint,
                                modifier           = Modifier.size(48.dp)
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // ── Title + creator ───────────────────────────────────────
            Column(
                modifier            = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text       = media.title,
                    style      = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text  = media.creatorCredit(context),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(Modifier.height(12.dp))

            // ── Rating row ────────────────────────────────────────────
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier          = Modifier.padding(horizontal = 24.dp)
            ) {
                val fullStars  = media.averageRating.toInt()
                val hasHalf    = (media.averageRating - fullStars) >= 0.5f
                val emptyStars = 5 - fullStars - if (hasHalf) 1 else 0

                repeat(fullStars) {
                    Icon(
                        imageVector        = Icons.Filled.Star,
                        contentDescription = null,
                        tint               = MaterialTheme.colorScheme.tertiary,
                        modifier           = Modifier.size(18.dp)
                    )
                }
                if (hasHalf) {
                    Icon(
                        imageVector        = Icons.AutoMirrored.Filled.StarHalf,
                        contentDescription = null,
                        tint               = MaterialTheme.colorScheme.tertiary,
                        modifier           = Modifier.size(18.dp)
                    )
                }
                repeat(emptyStars) {
                    Icon(
                        imageVector        = Icons.Outlined.StarOutline,
                        contentDescription = null,
                        tint               = MaterialTheme.colorScheme.tertiary,
                        modifier           = Modifier.size(18.dp)
                    )
                }

                Spacer(Modifier.width(6.dp))
                Text(
                    text       = "${"%.1f".format(media.averageRating)}",
                    style      = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text  = "(${"%,d".format(media.ratingCount)})",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(Modifier.height(20.dp))

            // ── Action buttons ────────────────────────────────────────
            Row(
                modifier              = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick  = { },
                    shape    = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor   = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("+ Want To")
                }
                OutlinedButton(
                    onClick  = { },
                    shape    = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Outlined.Favorite,
                        contentDescription = null,
                        tint     = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text  = "Save",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // ── About ─────────────────────────────────────────────────
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
            ) {
                Text(
                    text       = "About",
                    style      = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text  = media.description ?: "No description available.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(Modifier.height(24.dp))

            // ── Stat grid ─────────────────────────────────────────────
            Row(
                modifier              = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatBox(
                    label    = "Year",
                    value    = media.publishedYear?.toString() ?: "—",
                    modifier = Modifier.weight(1f)
                )

                when (media.mediaType) {
                    "book"  -> StatBox(
                        label    = "Pages",
                        value    = media.pageCount?.toString() ?: "—",
                        modifier = Modifier.weight(1f)
                    )
                    "movie" -> StatBox(
                        label    = "Runtime",
                        value    = media.runtimeMinutes?.let { "${it}m" } ?: "—",
                        modifier = Modifier.weight(1f)
                    )
                    else    -> StatBox(
                        label    = "Seasons",
                        value    = media.seasonCount?.let {
                            "$it / ${media.episodeCount ?: "?"} eps"
                        } ?: "—",
                        modifier = Modifier.weight(1f)
                    )
                }

                StatBox(
                    label    = "Genre",
                    value    = media.genres.firstOrNull() ?: "—",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(24.dp))

            // ── Reviews header ────────────────────────────────────────
            Row(
                modifier              = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Text(
                    text       = "Reviews (${media.reviewCount})",
                    style      = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                TextButton(onClick = { onWriteReview(media.id) }) {
                    Text(
                        text  = "+ Write Review",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            // ── Review cards ──────────────────────────────────────────
            fakeReviews.forEach { review ->
                ReviewCard(
                    review   = review,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
                )
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

// ── Stat box ──────────────────────────────────────────────────────────────────
@Composable
private fun StatBox(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier  = modifier,
        shape     = RoundedCornerShape(12.dp),
        colors    = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier            = Modifier.padding(12.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text  = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text       = value,
                style      = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

// ── Review card ───────────────────────────────────────────────────────────────
@Composable
private fun ReviewCard(
    review: Review,
    modifier: Modifier = Modifier
) {
    val displayName = review.user?.displayName ?: "Unknown"
    val username    = review.user?.username    ?: ""

    Card(
        modifier  = modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(12.dp),
        colors    = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text  = displayName.firstOrNull()?.toString() ?: "?",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.background
                    )
                }
                Spacer(Modifier.width(10.dp))
                Column {
                    Text(
                        text       = displayName,
                        style      = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text  = "@$username · ${review.createdAt}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(Modifier.weight(1f))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(review.rating) {
                        Icon(
                            imageVector        = Icons.Filled.Star,
                            contentDescription = null,
                            tint               = MaterialTheme.colorScheme.tertiary,
                            modifier           = Modifier.size(14.dp)
                        )
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
            Text(
                text  = review.reviewText ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}