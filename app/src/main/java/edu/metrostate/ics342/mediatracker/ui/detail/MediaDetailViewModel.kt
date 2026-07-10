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

    fun loadMedia(mediaId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = when (val result = mediaRepository.getMediaDetail(mediaId)) {
                is MediaDetailResult.Success      -> UiState.Success(result.media)
                is MediaDetailResult.NotFound     -> UiState.NotFound
                is MediaDetailResult.NetworkError -> UiState.Error
                is MediaDetailResult.UnknownError -> UiState.Error
            }
        }
    }
}