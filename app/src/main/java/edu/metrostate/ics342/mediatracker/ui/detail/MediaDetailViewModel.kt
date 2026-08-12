package edu.metrostate.ics342.mediatracker.ui.detail

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

class MediaDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val mediaRepository = DefaultMediaRepository(
        DefaultSessionRepository(application)
    )

    sealed class UiState {
        object Loading  : UiState()
        object NotFound : UiState()
        object Error    : UiState()
        data class Success(val media: Media) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    // ── Library status ────────────────────────────────────────────────────
    private val _libraryStatus = MutableStateFlow<String?>(null)
    val libraryStatus: StateFlow<String?> = _libraryStatus.asStateFlow()

    // ── Favorite status ───────────────────────────────────────────────────
    private val _isFavorited = MutableStateFlow(false)
    val isFavorited: StateFlow<Boolean> = _isFavorited.asStateFlow()

    fun loadMedia(mediaId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            // Load media detail
            _uiState.value = when (val result = mediaRepository.getMediaDetail(mediaId)) {
                is MediaDetailResult.Success      -> UiState.Success(result.media)
                is MediaDetailResult.NotFound     -> UiState.NotFound
                is MediaDetailResult.NetworkError -> UiState.Error
                is MediaDetailResult.UnknownError -> UiState.Error
            }

            // Check library status
            val libraryEntry = mediaRepository.getLibraryEntry(mediaId)
            _libraryStatus.value = libraryEntry?.status

            // Check favorite status
            val favoriteEntry = mediaRepository.getFavoriteEntry(mediaId)
            _isFavorited.value = favoriteEntry != null
        }
    }

    fun onLibraryStatusSelected(mediaId: Int, status: String) {
        viewModelScope.launch {
            if (_libraryStatus.value == null) {
                // Not in library yet — add it
                val result = mediaRepository.addToLibrary(mediaId, status)
                if (result != null) _libraryStatus.value = result.status
            } else {
                // Already in library — update status
                val result = mediaRepository.updateLibraryEntry(mediaId, status)
                if (result != null) _libraryStatus.value = result.status
            }
        }
    }

    fun onFavoriteClick(mediaId: Int) {
        viewModelScope.launch {
            if (!_isFavorited.value) {
                val result = mediaRepository.addToFavorites(mediaId)
                if (result != null) _isFavorited.value = true
            }
        }
    }
}