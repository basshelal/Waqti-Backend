package uk.whitecrescent.waqti.tests

import uk.whitecrescent.waqti.sleep
import uk.whitecrescent.waqti.task.Task
import java.time.Duration

object TestUtils {

    fun testTask() = Task("TestTask")

    fun getTasks(amount: Int): List<Task> {
        val list = ArrayList<Task>()
        for (i in 0 until amount) {
            list.add(Task("TestTask$i"))
        }
        return list.toList()
    }
}

inline fun after(duration: Duration, func: () -> Any) {
    sleep(duration.seconds.toInt())
    func.invoke()
}