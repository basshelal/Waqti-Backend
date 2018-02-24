package uk.whitecrescent.waqti.code

import java.time.Duration
import java.time.LocalDateTime

class Task(var title: String) {

    private var state = DEFAULT_TASK_STATE // Existing
    var isFailable = false
    var isKillable = true // not can kill now, more like is it possible to ever be killed, doesn't apply to Templates
    var taskID = GSON.newID()

    // Task Properties, all initially set to default values

    var time: Property<LocalDateTime> = DEFAULT_TIME_PROPERTY
    var duration: Property<Duration> = DEFAULT_DURATION_PROPERTY
    var priority: Property<Priority> = DEFAULT_PRIORITY_PROPERTY
    var label: Property<Label> = DEFAULT_LABEL_PROPERTY
    var optional: Property<Boolean> = DEFAULT_OPTIONAL_PROPERTY
    var description: Property<StringBuilder> = DEFAULT_DESCRIPTION_PROPERTY
    var checkList: Property<CheckList> = DEFAULT_CHECKLIST_PROPERTY
    var deadline: Property<LocalDateTime> = DEFAULT_DEADLINE_PROPERTY
    var target: Property<String> = DEFAULT_TARGET_PROPERTY

    // TODO make before and after use something other than Tasks, like TaskID, using Tasks causes infinite loops
    // var before: Property<Task?> = DEFAULT_BEFORE_PROPERTY
    // var after: Property<Task?> = DEFAULT_AFTER_PROPERTY

    private fun getAllProperties() = listOf(
            time,
            duration,
            priority,
            label,
            optional,
            description,
            checkList,
            deadline,
            target)

    private fun getAllConstraints() = getAllProperties().filter { it is Constraint }

    fun getAllShowingProperties() =
            getAllProperties().filter { it.isVisible }

    fun getAllShowingConstraints() =
            getAllConstraints().filter { it.isVisible }

    fun getAllUnmetAndShowingConstraints() =
            getAllConstraints().filter { !(it as Constraint).isMet && it.isVisible }

    private fun isNotConstraint(property: Property<*>) = property !is Constraint

    // For chaining

    fun setTimeProperty(timeProperty: Property<LocalDateTime>): Task {
        this.time = timeProperty
        return this
    }

    fun setTimeValue(time: LocalDateTime): Task {
        this.time = Property(SHOWING, time)
        return this
    }

    fun setDurationProperty(durationProperty: Property<Duration>): Task {
        this.duration = durationProperty
        return this
    }

    fun setDurationValue(duration: Duration): Task {
        this.duration = Property(SHOWING, duration)
        return this
    }

    fun setPriorityProperty(priorityProperty: Property<Priority>): Task {
        this.priority = priorityProperty
        return this
    }

    fun setPriorityValue(priority: Priority): Task {
        this.priority = Property(SHOWING, priority)
        return this
    }

    fun setLabelProperty(labelProperty: Property<Label>): Task {
        this.label = labelProperty
        return this
    }

    fun setLabelValue(label: Label): Task {
        this.label = Property(SHOWING, label)
        return this
    }

    fun setOptionalProperty(optionalProperty: Property<Boolean>): Task {
        this.optional = optionalProperty
        return this
    }

    fun setOptionalValue(optional: Boolean): Task {
        this.optional = Property(SHOWING, optional)
        return this
    }

    fun setDescriptionProperty(descriptionProperty: Property<StringBuilder>): Task {
        this.description = descriptionProperty
        return this
    }

    fun setDescriptionValue(description: StringBuilder): Task {
        this.description = Property(SHOWING, description)
        return this
    }

    fun setChecklistProperty(checkListProperty: Property<CheckList>): Task {
        this.checkList = checkListProperty
        return this
    }

    fun setChecklistValue(checkList: CheckList): Task {
        this.checkList = Property(SHOWING, checkList)
        return this
    }

    fun setDeadlineProperty(deadlineProperty: Property<LocalDateTime>): Task {
        this.deadline = deadlineProperty
        return this
    }

    fun setDeadlineValue(deadline: LocalDateTime): Task {
        this.deadline = Property(SHOWING, deadline)
        return this
    }

    fun setTargetProperty(targetProperty: Property<String>): Task {
        this.target = targetProperty
        return this
    }

    fun setTargetValue(target: String): Task {
        this.target = Property(SHOWING, target)
        return this
    }

    // Hide Properties functions, sets them to default values if they are not Constraints

    fun hideTime() {
        if (isNotConstraint(time)) {
            time = DEFAULT_TIME_PROPERTY
        } else throw IllegalStateException("Cannot hide, time is Constraint")
    }

    fun hideDuration() {
        if (isNotConstraint(duration)) {
            duration = DEFAULT_DURATION_PROPERTY
        } else throw IllegalStateException("Cannot hide, duration is Constraint")
    }

