package uk.whitecrescent.waqti.test

import uk.whitecrescent.waqti.code.Task

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