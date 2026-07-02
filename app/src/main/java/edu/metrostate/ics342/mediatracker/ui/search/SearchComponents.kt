package edu.metrostate.ics342.mediatracker.ui.search

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Tv
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import edu.metrostate.ics342.mediatracker.data.model.Media
import edu.metrostate.ics342.mediatracker.data.model.creatorCredit

@Composable
fun MediaTypeFilterChips(
    selectedType: String,
    onTypeSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val types = listOf(
        ""      to "All",
        "book"  to "Books",
        "movie" to "Movies",
        "show"  to "Shows"
    )

    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        types.forEach { (type, label) ->
            val isSelected = selectedType == type
            FilterChip(
                selected = isSelected,
                onClick  = { onTypeSelect(type) },
                label    = { Text(label) },
                shape    = RoundedCornerShape(8.dp),
                colors = FilterChipDefaults.filterChipColors(
                    containerColor         = MaterialTheme.colorScheme.surface,
                    labelColor             = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedLabelColor     = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled     = true,
                    selected    = isSelected,
                    borderColor = MaterialTheme.colorScheme.outline
                )
            )
        }
    }
}

@Composable
fun MediaResultCard(
    media: Media,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val icon: ImageVector = when (media.mediaType) {
        "book"  -> Icons.Outlined.MenuBook
        "movie" -> Icons.Outlined.Movie
        "show"  -> Icons.Outlined.Tv
        else    -> Icons.Outlined.MenuBook
    }

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

    val context = LocalContext.current
    val creator = media.creatorCredit(context)

    Card(
        modifier  = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape     = RoundedCornerShape(12.dp),
        colors    = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick   = onClick
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp, 78.dp)
                    .clip(RoundedCornerShape(6.dp)),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    color    = containerColor,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            icon,
                            contentDescription = null,
                            tint = iconTint
                        )
                    }
                }
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text       = media.title,
                    style      = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    maxLines   = 2
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text  = creator,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector        = Icons.Outlined.Star,
                        contentDescription = null,
                        tint     = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(Modifier.width(2.dp))
                    Text(
                        text  = "${media.averageRating} · ${media.mediaType.replaceFirstChar { it.uppercase() }} · ${media.publishedYear ?: "—"}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}