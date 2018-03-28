package uk.whitecrescent.waqti.tests.collections

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import uk.whitecrescent.waqti.collections.Tuple
import uk.whitecrescent.waqti.task.Task

@DisplayName("Tuple Tests")
class TupleTests {

    @DisplayName("Tuple creation zero Tasks")
    @Test
    fun testTupleCreationZeroTasks() {
        assertThrows(IllegalStateException::class.java, { val tuple = Tuple() })
    }

    @DisplayName("Tuple creation one Task")
    @Test
    fun testTupleCreationOneTask() {
        val tuple = Tuple(Task("Task1"))

    }

    @DisplayName("Tuple creation two Tasks")
    @Test
    fun testTupleCreationTwoTasks() {
        val tuple = Tuple(Task("Task1"), Task("Task2"))

    }

    @DisplayName("Tuple creation many Tasks")
    @Test
    fun testTupleCreationManyTasks() {
        val tuple = Tuple(Task("Task1"), Task("Task2"), Task("Task3"))

    }

}