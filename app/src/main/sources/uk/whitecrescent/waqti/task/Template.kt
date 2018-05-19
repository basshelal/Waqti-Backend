package uk.whitecrescent.waqti.task

import uk.whitecrescent.waqti.Cache
import uk.whitecrescent.waqti.Cacheable

// TODO: 19-May-18 Templates, PropertyBundles and that whole thing need to be tested and doc'd

class Template(val task: Task) : Cacheable {

    private val propertyBundle = PropertyBundle(task)
    private val id = Cache.newTemplateID()

    init {
        Cache.putTemplate(this)
    }

    companion object {

        fun fromTemplate(template: Template, title: String = "New Task"): Task {
            val task = Task(title)
            val propertyBundle = template.propertyBundle
            task.setTimeProperty(propertyBundle.time)
            task.setDurationProperty(propertyBundle.duration)
            task.setPriorityProperty(propertyBundle.priority)
            task.setLabelsProperty(propertyBundle.labels)
            task.setOptionalProperty(propertyBundle.optional)
            task.setDescriptionProperty(propertyBundle.description)
            task.setChecklistProperty(propertyBundle.checklist)
            task.setDeadlineProperty(propertyBundle.deadline)
            task.setTargetProperty(propertyBundle.target)
            task.setBeforeProperty(propertyBundle.before)
            task.setSubTasksProperty(propertyBundle.subTasks)
            return task
        }
    }

    fun bundlesAreSubset(superBundle: PropertyBundle, subBundle: PropertyBundle): Boolean {
        var timeEq = true
        var durationEq = true
        var priorityEq = true
        var labelsEq = true
        var optionalEq = true
        var descriptionEq = true
        var checklistEq = true
        var deadlineEq = true
        var targetEq = true
        var beforeEq = true
        var subTasksEq = true

        if (superBundle.time != DEFAULT_TIME_PROPERTY) {
            timeEq = subBundle.time == superBundle.time
        }
        if (superBundle.duration != DEFAULT_DURATION_PROPERTY) {
            durationEq = subBundle.duration == superBundle.duration
        }
        if (superBundle.priority != DEFAULT_PRIORITY_PROPERTY) {
            priorityEq = subBundle.priority == superBundle.priority
        }
        if (superBundle.labels != DEFAULT_LABELS_PROPERTY) {
            labelsEq = subBundle.labels == superBundle.labels
        }
        if (superBundle.optional != DEFAULT_OPTIONAL_PROPERTY) {
            optionalEq = subBundle.optional == superBundle.optional
        }
        if (superBundle.description != DEFAULT_DESCRIPTION_PROPERTY) {
            descriptionEq = subBundle.description == superBundle.description
        }
        if (superBundle.checklist != DEFAULT_CHECKLIST_PROPERTY) {
            checklistEq = subBundle.checklist == superBundle.checklist
        }
        if (superBundle.deadline != DEFAULT_DEADLINE_PROPERTY) {
            deadlineEq = subBundle.deadline == superBundle.deadline
        }
        if (superBundle.target != DEFAULT_TARGET_PROPERTY) {
            targetEq = subBundle.target == superBundle.target
        }
        if (superBundle.before != DEFAULT_BEFORE_PROPERTY) {
            beforeEq = subBundle.before == superBundle.before
        }
        if (superBundle.subTasks != DEFAULT_SUB_TASKS_PROPERTY) {
            subTasksEq = subBundle.subTasks == superBundle.subTasks
        }
        return timeEq && durationEq && priorityEq && labelsEq && optionalEq && descriptionEq &&
                checklistEq && deadlineEq && targetEq && beforeEq && subTasksEq
    }

    fun taskBundlesAreSubset(superTask: Task, subTask: Task) =
            bundlesAreSubset(PropertyBundle(superTask), PropertyBundle(subTask))

    override fun id() = id
}