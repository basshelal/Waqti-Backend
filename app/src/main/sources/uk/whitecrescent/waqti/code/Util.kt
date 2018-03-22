package uk.whitecrescent.waqti.code

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

//TODO delete this, put the threads in Constants.kt and check that they're sensible
object Concurrent {

    val timeCheckingThread = Schedulers.newThread()
    val stateCheckingThread = Schedulers.newThread()
    val otherTaskCheckingThread = Schedulers.newThread()

    /**
     * An Observable that emits every so often on the time checking Thread.
     * This is useful for having a single Observable for repeating every certain time period, since
     * having many can be computationally expensive.
     * You can add multiple different kinds of Observers to this and hence the power and efficiency of it.
     * The emitting repeats indefinitely which can be very useful for constant concurrent checking.
     */
    val timeCheckerObservable =
            Observable.interval(TIME_CHECKING_PERIOD, TIME_CHECKING_UNIT)
                    .subscribeOn(timeCheckingThread)

    fun taskStateCheckingObservable(task: Task): Observable<TaskState> {
        return Observable.just(task.getTaskState()).subscribeOn(timeCheckingThread)
    }

    fun <T> repeatAndGetEvery(func: () -> T, period: Long, timeUnit: TimeUnit) {
        Observable.interval(period, timeUnit)
                .subscribeOn(timeCheckingThread)
                .subscribe(
                        { func.invoke() },
                        {},
                        { throw IllegalStateException("Could not complete concurrent operation") },
                        {}
                )
    }

}

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
        val task = database.get(id)
        if (task != null) {
            tasks.add(task)
        }
    }
    return tasks
}

fun taskIDsToTasks(vararg taskIDs: TaskID): ArrayList<Task> {
    val tasks = ArrayList<Task>(taskIDs.size)
    for (id in taskIDs) {
        val task = database.get(id)
        if (task != null) {
            tasks.add(task)
        }
    }
    return tasks
}

// Extension
fun <T> List<T>.toArrayList() : ArrayList<T> {
    return this as ArrayList<T>
}

// When implementing this API, change usages of this to use your database system appropriately
val database = ConcurrentHashMap<TaskID, Task>(5000)