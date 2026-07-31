package edu.metrostate.ics342.mediatracker.ui.priorities

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Star
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import edu.metrostate.ics342.mediatracker.R
import edu.metrostate.ics342.mediatracker.data.model.Priority
import edu.metrostate.ics342.mediatracker.data.model.creatorCredit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioritiesScreen(
    onNavigateBack: () -> Unit,
    onMediaClick: (Int) -> Unit,
    viewModel: PrioritiesViewModel = viewModel()
) {
    val priorities by viewModel.priorities.collectAsState()
    val isLoading  by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Priorities") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                isLoading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                priorities.isEmpty() -> {
                    Box(
                        modifier         = Modifier.fillMaxSize().padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text  = "No priorities set — mark a 'Want To' item as a priority to see it here.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                else -> {
                    Text(
                        text     = "${priorities.size}/5 items",
                        style    = MaterialTheme.typography.labelMedium,
                        color    = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )

                    LazyColumn(
                        contentPadding      = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(priorities, key = { it.mediaId }) { priority ->
                            PriorityCard(
                                priority    = priority,
                                onClick     = { onMediaClick(priority.mediaId) },
                                onRemove    = { viewModel.removePriority(priority.mediaId) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PriorityCard(
    priority: Priority,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    val context = LocalContext.current
    val media   = priority.media

    val priorityColor = when (priority.priority) {
        1    -> MaterialTheme.colorScheme.error
        2    -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.primary
    }

    val priorityLabel = when (priority.priority) {
        1    -> "High"
        2    -> "Medium"
        else -> "Low"
    }

    Card(
        modifier  = Modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(12.dp),
        colors    = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick   = onClick
    ) {
        Row(
            modifier          = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ── Order index badge ─────────────────────────────────────
            Box(
                modifier         = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    color    = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text  = "${priority.orderIndex + 1}",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(Modifier.width(12.dp))

            // ── Cover ─────────────────────────────────────────────────
            Box(
                modifier         = Modifier
                    .size(48.dp, 68.dp)
                    .clip(RoundedCornerShape(6.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (media?.coverUrl != null) {
                    AsyncImage(
                        model              = media.coverUrl,
                        contentDescription = media.title,
                        contentScale       = ContentScale.Crop,
                        modifier           = Modifier.fillMaxSize()
                    )
                } else {
                    Surface(
                        color    = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                painter = painterResource(
                                    when (media?.mediaType) {
                                        "book"  -> R.drawable.menu_book_
                                        "movie" -> R.drawable.movie
                                        else    -> R.drawable.tv
                                    }
                                ),
                                contentDescription = null,
                                tint               = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier           = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.width(12.dp))

            // ── Info ──────────────────────────────────────────────────
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text       = media?.title ?: "Unknown",
                    style      = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    maxLines   = 2
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text  = media?.creatorCredit(context) ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    // Priority badge
                    SuggestionChip(
                        onClick = { },
                        label   = {
                            Text(
                                text  = priorityLabel,
                                style = MaterialTheme.typography.labelSmall,
                                color = priorityColor
                            )
                        },
                        border = null,
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = priorityColor.copy(alpha = 0.12f)
                        )
                    )
                    // Estimated time
                    if (priority.estimatedTimeHours > 0f) {
                        Text(
                            text  = "${priority.estimatedTimeHours}h",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                // Notes
                if (!priority.notes.isNullOrBlank()) {
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text     = priority.notes,
                        style    = MaterialTheme.typography.bodySmall,
                        color    = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2
                    )
                }
            }

            // ── Remove button ─────────────────────────────────────────
            TextButton(onClick = onRemove) {
                Text(
                    text  = "Remove",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}