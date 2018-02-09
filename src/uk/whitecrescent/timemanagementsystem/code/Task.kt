package uk.whitecrescent.timemanagementsystem.code

import java.time.Duration
import java.time.LocalDateTime
import java.util.Random

class Task(var title: String) {

    private var state = DEFAULT_TASK_STATE
    var isFailable = false
    var isKillable = true
    val taskID = Math.abs(Random().nextLong())
    fun taskCode() = Math.abs(LocalDateTime.now().hashCode())

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

    fun getTaskState() = this.state

    fun canKill() =
            state != TaskState.KILLED &&
                    isKillable &&
                    getAllUnmetAndShowingConstraints().isEmpty()

    fun canFail() =
            state != TaskState.FAILED &&
                    isFailable


    //TODO Failing is still a problem since what decides if we can fail it?? Actually it's because was killable and now no longer killable
    fun fail() {
        if (state == TaskState.FAILED) {
            throw IllegalStateException("Fail unsuccessful, $this is already Failed!")
        }
        if (!isFailable) {
            throw IllegalStateException("Fail unsuccessful, $this is not Failable")
        } else if (canFail()) {
            state = TaskState.FAILED
        }
    }

    fun kill() {
        if (state == TaskState.KILLED) {
            throw IllegalStateException("Kill unsuccessful, $this is already Killed!")
        }
        if (!isKillable) {
            throw IllegalStateException("Kill unsuccessful, $this is not Killable")
        }
        if (!getAllUnmetAndShowingConstraints().isEmpty()) {
            throw IllegalStateException("Kill unsuccessful, $this has unmet Constraints")
        } else if (canKill()) {
            state = TaskState.KILLED
        }
    }


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
    UNBORN,
    EXISTING,
    FAILED,
    SLEEPING,
    KILLED,
    IMMORTAL
}


