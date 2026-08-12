package edu.metrostate.ics342.mediatracker.ui.review

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import edu.metrostate.ics342.mediatracker.R
import edu.metrostate.ics342.mediatracker.data.model.creatorCredit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteReviewScreen(
    mediaId: Int,
    onNavigateBack: () -> Unit,
    viewModel: WriteReviewViewModel = viewModel()
) {
    val media       by viewModel.media.collectAsState()
    val rating      by viewModel.rating.collectAsState()
    val reviewText  by viewModel.reviewText.collectAsState()
    val shareToFeed by viewModel.shareToFeed.collectAsState()
    val context     = LocalContext.current

    LaunchedEffect(mediaId) {
        viewModel.loadMedia(mediaId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Write Review") },
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
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            // ── Cover image ───────────────────────────────────────────
            media?.let { m ->
                Box(
                    modifier         = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (m.coverUrl != null) {
                        Box(
                            modifier         = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surface),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model              = m.coverUrl,
                                contentDescription = m.title,
                                contentScale       = ContentScale.Fit,
                                modifier           = Modifier
                                    .height(180.dp)
                                    .width(120.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                        }
                    } else {
                        val containerColor = when (m.mediaType) {
                            "book"  -> MaterialTheme.colorScheme.primaryContainer
                            "movie" -> MaterialTheme.colorScheme.secondaryContainer
                            else    -> MaterialTheme.colorScheme.tertiaryContainer
                        }
                        val iconTint = when (m.mediaType) {
                            "book"  -> MaterialTheme.colorScheme.onPrimaryContainer
                            "movie" -> MaterialTheme.colorScheme.onSecondaryContainer
                            else    -> MaterialTheme.colorScheme.onTertiaryContainer
                        }
                        Box(
                            modifier         = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surface),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier         = Modifier
                                    .size(72.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(containerColor),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(
                                        when (m.mediaType) {
                                            "book"  -> R.drawable.menu_book_
                                            "movie" -> R.drawable.movie
                                            else    -> R.drawable.tv
                                        }
                                    ),
                                    contentDescription = null,
                                    tint               = iconTint,
                                    modifier           = Modifier.size(36.dp)
                                )
                            }
                        }
                    }
                }

                // ── Title + creator ───────────────────────────────────
                Column(
                    modifier            = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text       = m.title,
                        style      = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text  = m.creatorCredit(context),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp))
            }

            // ── Star rating ───────────────────────────────────────────
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(
                    text       = "Your Rating",
                    style      = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(8.dp))
                StarRatingRow(
                    rating         = rating,
                    onRatingChange = viewModel::onRatingChange
                )
            }

            // ── Review text ───────────────────────────────────────────
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(
                    text       = "Your Review",
                    style      = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value         = reviewText,
                    onValueChange = viewModel::onReviewTextChange,
                    placeholder   = { Text("What did you think?") },
                    modifier      = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    shape         = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary
                    ),
                    maxLines = 8
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text  = "${reviewText.length}/500",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (reviewText.length >= 500)
                        MaterialTheme.colorScheme.error
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            // ── Share to feed ─────────────────────────────────────────
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier          = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Checkbox(
                    checked         = shareToFeed,
                    onCheckedChange = viewModel::onShareToFeedChange
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text  = "Share to activity feed",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // ── Post Review button ────────────────────────────────────
            Button(
                onClick  = {
                    // TODO: Wire to POST /reviews
                    onNavigateBack()
                },
                enabled  = rating > 0,
                shape    = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor   = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(50.dp)
            ) {
                Text("Post Review")
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

// ── Star rating row ───────────────────────────────────────────────────────────
@Composable
fun StarRatingRow(
    rating: Int,
    onRatingChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier              = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        (1..5).forEach { star ->
            IconButton(onClick = { onRatingChange(star) }) {
                Icon(
                    imageVector = if (star <= rating)
                        Icons.Filled.Star
                    else
                        Icons.Outlined.StarOutline,
                    contentDescription = "Star $star",
                    tint = if (star <= rating)
                        MaterialTheme.colorScheme.tertiary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}