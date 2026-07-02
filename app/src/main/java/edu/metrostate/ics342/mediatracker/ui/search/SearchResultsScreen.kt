package edu.metrostate.ics342.mediatracker.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.metrostate.ics342.mediatracker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultsScreen(
    initialQuery: String,
    onMediaClick: (Int) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: SearchResultsViewModel = viewModel()
) {
    var searchBarQuery by remember { mutableStateOf(initialQuery) }
    val results         by viewModel.results.collectAsState()
    val selectedType    by viewModel.selectedType.collectAsState()
    val isLoading       by viewModel.isLoading.collectAsState()

    val listState = rememberLazyListState()

    // Trigger next page load when within 5 items of the end
    val reachedBottom by remember {
        derivedStateOf {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
            val total = listState.layoutInfo.totalItemsCount
            total > 0 && lastVisible >= total - 5
        }
    }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom) viewModel.loadNextPage()
    }

    LaunchedEffect(initialQuery) {
        viewModel.search(initialQuery)
    }

    Column(modifier = Modifier.fillMaxSize()) {

        OutlinedTextField(
            value         = searchBarQuery,
            onValueChange = { searchBarQuery = it },
            placeholder   = { Text("Search") },
            leadingIcon   = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            trailingIcon  = {
                IconButton(onClick = { viewModel.search(searchBarQuery) }) {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }
            },
            singleLine      = true,
            shape           = RoundedCornerShape(28.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = { viewModel.search(searchBarQuery) }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // ── Filter chips ─────────────────────────────────────────────────
        MediaTypeFilterChips(
            selectedType = selectedType,
            onTypeSelect = viewModel::onTypeSelect,
            modifier     = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(8.dp))

        if (!isLoading) {
            Text(
                text     = stringResource(R.string.search_results_count, results.size),
                style    = MaterialTheme.typography.labelMedium,
                color    = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(8.dp))
        }


        LazyColumn(
            state    = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            items(results, key = { it.id }) { media ->
                MediaResultCard(
                    media   = media,
                    onClick = { onMediaClick(media.id) }
                )
            }

            if (isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}