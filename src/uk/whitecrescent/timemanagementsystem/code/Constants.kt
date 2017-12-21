package uk.whitecrescent.timemanagementsystem.code

import java.time.Duration
import java.time.LocalDateTime

val NUMBER_OF_PROPERTIES: Int = 5

val HIDDEN = false
val SHOWING = true

val DEFAULT_TIME: LocalDateTime = LocalDateTime.MIN
val DEFAULT_DURATION: Duration = Duration.ZERO
val DEFAULT_PRIORITY: Priority = Priority.createNewPriority("DEFAULT")
val DEFAULT_LABEL: Label = Label.createNewLabel("DEFAULT")
val DEFAULT_OPTIONAL: Boolean = false