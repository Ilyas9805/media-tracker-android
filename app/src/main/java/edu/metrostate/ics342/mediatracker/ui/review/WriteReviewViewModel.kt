package edu.metrostate.ics342.mediatracker.ui.review

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.metrostate.ics342.mediatracker.data.MediaDetailResult
import edu.metrostate.ics342.mediatracker.data.datastore.DefaultSessionRepository
import edu.metrostate.ics342.mediatracker.data.model.Media
import edu.metrostate.ics342.mediatracker.data.network.DefaultMediaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WriteReviewViewModel(application: Application) : AndroidViewModel(application) {

    private val mediaRepository = DefaultMediaRepository(
        DefaultSessionRepository(application)
    )

    private val _media = MutableStateFlow<Media?>(null)
    val media: StateFlow<Media?> = _media.asStateFlow()

    private val _rating = MutableStateFlow(0)
    val rating: StateFlow<Int> = _rating.asStateFlow()

    private val _reviewText = MutableStateFlow("")
    val reviewText: StateFlow<String> = _reviewText.asStateFlow()

    private val _shareToFeed = MutableStateFlow(true)
    val shareToFeed: StateFlow<Boolean> = _shareToFeed.asStateFlow()

    fun loadMedia(mediaId: Int) {
        viewModelScope.launch {
            val result = mediaRepository.getMediaDetail(mediaId)
            if (result is MediaDetailResult.Success) {
                _media.value = result.media
            }
        }
    }

    fun onRatingChange(value: Int)          { _rating.value     = value }
    fun onReviewTextChange(value: String)   {
        if (value.length <= 500) _reviewText.value = value
    }
    fun onShareToFeedChange(value: Boolean) { _shareToFeed.value = value }
}