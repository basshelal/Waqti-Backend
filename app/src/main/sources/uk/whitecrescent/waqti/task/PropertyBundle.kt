package uk.whitecrescent.waqti.task

import uk.whitecrescent.waqti.Duration
import uk.whitecrescent.waqti.Time

@Suppress("UNCHECKED_CAST")
class PropertyBundle(val task: Task) {

    private val properties = arrayOf(*ALL_PROPERTIES)
    private val timeIndex = 0
    private val durationIndex = 1
    private val priorityIndex = 2
    private val labelsIndex = 3
    private val optionalIndex = 4
    private val descriptionIndex = 5
    private val checklistIndex = 6
    private val targetIndex = 7
    private val deadlineIndex = 8
    private val beforeIndex = 9
    private val subTasksIndex = 10

    var time: Property<Time>
        set(value) {
            properties[timeIndex] = value
        }
        get() = properties[timeIndex] as Property<Time>

    var duration: Property<Duration>
        set(value) {
            properties[durationIndex] = value
        }
        get() = properties[durationIndex] as Property<Duration>

    var priority: Property<Priority>
        set(value) {
            properties[priorityIndex] = value
        }
        get() = properties[priorityIndex] as Property<Priority>

    var labels: Property<ArrayList<Label>>
        set(value) {
            properties[labelsIndex] = value
        }
        get() = properties[labelsIndex] as Property<ArrayList<Label>>

    var optional: Property<Optional>
        set(value) {
            properties[optionalIndex] = value
        }
        get() = properties[optionalIndex] as Property<Optional>

    var description: Property<Description>
        set(value) {
            properties[descriptionIndex] = description
        }
        get() = properties[descriptionIndex] as Property<Description>

    var checklist: Property<Checklist>
        set(value) {
            properties[checklistIndex] = value
        }
        get() = properties[checklistIndex] as Property<Checklist>

    var target: Property<Target>
        set(value) {
            properties[targetIndex] = value
        }
        get() = properties[targetIndex] as Property<Target>

    var deadline: Property<Time>
        set(value) {
            properties[deadlineIndex] = value
        }
        get() = properties[deadlineIndex] as Property<Time>

    var before: Property<ID>
        set(value) {
            properties[beforeIndex] = value
        }
        get() = properties[beforeIndex] as Property<ID>

    var subTasks: Property<ArrayList<ID>>
        set(value) {
            properties[subTasksIndex] = value
        }
        get() = properties[subTasksIndex] as Property<ArrayList<ID>>

    init {
        time = task.time
        duration = task.duration
        priority = task.priority
        labels = task.labels
        optional = task.optional
        description = task.description
        checklist = task.checklist
        target = task.target
        deadline = task.deadline
        before = task.before
        subTasks = task.subTasks
        assert(properties.size == 11) // TODO: 19-May-18 remember to remove this
    }

}