    fun hidePriority() {
        if (isNotConstraint(priority)) {
            priority = DEFAULT_PRIORITY_PROPERTY
        } else throw IllegalStateException("Cannot hide, priority is Constraint")
    }

    fun hideLabel() {
        if (isNotConstraint(label)) {
            label = DEFAULT_LABEL_PROPERTY
        } else throw IllegalStateException("Cannot hide, label is Constraint")
    }

    fun hideOptional() {
        if (isNotConstraint(optional)) {
            optional = DEFAULT_OPTIONAL_PROPERTY
        } else throw IllegalStateException("Cannot hide, optional is Constraint")
    }

    fun hideDescription() {
        if (isNotConstraint(description)) {
            description = DEFAULT_DESCRIPTION_PROPERTY
        } else throw IllegalStateException("Cannot hide, description is Constraint")
    }

    fun hideChecklist() {
        if (isNotConstraint(checkList)) {
            checkList = DEFAULT_CHECKLIST_PROPERTY
        } else throw IllegalStateException("Cannot hide, checklist is Constraint")
    }

    fun hideDeadline() {
        if (isNotConstraint(deadline)) {
            deadline = DEFAULT_DEADLINE_PROPERTY
        } else throw IllegalStateException("Cannot hide, deadline is Constraint")
    }

    fun hideTarget() {
        if (isNotConstraint(target)) {
            target = DEFAULT_TARGET_PROPERTY
        } else throw IllegalStateException("Cannot hide, target is Constraint")
    }

    // Task lifecycle
    //TODO Tue-20-Feb-18 For now we'll have only 2 states working, EXISTING and KILLED

    fun getTaskState() = state

    private fun isExisting() = state == TaskState.EXISTING

    fun canKill() = isKillable &&
            isExisting() &&
            getAllUnmetAndShowingConstraints().isEmpty()


    fun canFail() = isFailable &&
            isExisting()

    // Tue-20-Feb-18 Let's leave this for now
    fun fail() {
        if (state == TaskState.FAILED) {
            throw TaskStateException("Fail unsuccessful, ${this.title} is already Failed!", getTaskState())
        }
        if (!isFailable) {
            throw TaskStateException("Fail unsuccessful, ${this.title} is not Failable", getTaskState())
        } else if (canFail()) {
            state = TaskState.FAILED
        }
    }


    // If a Task is killed it can be modified because it doesn't matter at all, after killed there is no other State.
    fun kill() {
        if (state == TaskState.KILLED) {
            throw TaskStateException("Kill unsuccessful, ${this.title} is already Killed!", getTaskState())
        }
        if (!isKillable) {
            throw TaskStateException("Kill unsuccessful, ${this.title} is not Killable", getTaskState())
        }
        if (!getAllUnmetAndShowingConstraints().isEmpty()) {
            throw TaskStateException("Kill unsuccessful, ${this.title} has unmet Constraints", getTaskState())
        } else if (canKill()) {
            state = TaskState.KILLED
        } else {
            throw TaskStateException("Kill unsuccessful, unknown reason, remember only EXISTING tasks can be killed!", getTaskState())
        }
    }

    // Database stuff

    fun saveToJSON(): Task {
        GSON.saveTask(this)
        return this
    }

    // Used by JSON only!
    constructor(
            state: TaskState,
            isFailable: Boolean,
            isKillable: Boolean,
            taskID: Long,
            time: Property<LocalDateTime>,
            duration: Property<Duration>,
            priority: Property<Priority>,
            label: Property<Label>,
            optional: Property<Boolean>,
            description: Property<StringBuilder>,
            checkList: Property<CheckList>,
            deadline: Property<LocalDateTime>,
            target: Property<String>,
            title: String
    ) : this(title) {
        this.state = state
        this.isFailable = isFailable
        this.isKillable = isKillable
        this.taskID = taskID
        this.time = time
        this.duration = duration
        this.priority = priority
        this.label = label
        this.optional = optional
        this.description = description
        this.checkList = checkList
        this.deadline = deadline
        this.target = target

    }

    // Overriden from kotlin.Any

    override fun hashCode() =
            title.hashCode() + getAllShowingProperties().hashCode()

    override fun equals(other: Any?) =
            other is Task &&
                    other.title.equals(this.title) &&
                    other.getTaskState().equals(this.state) &&
                    other.getAllShowingProperties().equals(this.getAllShowingProperties())

    override fun toString(): String {
        val s = StringBuilder("Task: title = $title\n")

        getAllShowingProperties().forEach { s.append("$it \n") }

        s.append("state = $state\n\n")

        return s.toString()
    }
}

enum class TaskState {
    WAITING, // Not yet relevant or Waiting to be relevant once again, after being failed
    EXISTING,
    FAILED,
    KILLED,
    IMMORTAL // Template
}


