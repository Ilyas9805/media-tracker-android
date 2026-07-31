package edu.metrostate.ics342.mediatracker.ui.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import edu.metrostate.ics342.mediatracker.R
import edu.metrostate.ics342.mediatracker.data.model.LibraryEntry
import edu.metrostate.ics342.mediatracker.data.model.LibraryStatus
import edu.metrostate.ics342.mediatracker.data.model.creatorCredit
import edu.metrostate.ics342.mediatracker.theme.FinishedContainer
import edu.metrostate.ics342.mediatracker.theme.InProgressContainer
import edu.metrostate.ics342.mediatracker.theme.OnFinishedContainer
import edu.metrostate.ics342.mediatracker.theme.OnInProgressContainer
import edu.metrostate.ics342.mediatracker.theme.OnWantToContainer
import edu.metrostate.ics342.mediatracker.theme.WantToContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    onMediaClick      : (Int) -> Unit,
    onPrioritiesClick : () -> Unit,
    viewModel         : LibraryViewModel = viewModel()
) {
    val items          by viewModel.libraryItems.collectAsState()
    val isLoading      by viewModel.isLoading.collectAsState()
    val selectedStatus by viewModel.filterState.collectAsState()

    var selectedType by remember { mutableStateOf("all") }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text(stringResource(R.string.library_title)) })

        // ── Priorities entry point ────────────────────────────────────
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .clickable { onPrioritiesClick() },
            shape     = RoundedCornerShape(12.dp),
            colors    = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Row(
                modifier          = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector        = Icons.Outlined.Star,
                    contentDescription = null,
                    tint               = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier           = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text       = "My Priorities",
                    style      = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color      = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text  = "View →",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        // ── Type filter chips ─────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                "all"    to R.string.filter_all,
                "book"   to R.string.filter_books,
                "movie"  to R.string.filter_movies,
                "show"   to R.string.filter_shows,
                "comics" to R.string.filter_comics,
                "albums" to R.string.filter_albums,
            ).forEach { (key, labelRes) ->
                val isSelected = selectedType == key
                FilterChip(
                    selected = isSelected,
                    onClick  = { selectedType = key },
                    label    = { Text(stringResource(labelRes)) },
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

        // ── Status segmented button ───────────────────────────────────
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            LibraryStatus.values().forEachIndexed { index, status ->
                SegmentedButton(
                    shape    = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = LibraryStatus.values().size
                    ),
                    selected = selectedStatus == status,
                    onClick  = { viewModel.updateFilter(status) },
                    label    = { Text(stringResource(status.labelRes)) }
                )
            }
        }

        HorizontalDivider(modifier = Modifier.padding(top = 8.dp))

        if (isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Column
        }

        val filteredItems = items.filter { entry ->
            selectedType == "all" || entry.media?.mediaType == selectedType
        }

        if (filteredItems.isEmpty()) {
            Box(
                modifier         = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    stringResource(R.string.library_empty),
                    style     = MaterialTheme.typography.bodyLarge,
                    color     = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
            return@Column
        }

        Text(
            text     = if (filteredItems.size == 1)
                stringResource(R.string.library_item_count, filteredItems.size)
            else
                stringResource(R.string.library_items_count, filteredItems.size),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style    = MaterialTheme.typography.labelMedium,
            color    = MaterialTheme.colorScheme.onSurfaceVariant
        )

        LazyColumn(
            contentPadding      = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredItems, key = { it.mediaId }) { entry ->
                LibraryEntryCard(
                    entry          = entry,
                    onClick        = { onMediaClick(entry.mediaId) },
                    onRemove       = { viewModel.removeItem(entry.mediaId) },
                    onStatusChange = { newStatus -> viewModel.changeStatus(entry.mediaId, newStatus) }
                )
            }
        }
    }
}

@Composable
private fun LibraryEntryCard(
    entry: LibraryEntry,
    onClick: () -> Unit,
    onRemove: () -> Unit,
    onStatusChange: (String) -> Unit
) {
    val context          = LocalContext.current
    val media            = entry.media
    var menuExpanded     by remember { mutableStateOf(false) }
    var showStatusDialog by remember { mutableStateOf(false) }

    val (badgeContainer, badgeOnContainer) = when (entry.status) {
        "want_to"     -> WantToContainer     to OnWantToContainer
        "in_progress" -> InProgressContainer to OnInProgressContainer
        else          -> FinishedContainer   to OnFinishedContainer
    }

    // ── Status change dialog ──────────────────────────────────────────
    if (showStatusDialog) {
        AlertDialog(
            onDismissRequest = { showStatusDialog = false },
            title = { Text(stringResource(R.string.action_change_status)) },
            text = {
                Column {
                    listOf(
                        "want_to"     to stringResource(R.string.status_want_to),
                        "in_progress" to stringResource(R.string.status_in_progress),
                        "finished"    to stringResource(R.string.status_finished)
                    ).forEach { (key, label) ->
                        TextButton(
                            onClick  = {
                                onStatusChange(key)
                                showStatusDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) { Text(label) }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showStatusDialog = false }) {
                    Text(stringResource(R.string.settings_cancel_button))
                }
            }
        )
    }

    Card(
        modifier  = Modifier.fillMaxWidth().clickable { onClick() },
        shape     = RoundedCornerShape(12.dp),
        colors    = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

            // ── Cover ─────────────────────────────────────────────────
            Box(
                modifier         = Modifier
                    .size(64.dp, 90.dp)
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
                        Box(
                            modifier         = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
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
                                modifier           = Modifier.size(32.dp)
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
                Spacer(Modifier.height(6.dp))
                SuggestionChip(
                    onClick = { },
                    label   = {
                        Text(
                            text  = when (entry.status) {
                                "want_to"     -> stringResource(R.string.status_want_to)
                                "in_progress" -> stringResource(R.string.status_in_progress)
                                else          -> stringResource(R.string.status_finished)
                            },
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = badgeContainer,
                        labelColor     = badgeOnContainer
                    ),
                    border = null
                )
            }

            // ── Three dot menu ────────────────────────────────────────
            Box {
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(
                        Icons.Outlined.MoreVert,
                        contentDescription = stringResource(R.string.action_more_options)
                    )
                }
                DropdownMenu(
                    expanded         = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text    = { Text(stringResource(R.string.action_change_status)) },
                        onClick = {
                            menuExpanded     = false
                            showStatusDialog = true
                        }
                    )
                    DropdownMenuItem(
                        text    = {
                            Text(
                                stringResource(R.string.action_remove_from_library),
                                color = MaterialTheme.colorScheme.error
                            )
                        },
                        onClick = {
                            menuExpanded = false
                            onRemove()
                        }
                    )
                }
            }
        }
    }
}