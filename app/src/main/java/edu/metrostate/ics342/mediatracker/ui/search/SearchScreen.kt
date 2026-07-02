package edu.metrostate.ics342.mediatracker.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import edu.metrostate.ics342.mediatracker.data.FakeMediaRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onSearch: (String) -> Unit,
    onMediaClick: (Int) -> Unit
) {
    var query by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("") }

    val popularItems = FakeMediaRepository.mediaList.filter { media ->
        selectedType.isEmpty() || media.mediaType == selectedType
    }

    Column(modifier = Modifier.fillMaxSize()) {


        OutlinedTextField(
            value         = query,
            onValueChange = { query = it },
            placeholder   = { Text("Search") },
            leadingIcon   = {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            singleLine      = true,
            shape           = RoundedCornerShape(28.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = { if (query.isNotBlank()) onSearch(query) }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )


        MediaTypeFilterChips(
            selectedType = selectedType,
            onTypeSelect = { selectedType = it },
            modifier     = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text  = "Popular This Week",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            popularItems.forEach { media ->
                MediaResultCard(
                    media   = media,
                    onClick = { onMediaClick(media.id) }
                )
            }
        }
    }
}