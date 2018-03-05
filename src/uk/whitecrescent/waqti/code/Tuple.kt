package uk.whitecrescent.waqti.code

//!!Make sure this is unused until we fix the before and after into by ID not Task- I think I fixed this, check ok?!
class Tuple(vararg tasks: Task) {
    var tasks = ArrayList<Task>(tasks.asList())

    init {
        if (tasks.size == 1) {
            throw IllegalStateException("Tuple cannot be of size 1")
        }
        for (index in 1..tasks.lastIndex - 1) {
            tasks[index].setBeforeConstraint(Constraint(SHOWING, tasks[index - 1].taskID, UNMET))
            tasks[index].setAfterConstraint(Constraint(SHOWING, tasks[index + 1].taskID, UNMET))
        }
        tasks[0].setAfterConstraint(Constraint(SHOWING, tasks[1].taskID, UNMET))
        tasks[tasks.lastIndex].setBeforeConstraint(Constraint(SHOWING, tasks[tasks.lastIndex - 1].taskID, UNMET))
    }

    fun addAndConstrain(index: Int = tasks.size + 1, task: Task) {
        tasks.last().setAfterConstraint(Constraint(SHOWING, task.taskID, UNMET))
        task.setBeforeConstraint(Constraint(SHOWING, tasks.last().taskID, UNMET))
        tasks.add(index, task)
    }

    // This should be removed since it doesn't constrain
    fun add(index: Int = tasks.size + 1, task: Task) {
        tasks.add(index, task)
    }

    fun killTaskAt(index: Int) {
        if (tasks[index - 1].getTaskState().equals(TaskState.KILLED)) {
            tasks[index].kill()
            tasks[index + 1].setBeforeConstraint(Constraint(HIDDEN, tasks[index].taskID, MET))
        }
    }

    fun killTask(task: Task) {
        killTaskAt(tasks.indexOf(task))
    }

}
