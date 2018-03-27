package uk.whitecrescent.waqti

import uk.whitecrescent.waqti.task.DATABASE
import uk.whitecrescent.waqti.task.GRACE_PERIOD
import uk.whitecrescent.waqti.task.Task
import uk.whitecrescent.waqti.task.TaskID
import java.time.Duration

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

fun <T> List<T>.toArrayList(): ArrayList<T> {
    return this as ArrayList<T>
}

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