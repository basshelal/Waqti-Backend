package uk.whitecrescent.timemanagementsystem.code

import java.time.Duration
import java.time.LocalDateTime

class Task(var title: String) {
    private var state = DEFAULT_TASK_STATE
    var isFailable = DEFAULT_FAILABLE
    var isKillable = DEFAULT_KILLABLE

    // Task Properties, all initially set to default values
    //TODO maybe put these in the primary constructor? Depends on the order of creation and setting
    var time: Property<LocalDateTime> = DEFAULT_TIME_PROPERTY
    var duration: Property<Duration> = DEFAULT_DURATION_PROPERTY
    var priority: Property<Priority> = DEFAULT_PRIORITY_PROPERTY
    var label: Property<Label> = DEFAULT_LABEL_PROPERTY
    var optional: Property<Boolean> = DEFAULT_OPTIONAL_PROPERTY
    var description: Property<StringBuilder> = DEFAULT_DESCRIPTION_PROPERTY
    var checkList: Property<CheckList> = DEFAULT_CHECKLIST_PROPERTY
    var deadline: Property<LocalDateTime> = DEFAULT_DEADLINE_PROPERTY
    var target: Property<String> = DEFAULT_TARGET_PROPERTY

    // Task Constraints
    var before = DEFAULT_BEFORE_CONSTRAINT
    var after = DEFAULT_AFTER_CONSTRAINT
    //TODO Change these to become Properties initially! These are a cause for many problems :(


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

    private fun getAllConstraints() = listOf<Constraint<*>>(before, after) + getAllProperties().filter { it is Constraint }

    private fun isNotConstraint(property: Property<*>) = property !is Constraint


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

    fun getAllShowingProperties() =
            getAllProperties().filter { it.isVisible }

    fun getAllShowingConstraints() =
            getAllConstraints().filter { it.isVisible }

    fun getAllUnmetAndVisibleConstraints() =
            getAllConstraints().filter { !(it as Constraint).isMet && it.isVisible }

    fun getTaskState() = this.state

    fun canKill() =
            state != TaskState.KILLED &&
                    isKillable &&
                    getAllUnmetAndVisibleConstraints().isEmpty()

    fun canFail() =
            state != TaskState.FAILED && isFailable


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
        if (!getAllUnmetAndVisibleConstraints().isEmpty()) {
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

        if (before.isVisible) s.append("before: ${before.value?.title}\n")
        if (after.isVisible) s.append("after: ${after.value?.title}\n")
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


