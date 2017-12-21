package uk.whitecrescent.timemanagementsystem.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import uk.whitecrescent.timemanagementsystem.code.DEFAULT_PRIORITY
import uk.whitecrescent.timemanagementsystem.code.Priority
import uk.whitecrescent.timemanagementsystem.code.Task
import uk.whitecrescent.timemanagementsystem.code.TaskState
import java.time.LocalDateTime

open class KTestSuite {

    @DisplayName("Task Title")
    @Test
    fun taskTitle() {
        val task = Task("First Name")
        assertEquals("First Name", task.title)

        task.title = "Second Name"
        assertEquals("Second Name", task.title)
    }


    @DisplayName("Task Time")
    @Test
    fun taskTime() {
        val task = Task("Task")
        assertEquals(LocalDateTime.MIN, task.time.value)
    }

    @DisplayName("Test")
    @Test
    fun test() {
        assertEquals(DEFAULT_PRIORITY, Priority.createNewPriority("DEFAULT"))
        assertEquals(
                Priority.createNewPriority("My Priority"),
                Priority.createNewPriority("My Priority"))

        assertEquals(2, Priority.allPriorities.size)

        assertEquals(DEFAULT_PRIORITY, Priority.getPriorityByName("DEFAULT"))
        assertThrows(IllegalArgumentException::class.java, {Priority.getPriorityByName("Stuff")})

    }

    @DisplayName("Runner")
    @Test
    fun test0() {
        val task = Task("Task 1")

        assertEquals("Task 1", task.title)

        assertEquals(TaskState.UNBORN, task.state)

        println(task.state)

        task.changeStateToExisting()

        println(task.state)

        assertEquals(TaskState.EXISTING, task.state)
    }

}
