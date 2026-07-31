package edu.metrostate.ics342.mediatracker.data.network

import kotlinx.serialization.Serializable

@Serializable
//API
data class PriorityRequest(
    val mediaId            : Int,
    val priority           : Int,
    val orderIndex         : Int,
    val estimatedTimeHours : Float = 0f,
    val notes              : String? = null
)

@Serializable
data class SetPrioritiesRequest(
    val priorities: List<PriorityRequest>
)