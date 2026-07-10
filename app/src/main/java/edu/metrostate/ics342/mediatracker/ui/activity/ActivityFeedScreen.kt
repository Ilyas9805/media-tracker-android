package edu.metrostate.ics342.mediatracker.ui.activity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.metrostate.ics342.mediatracker.data.model.ActivityEvent
import edu.metrostate.ics342.mediatracker.data.model.descriptionText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityFeedScreen(
    onMediaClick: (Int) -> Unit,
    onUserClick: (String) -> Unit,
    viewModel: ActivityFeedViewModel = viewModel()
) {
    val feedItems by viewModel.feedItems.collectAsState()
    val context   = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(stringResource(edu.metrostate.ics342.mediatracker.R.string.feed_title)) }
        )

        LazyColumn(
            contentPadding      = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(feedItems, key = { it.id }) { event ->
                ActivityEventCard(
                    event        = event,
                    onMediaClick = {
                        android.util.Log.d("ActivityFeed", "Tapped mediaId: ${event.mediaId}")
                        onMediaClick(event.mediaId)
                    },
                    onUserClick  = { onUserClick(event.userId) },
                    context      = context
                )
            }
        }
    }
}

@Composable
private fun ActivityEventCard(
    event: ActivityEvent,
    onMediaClick: () -> Unit,
    onUserClick: () -> Unit,
    context: android.content.Context
) {
    Card(
        modifier  = Modifier.fillMaxWidth().clickable { onMediaClick() },
        shape     = RoundedCornerShape(12.dp),
        colors    = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // ── User row ──────────────────────────────────────────────
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier          = Modifier.clickable { onUserClick() }
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(color = MaterialTheme.colorScheme.primaryContainer) {
                        Box(
                            modifier         = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text  = event.user?.displayName?.firstOrNull()?.toString() ?: "?",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }

                Spacer(Modifier.width(10.dp))

                Column {
                    Text(
                        text       = event.user?.displayName ?: "Someone",
                        style      = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text  = event.createdAt,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            // ── Description ───────────────────────────────────────────
            Text(
                text  = event.descriptionText(context),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // ── Review text ───────────────────────────────────────────
            if (event.activityType == "review" && event.reviewText != null) {
                Spacer(Modifier.height(8.dp))
                if (event.rating != null) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        repeat(event.rating) {
                            Icon(
                                imageVector        = Icons.Outlined.Star,
                                contentDescription = null,
                                tint               = MaterialTheme.colorScheme.tertiary,
                                modifier           = Modifier.size(14.dp)
                            )
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                }
                Text(
                    text  = event.reviewText,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}