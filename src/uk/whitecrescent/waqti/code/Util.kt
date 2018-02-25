package uk.whitecrescent.waqti.code

import com.google.gson.Gson
import java.io.*
import java.util.Random

object GSON {

    private val gson = Gson()
    private val TASK_ID_FILE = "database/taskid.json"
    private val TASKS_FILE = "database/tasks.json"

    //TODO be careful with the order of insertion (and thus order of reading), do check them!

    // Task ID

    fun readAllIDs(): Array<Long> {
        val reader = BufferedReader(FileReader(TASK_ID_FILE))
        return gson.fromJson<Array<Long>>(reader, Array<Long>::class.java)
    }

    fun saveID(vararg ids: Long) {
        val currentIDs = readAllIDs()
        val fileWriter = BufferedWriter(FileWriter(TASK_ID_FILE))
        val array = Array(ids.size, { ids[it] })
        gson.toJson(arrayOf(*array, *currentIDs), fileWriter)
        fileWriter.close()
    }

    fun newID(): Long {
        var id = Math.abs(Random().nextLong())
        while (readAllIDs().contains(id)) {
            id = Math.abs(Random().nextLong())
        }
        saveID(id)
        return id
    }

    fun clearTaskIDFile() {
        // Cannot work if the file is completely empty (meaning no brackets even) but this should almost never be the case
        val fileWriter = BufferedWriter(FileWriter(TASK_ID_FILE))
        gson.toJson(listOf<Long>(), fileWriter)
        fileWriter.close()
    }

    fun readIDsFromTasks(): Array<Long> {
        val reader = BufferedReader(FileReader(TASKS_FILE))
        val allTasks = gson.fromJson<Array<Task>>(reader, Array<Task>::class.java)

        return Array<Long>(allTasks.size, { allTasks[it].taskID })

    }

    // Tasks

    // Newest First, Oldest Last
    fun readAllTasks(): ArrayList<Task> {
        val reader = BufferedReader(FileReader(TASKS_FILE))
        return gson.fromJson<ArrayList<Task>>(reader, ArrayList::class.java)
    }

    fun getTaskAt(index: Int): Task {
        return readAllTasks()[index]
    }

    fun getTask(task: Task): Task {
        val task0 = readAllTasks().find { it == task }
        if (task0 == null) {
            throw IllegalArgumentException("Cannot find")
        } else return task0
    }

    fun getTaskByID(id: Long): Task {
        val task = readAllTasks().find { it.taskID == id }
        if (task == null) {
            throw IllegalArgumentException("Cannot find")
        } else return task
    }

    fun getTasksByTitle(title: String): List<Task> {
        return readAllTasks().filter { it.title == title }
    }

    fun updateTaskAt(index: Int, task: Task) {
        val allTasks = readAllTasks()
        val taskID = allTasks[index].taskID
        allTasks[index] = Task(
                task.getTaskState(),
                task.isFailable,
                task.isKillable,
                taskID,
                task.time,
                task.duration,
                task.priority,
                task.label,
                task.optional,
                task.description,
                task.checkList,
                task.deadline,
                task.target,
                task.before,
                task.after,
                task.title)
        val fileWriter = BufferedWriter(FileWriter(TASKS_FILE))
        gson.toJson(arrayOf(*allTasks.toArray()), fileWriter)
        fileWriter.close()
    }

    fun updateTaskByID(id: Long, task: Task) {
        updateTaskAt(readAllTasks().indexOf(getTaskByID(id)), task)
    }

    fun updateTask(oldTask: Task, newTask: Task) {
        updateTaskAt(readAllTasks().indexOf(oldTask), newTask)
    }

    fun saveTaskAt(index: Int, task: Task) {
        val allTasksList = readAllTasks().toMutableList()
        allTasksList.add(index, task)
        val fileWriter = BufferedWriter(FileWriter(TASKS_FILE))
        gson.toJson(allTasksList.toTypedArray(), fileWriter)
        fileWriter.close()
    }

    fun deleteTask(task: Task) {
        val allTasksList = readAllTasks().toMutableList()
        allTasksList.removeAt(allTasksList.indexOf(task))
        val fileWriter = BufferedWriter(FileWriter(TASKS_FILE))
        gson.toJson(allTasksList.toTypedArray(), fileWriter)
        fileWriter.close()
    }

    fun deleteTaskAt(index: Int) {
        deleteTask(getTaskAt(index))
    }

    //TODO Trimming to size before re-adding back to JSON is a problem

    fun deleteAll(predicate: (Task) -> Boolean) {
        val allTasksList = readAllTasks().toMutableList()
        allTasksList.removeIf(predicate)
        val fileWriter = BufferedWriter(FileWriter(TASKS_FILE))
        gson.toJson(allTasksList.toTypedArray(), fileWriter)
        fileWriter.close()
    }

    // To the top of file
    fun saveTask(vararg tasks: Task) {
        val allTasks = readAllTasks()
        val fileWriter = BufferedWriter(FileWriter(TASKS_FILE))
        val array = Array(tasks.size, { tasks[it] })
        gson.toJson(arrayListOf(*array, *allTasks.toArray()), fileWriter)
        fileWriter.close()
    }

    fun clearTasksFile() {
        val fileWriter = BufferedWriter(FileWriter(TASKS_FILE))
        gson.toJson(listOf<Task>(), fileWriter)
        fileWriter.close()
    }


}

object Concurrent {

}


//extension functions go here for universal access