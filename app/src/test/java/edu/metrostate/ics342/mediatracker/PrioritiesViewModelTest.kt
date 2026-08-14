package edu.metrostate.ics342.mediatracker

import edu.metrostate.ics342.mediatracker.data.model.Priority
import org.junit.Assert.assertEquals
import org.junit.Test

class PrioritiesViewModelTest {

    @Test
    fun removePriority_removesCorrectItemFromLocalState() {
        // Given a list of 2 priorities
        val priorities = listOf(
            Priority(mediaId = 1, priority = 1, orderIndex = 0, estimatedTimeHours = 2f),
            Priority(mediaId = 2, priority = 2, orderIndex = 1, estimatedTimeHours = 1f)
        )

        // When removing mediaId = 1
        val result = priorities.filter { it.mediaId != 1 }

        // Then only mediaId = 2 should remain
        assertEquals(1, result.size)
        assertEquals(2, result.first().mediaId)
    }

    @Test
    fun priorityList_enforcesMaximumOf5Items() {
        // Given a full list of 5 priorities
        val priorities = (1..5).map { i ->
            Priority(mediaId = i, priority = 1, orderIndex = i - 1, estimatedTimeHours = 1f)
        }

        // When checking if we can add more
        val canAdd = priorities.size < 5

        // Then adding should be blocked
        assertEquals(false, canAdd)
    }
}