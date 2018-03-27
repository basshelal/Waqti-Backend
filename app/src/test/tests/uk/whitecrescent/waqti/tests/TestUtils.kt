package uk.whitecrescent.waqti.tests

import uk.whitecrescent.waqti.task.Task

object TestUtils {

    fun testTask() = Task("TestTask")

    fun getTasks(amount: Int): ArrayList<Task> {
        val list = ArrayList<Task>()
        for (i in 0..amount - 1) {
            list.add(Task("TestTask$i"))
        }
        return list
    }
}