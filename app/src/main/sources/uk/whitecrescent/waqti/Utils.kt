package uk.whitecrescent.waqti

import uk.whitecrescent.waqti.collections.Tuple
import uk.whitecrescent.waqti.task.DATABASE
import uk.whitecrescent.waqti.task.GRACE_PERIOD
import uk.whitecrescent.waqti.task.Task
import uk.whitecrescent.waqti.task.TaskID

fun sleep(seconds: Int) = Thread.sleep((seconds) * 1000L)

fun <T> logD(t: T) {
    println("DEBUG: ${t.toString()}")
}

fun <T> logI(t: T) {
    println("INFO: ${t.toString()}")
}

fun <T> logE(t: T) {
    error("ERROR: ${t.toString()}")
}

fun setGracePeriod(duration: Duration) {
    GRACE_PERIOD = duration
}

// Extensions

fun ArrayList<Task>.taskIDs(): ArrayList<TaskID> {
    val ids = ArrayList<TaskID>(this.size)
    this.forEach { ids.add(it.taskID) }
    return ids
}

fun ArrayList<TaskID>.tasks(): ArrayList<Task> {
    val tasks = ArrayList<Task>(this.size)
    for (id in this) {
        val task = DATABASE.get(id)
        if (task != null) {
            tasks.add(task)
        }
    }
    return tasks
}

fun Collection<Tuple>.toTasks(): Array<Task> {
    val result = ArrayList<Task>(this.size)
    for (tuple in this) {
        result.addAll(tuple.getAll())
    }
    return result.toTypedArray()
}

fun TaskID.task(): Task {
    val found = DATABASE.get(this)
    when {
        found == null -> throw IllegalStateException("Task Not Found!")
        else -> return found
    }
}