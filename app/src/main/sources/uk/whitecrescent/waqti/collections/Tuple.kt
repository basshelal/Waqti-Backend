package uk.whitecrescent.waqti.collections

import uk.whitecrescent.waqti.task.Task

class Tuple(vararg tasks: Task) {

    private val tasksList: ArrayList<Task>

    operator fun get(index: Int) = tasksList[index]

    operator fun get(task: Task) = tasksList.find { it == task }

    init {
        tasksList = ArrayList(tasks.asList())
        when {
            tasksList.size < 1 -> {
                throw IllegalStateException("Tuple must have at least 1 Task!")
            }
            tasksList.size > 1 -> {
                for (index in 1..tasksList.lastIndex) {
                    tasksList[index].setBeforePropertyValue(tasksList[index - 1].taskID)
                }
            }
        }
    }

    fun constrainAll() {
        for (index in 1..tasksList.lastIndex) {
            tasksList[index].setBeforeConstraintValue(tasksList[index - 1].taskID)
        }
    }

    fun unConstrainAll() {
        for (index in 1..tasksList.lastIndex) {
            tasksList[index].setBeforePropertyValue(tasksList[index - 1].taskID)
        }
    }

    fun addToEnd(task: Task) {

    }

    fun addAt(task: Task, index: Int) {
    }


    // Adds the passed in Task to the end of the Tuple
    fun addAndConstrain(task: Task) {

    }

    fun addAndConstrainAt(index: Int, task: Task) {

    }

    fun killTaskAt(index: Int) {
        when {
            index < 0 -> {
                throw IndexOutOfBoundsException("Index cannot be 0")
            }
            index > tasksList.size - 1 -> {
                throw IndexOutOfBoundsException("Index cannot exceed ${tasksList.size - 1}")
            }
            else -> {
                tasksList[index].kill()
            }
        }
    }

    fun killTask(task: Task) {
        val task0 = get(task)
        when {
            task0 == null -> {
                throw IllegalStateException("Task not found!")
            }
            else -> {
                task0.kill()
            }
        }
    }

}
