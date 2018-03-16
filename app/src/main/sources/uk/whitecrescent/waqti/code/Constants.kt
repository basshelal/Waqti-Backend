package uk.whitecrescent.waqti.code

import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

// Type Aliases for more readable code
typealias Description = StringBuilder
typealias Optional = Boolean
typealias Target = String
typealias Time = LocalDateTime
typealias TaskID = Long

const val NUMBER_OF_PROPERTIES = 12

// Used for how often will Observers check stuff on the background threads
const val TIME_CHECKING_PERIOD = 1L
val TIME_CHECKING_UNIT = TimeUnit.SECONDS

// Used for Properties and Constraints
const val HIDDEN = false
const val SHOWING = true

// Used for Constraints
const val MET = true
const val UNMET = false

// Used for Optionals
const val OPTIONAL = true
const val MANDATORY = false

// Used for Tasks
const val DEFAULT_FAILABLE = false
const val DEFAULT_KILLABLE = true

val DEFAULT_TASK_STATE = TaskState.EXISTING
val DEFAULT_TIME: LocalDateTime = LocalDateTime.MIN
val DEFAULT_DURATION: Duration = Duration.ZERO
val DEFAULT_PRIORITY = Priority.getOrCreatePriority("", -1)
val DEFAULT_LABEL_LIST = arrayListOf<Label>()
const val DEFAULT_OPTIONAL = MANDATORY
val DEFAULT_DESCRIPTION = StringBuilder("")
val DEFAULT_CHECKLIST = Checklist()
const val DEFAULT_TARGET = ""
val DEFAULT_DEADLINE: LocalDateTime = LocalDateTime.MAX
const val DEFAULT_TASK_ID: Long = 0L

val DEFAULT_TIME_PROPERTY = Property(HIDDEN, DEFAULT_TIME)
val DEFAULT_DURATION_PROPERTY = Property(HIDDEN, DEFAULT_DURATION)
val DEFAULT_PRIORITY_PROPERTY = Property(HIDDEN, DEFAULT_PRIORITY)
val DEFAULT_LABEL_PROPERTY = Property(HIDDEN, DEFAULT_LABEL_LIST)
val DEFAULT_OPTIONAL_PROPERTY = Property(HIDDEN, DEFAULT_OPTIONAL)
val DEFAULT_DESCRIPTION_PROPERTY = Property(HIDDEN, DEFAULT_DESCRIPTION)
val DEFAULT_CHECKLIST_PROPERTY = Property(HIDDEN, DEFAULT_CHECKLIST)
val DEFAULT_TARGET_PROPERTY = Property(HIDDEN, DEFAULT_TARGET)
val DEFAULT_DEADLINE_PROPERTY = Property(HIDDEN, DEFAULT_DEADLINE)
val DEFAULT_BEFORE_PROPERTY = Property(HIDDEN, DEFAULT_TASK_ID)
val DEFAULT_AFTER_PROPERTY = Property(HIDDEN, DEFAULT_TASK_ID)
val DEFAULT_SUB_TASKS_PROPERTY = Property(HIDDEN, arrayListOf<TaskID>())

// Used for deadlines
var GRACE_PERIOD: Duration = Duration.ofSeconds(0)
