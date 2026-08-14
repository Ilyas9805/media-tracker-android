package edu.metrostate.ics342.mediatracker.ui.library

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.metrostate.ics342.mediatracker.data.datastore.DefaultSessionRepository
import edu.metrostate.ics342.mediatracker.data.model.LibraryEntry
import edu.metrostate.ics342.mediatracker.data.model.LibraryStatus
import edu.metrostate.ics342.mediatracker.data.network.DefaultMediaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


//API
class LibraryViewModel(application: Application) : AndroidViewModel(application) {

    private val mediaRepository = DefaultMediaRepository(
        DefaultSessionRepository(application)
    )

    private val _libraryItems = MutableStateFlow<List<LibraryEntry>>(emptyList())
    val libraryItems: StateFlow<List<LibraryEntry>> = _libraryItems.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _filterState = MutableStateFlow(LibraryStatus.WANT_TO)
    val filterState: StateFlow<LibraryStatus> = _filterState.asStateFlow()

    init {
        loadLibrary()
    }

    fun loadLibrary() {
        viewModelScope.launch {
            _isLoading.value = true
            val status = when (_filterState.value) {
                LibraryStatus.WANT_TO     -> "want_to"
                LibraryStatus.IN_PROGRESS -> "in_progress"
                LibraryStatus.FINISHED    -> "finished"
            }
            _libraryItems.value = mediaRepository.getLibrary(status = status)
            _isLoading.value = false
        }
    }

    fun updateFilter(status: LibraryStatus) {
        _filterState.value = status
        loadLibrary()
    }

    fun removeItem(mediaId: Int) {
        viewModelScope.launch {
            val success = mediaRepository.removeFromLibrary(mediaId)
            if (success) {
                _libraryItems.value = _libraryItems.value.filter { it.mediaId != mediaId }
            }
        }
    }

    fun changeStatus(mediaId: Int, newStatus: String) {
        viewModelScope.launch {
            val result = mediaRepository.updateLibraryEntry(mediaId, newStatus)
            if (result != null) {
                _libraryItems.value = _libraryItems.value.map { entry ->
                    if (entry.mediaId == mediaId) entry.copy(status = newStatus) else entry
                }
            }
        }
    }
}