package uk.whitecrescent.waqti.code

import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

const val NUMBER_OF_PROPERTIES = 10

const val TIME_CHECKING_PERIOD = 1L
val TIME_CHECKING_UNIT = TimeUnit.SECONDS

const val HIDDEN = false
const val SHOWING = true

const val MET = true
const val UNMET = false

const val DEFAULT_FAILABLE = false
const val DEFAULT_KILLABLE = true

val DEFAULT_TASK_STATE = TaskState.EXISTING
val DEFAULT_TIME = LocalDateTime.MIN!!
val DEFAULT_DURATION = Duration.ZERO!!
val DEFAULT_PRIORITY = Priority.createNewPriority("DEFAULT")
val DEFAULT_LABEL = Label.createNewLabel("DEFAULT")
val DEFAULT_OPTIONAL = false
val DEFAULT_DESCRIPTION = StringBuilder("DEFAULT")
val DEFAULT_CHECKLIST = CheckList()
val DEFAULT_TARGET = "DEFAULT"
val DEFAULT_DEADLINE = LocalDateTime.MIN!!
val DEFAULT_TASK_ID: Long = 0L

val DEFAULT_TIME_PROPERTY = Property(HIDDEN, DEFAULT_TIME)
val DEFAULT_DURATION_PROPERTY = Property(HIDDEN, DEFAULT_DURATION)
val DEFAULT_PRIORITY_PROPERTY = Property(HIDDEN, DEFAULT_PRIORITY)
val DEFAULT_LABEL_PROPERTY = Property(HIDDEN, DEFAULT_LABEL)
val DEFAULT_OPTIONAL_PROPERTY = Property(HIDDEN, DEFAULT_OPTIONAL)
val DEFAULT_DESCRIPTION_PROPERTY = Property(HIDDEN, DEFAULT_DESCRIPTION)
val DEFAULT_CHECKLIST_PROPERTY = Property(HIDDEN, DEFAULT_CHECKLIST)
val DEFAULT_TARGET_PROPERTY = Property(HIDDEN, DEFAULT_TARGET)
val DEFAULT_DEADLINE_PROPERTY = Property(HIDDEN, DEFAULT_DEADLINE)
val DEFAULT_BEFORE_PROPERTY = Property(HIDDEN, DEFAULT_TASK_ID)
val DEFAULT_AFTER_PROPERTY = Property(HIDDEN, DEFAULT_TASK_ID)

