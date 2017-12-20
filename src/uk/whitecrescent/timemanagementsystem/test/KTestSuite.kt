package uk.whitecrescent.timemanagementsystem.test

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import uk.whitecrescent.timemanagementsystem.code.Task
import uk.whitecrescent.timemanagementsystem.code.TaskState
import uk.whitecrescent.timemanagementsystem.code.TaskTime
import uk.whitecrescent.timemanagementsystem.code.TaskTitle
import java.time.LocalDateTime

open class KTestSuite {

    @Test
    fun testAddition() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testEmptyArray() {
        val array = Array(5, { String() })

        array.set(0, String())
        array.set(1, String())

        println(array.size)
        Assertions.assertNotNull(array.size)
        Assertions.assertThrows(ArrayIndexOutOfBoundsException::class.java, { array.set(5, String()) })
    }

    @Test
    fun testTask() {
        val task = Task()

        task.title = TaskTitle("Task 1", task)
        task.time = TaskTime(LocalDateTime.now(), task)

        assertEquals(LocalDateTime.now().second, task.time.time.second)
        assertEquals(LocalDateTime.now().minute, task.time.time.minute)
        assertEquals(LocalDateTime.now().hour, task.time.time.hour)
        assertEquals("Task 1", task.title.toString())

        assertEquals(TaskState.UNBORN, task.state)

        println(task.state)

        task.changeStateToExisting()

        println(task.state)

        assertEquals(TaskState.EXISTING, task.state)
    }

}
