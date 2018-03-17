package uk.whitecrescent.waqti.code

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

//region Old GSON, will delete soon
//object GSON {
//
//    private val gson = Gson()
//    private val TASK_ID_FILE = "C:\\Users\\bassh\\GitHub Repositories\\Waqti\\app\\src\\main\\sources\\database\\taskid.json"
//    private val TASKS_FILE = "C:\\Users\\bassh\\GitHub Repositories\\Waqti\\app\\src\\main\\sources\\database\\tasks.json"
//    private val PRIORITIES_FILE = "C:\\Users\\bassh\\GitHub Repositories\\Waqti\\app\\src\\main\\sources\\database\\priorities.json"
//
//    //be careful with the order of insertion (and thus order of reading), do check them!
//
//
//
//    fun readAllIDs(): Array<Long> {
//        val reader = BufferedReader(FileReader(TASK_ID_FILE))
//        return gson.fromJson<Array<Long>>(reader, Array<Long>::class.java)
//    }
//
//    fun saveID(vararg ids: Long) {
//        val currentIDs = readAllIDs()
//        val fileWriter = BufferedWriter(FileWriter(TASK_ID_FILE))
//        val array = Array(ids.size, { ids[it] })
//        gson.toJson(arrayOf(*array, *currentIDs), fileWriter)
//        fileWriter.close()
//    }
//
//    fun newID(): Long {
//        var id = Math.abs(Random().nextLong())
//        while (readAllIDs().contains(id)) {
//            id = Math.abs(Random().nextLong())
//        }
//        saveID(id)
//        return id
//    }
//
//    fun clearTaskIDFile() {
//        // Cannot work if the file is completely empty (meaning no brackets even) but this should almost never be the case
//        val fileWriter = BufferedWriter(FileWriter(TASK_ID_FILE))
//        gson.toJson(listOf<Long>(), fileWriter)
//        fileWriter.close()
//    }
//
//    fun readIDsFromTasks(): Array<Long> {
//        val reader = BufferedReader(FileReader(TASKS_FILE))
//        val allTasks = gson.fromJson<Array<Task>>(reader, Array<Task>::class.java)
//
//        return Array<Long>(allTasks.size, { allTasks[it].taskID })
//
//    }
//
//
//
//
//
//    // Newest First, Oldest Last
//    fun readAllTasks(): ArrayList<Task> {
//        val reader = BufferedReader(FileReader(TASKS_FILE))
//        return gson.fromJson<ArrayList<Task>>(reader, ArrayList::class.java)
//    }
//
//    fun getTaskAt(index: Int): Task {
//        return readAllTasks()[index]
//    }
//
//    fun getTask(task: Task): Task {
//        val task0 = readAllTasks().find { it == task }
//        if (task0 == null) {
//            throw IllegalArgumentException("Cannot find")
//        } else return task0
//    }
//
//    fun getTaskByID(id: Long): Task {
//        val task = readAllTasks().find { it.taskID == id }
//        if (task == null) {
//            throw IllegalArgumentException("Cannot find")
//        } else return task
//    }
//
//    fun getTasksByTitle(title: String): List<Task> {
//        return readAllTasks().filter { it.title == title }
//    }
//
//    fun saveTaskAt(index: Int, task: Task) {
//        val allTasksList = readAllTasks().toMutableList()
//        allTasksList.add(index, task)
//        val fileWriter = BufferedWriter(FileWriter(TASKS_FILE))
//        gson.toJson(allTasksList.toTypedArray(), fileWriter)
//        fileWriter.close()
//    }
//
//    fun deleteTask(task: Task) {
//        val allTasksList = readAllTasks().toMutableList()
//        allTasksList.removeAt(allTasksList.indexOf(task))
//        val fileWriter = BufferedWriter(FileWriter(TASKS_FILE))
//        gson.toJson(allTasksList.toTypedArray(), fileWriter)
//        fileWriter.close()
//    }
//
//    fun deleteTaskAt(index: Int) {
//        deleteTask(getTaskAt(index))
//    }
//
//    //Trimming to size before re-adding back to JSON is a problem
//
//    fun deleteAll(predicate: (Task) -> Boolean) {
//        val allTasksList = readAllTasks().toMutableList()
//        allTasksList.removeIf(predicate)
//        val fileWriter = BufferedWriter(FileWriter(TASKS_FILE))
//        gson.toJson(allTasksList.toTypedArray(), fileWriter)
//        fileWriter.close()
//    }
//
//    // To the top of file
//    fun saveTask(vararg tasks: Task) {
//        val allTasks = readAllTasks()
//        val fileWriter = BufferedWriter(FileWriter(TASKS_FILE))
//        val array = Array(tasks.size, { tasks[it] })
//        gson.toJson(arrayListOf(*array, *allTasks.toArray()), fileWriter)
//        fileWriter.close()
//    }
//
//    fun clearTasksFile() {
//        val fileWriter = BufferedWriter(FileWriter(TASKS_FILE))
//        gson.toJson(listOf<Task>(), fileWriter)
//        fileWriter.close()
//    }
//
//
//
//}

//endregion Old GSON, will delete soon

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

fun tasksToTaskIDs(tasks: List<Task>): List<TaskID> {
    val ids = ArrayList<TaskID>(tasks.size)
    tasks.forEach { ids.add(it.taskID) }
    return ids
}

fun taskIDsToTasks(taskIDs: List<TaskID>): List<Task> {
    val tasks = ArrayList<Task>(taskIDs.size)
    for (id in taskIDs) {
        val task = database.get(id)
        if (task != null) {
            tasks.add(task)
        }
    }
    return tasks
}

val database = ConcurrentHashMap<Long, Task>(5000)