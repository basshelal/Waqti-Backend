package uk.whitecrescent.waqti.code

import java.time.Duration
import java.time.LocalDateTime

fun now() = LocalDateTime.now()

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

fun tasksToTaskIDs(tasks: List<Task>): ArrayList<TaskID> {
    val ids = ArrayList<TaskID>(tasks.size)
    tasks.forEach { ids.add(it.taskID) }
    return ids
}

fun tasksToTaskIDs(vararg tasks: Task): ArrayList<TaskID> {
    val ids = ArrayList<TaskID>(tasks.size)
    tasks.forEach { ids.add(it.taskID) }
    return ids
}

fun taskIDsToTasks(taskIDs: List<TaskID>): ArrayList<Task> {
    val tasks = ArrayList<Task>(taskIDs.size)
    for (id in taskIDs) {
        val task = DATABASE.get(id)
        if (task != null) {
            tasks.add(task)
        }
    }
    return tasks
}

fun taskIDsToTasks(vararg taskIDs: TaskID): ArrayList<Task> {
    val tasks = ArrayList<Task>(taskIDs.size)
    for (id in taskIDs) {
        val task = DATABASE.get(id)
        if (task != null) {
            tasks.add(task)
        }
    }
    return tasks
}

// Extension
fun <T> List<T>.toArrayList(): ArrayList<T> {
    return this as ArrayList<T>
}