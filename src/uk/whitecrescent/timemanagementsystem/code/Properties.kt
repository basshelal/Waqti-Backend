package uk.whitecrescent.timemanagementsystem.code

abstract class Property(val value: Any, val task: Task) {

    override fun equals(other: Any?): Boolean {
        return value.equals(other)
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return value.toString()
    }

}
class TaskPriority(var priority: String, task: Task) : Property(priority, task)

class TaskLabel(var label: String, task: Task) : Property(label, task)

class TaskOptional(var optional: Boolean, task: Task) : Property(optional, task)

class TaskDescription(var description: StringBuilder, task: Task) : Property(description, task)

class TaskCheckList(var checkList: ArrayList<ListItem>, task: Task) : Property(checkList, task)
