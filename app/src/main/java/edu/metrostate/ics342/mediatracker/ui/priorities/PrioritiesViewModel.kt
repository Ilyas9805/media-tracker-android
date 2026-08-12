package edu.metrostate.ics342.mediatracker.ui.priorities

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.metrostate.ics342.mediatracker.data.datastore.DefaultSessionRepository
import edu.metrostate.ics342.mediatracker.data.model.Priority
import edu.metrostate.ics342.mediatracker.data.network.DefaultMediaRepository
import edu.metrostate.ics342.mediatracker.data.network.PriorityRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PrioritiesViewModel(application: Application) : AndroidViewModel(application) {

    private val mediaRepository = DefaultMediaRepository(
        DefaultSessionRepository(application)
    )

    private val _priorities = MutableStateFlow<List<Priority>>(emptyList())
    val priorities: StateFlow<List<Priority>> = _priorities.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadPriorities()
    }

    fun loadPriorities() {
        viewModelScope.launch {
            _isLoading.value = true
            _priorities.value = mediaRepository.getPriorities()
                .sortedBy { it.orderIndex }
            _isLoading.value = false
        }
    }

    fun addPriority(mediaId: Int, priority: Int, estimatedTimeHours: Float, notes: String?) {
        viewModelScope.launch {
            val current    = _priorities.value.toMutableList()
            val existing   = current.indexOfFirst { it.mediaId == mediaId }
            val orderIndex = if (existing >= 0) current[existing].orderIndex
            else current.size

            val request = PriorityRequest(
                mediaId            = mediaId,
                priority           = priority,
                orderIndex         = orderIndex,
                estimatedTimeHours = estimatedTimeHours,
                notes              = notes
            )

            val result = mediaRepository.setPriority(request)
            if (result != null) {
                // Reload full list from server after adding
                _priorities.value = mediaRepository.getPriorities()
                    .sortedBy { it.orderIndex }
            }
        }
    }

    fun removePriority(mediaId: Int) {
        // No DELETE endpoint exists — remove from local state only
        _priorities.value = _priorities.value.filter { it.mediaId != mediaId }
    }
}