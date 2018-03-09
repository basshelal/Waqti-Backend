package uk.whitecrescent.waqti.code

// We'll let this implementation constrain all the Tasks to one another, we can create a similar implementation that
// has them just as Properties
class Tuple(vararg tasks: Task) {

    private val tasksList: ArrayList<Task>

    operator fun get(index: Int) = tasksList[index]

    operator fun get(task: Task) = tasksList[tasksList.indexOf(task)]

    init {
        tasksList = ArrayList(tasks.asList())
        if (tasksList.size <= 1) {
            throw IllegalStateException("Tuple must have more than 1 Task!")
        }
        tasksList[0].setAfterConstraint(Constraint(SHOWING, tasksList[1].taskID, UNMET))
        for (index in 1..tasksList.lastIndex - 1) {
            tasksList[index].setBeforeConstraint(Constraint(SHOWING, tasksList[index - 1].taskID, UNMET))
            tasksList[index].setAfterConstraint(Constraint(SHOWING, tasksList[index + 1].taskID, UNMET))
        }
        tasksList[tasksList.lastIndex].setBeforeConstraint(Constraint(SHOWING, tasksList[tasksList.lastIndex - 1].taskID, UNMET))
    }

    // Adds the passed in Task to the end of the Tuple
    fun addAndConstrain(task: Task) {
        tasksList.last().setAfterConstraint(Constraint(SHOWING, task.taskID, UNMET))
        task.setBeforeConstraint(Constraint(SHOWING, tasksList.last().taskID, UNMET))
        tasksList.add(task)
    }

    fun addAndConstrainAt(index: Int, task: Task) {
        tasksList[index - 1].setAfterConstraint(Constraint(SHOWING, task.taskID, UNMET))
        task.setBeforeConstraint(Constraint(SHOWING, tasksList[index - 1].taskID, UNMET))
        task.setAfterConstraint(Constraint(SHOWING, tasksList[index + 1].taskID, UNMET))
        tasksList[index + 1].setBeforeConstraint(Constraint(SHOWING, task.taskID, UNMET))
        tasksList.add(index, task)
    }

    fun killTaskAt(index: Int) {
        if (tasksList[index - 1].getTaskState().equals(TaskState.KILLED)) {
            tasksList[index].kill()
            tasksList[index + 1].setBeforeConstraint(Constraint(HIDDEN, tasksList[index].taskID, MET))
        }
    }

    fun killTask(task: Task) {
        killTaskAt(tasksList.indexOf(task))
    }

    fun setBefore(thisTask: Task, thatTask: Task) {
        thisTask.setBeforeConstraint(Constraint(SHOWING, thatTask.taskID, UNMET))
    }

    fun setAfter(thisTask: Task, thatTask: Task) {
        thisTask.setAfterConstraint(Constraint(SHOWING, thatTask.taskID, UNMET))
    }

}
