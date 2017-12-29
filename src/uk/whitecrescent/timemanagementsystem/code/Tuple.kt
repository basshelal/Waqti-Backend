package uk.whitecrescent.timemanagementsystem.code

class Tuple(vararg tasksVararg: Task) {
    var tasks = ArrayList<Task>(tasksVararg.asList())

    init {
        for (index in 1..tasksVararg.lastIndex - 1) {
            tasksVararg[index].before = Constraint(SHOWING, tasksVararg[index - 1], UNMET)
            tasksVararg[index].after = Constraint(SHOWING, tasksVararg[index + 1], UNMET)
        }
        tasksVararg[0].after = Constraint(SHOWING, tasksVararg[1], UNMET)
        tasksVararg[tasksVararg.lastIndex].before = Constraint(SHOWING, tasksVararg[tasksVararg.lastIndex - 1], UNMET)
    }

    fun addAndConstrain(index: Int = tasks.size + 1, task: Task) {
        tasks.last().after = Constraint(SHOWING, task, UNMET)
        task.before = Constraint(SHOWING, tasks.last(), UNMET)
        tasks.add(index, task)
    }

    fun add(index: Int = tasks.size + 1, task: Task) {
        tasks.add(index, task)
    }

    fun killTaskAt(index: Int) {
        if (tasks[index - 1].getTaskState().equals(TaskState.KILLED)) {
            tasks[index].kill()
            tasks[index + 1].before = Constraint(HIDDEN, tasks[index], MET)
        }
    }

    fun killTask(task: Task) {
        killTaskAt(tasks.indexOf(task))
    }

}
