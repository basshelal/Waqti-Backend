package uk.whitecrescent.waqti.tests.collections

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import uk.whitecrescent.waqti.collections.Tuple
import uk.whitecrescent.waqti.task
import uk.whitecrescent.waqti.task.DEFAULT_BEFORE_PROPERTY
import uk.whitecrescent.waqti.task.Task
import uk.whitecrescent.waqti.tests.TestUtils.getTasks
import uk.whitecrescent.waqti.tests.TestUtils.testTask

@DisplayName("Tuple Tests")
class TupleTests {

    @DisplayName("Tuple creation zero Tasks")
    @Test
    fun testTupleCreationZeroTasks() {
        assertAll({ Tuple() })
        assertTrue(Tuple().isEmpty())
        assertEquals(0, Tuple().size)
    }

    @DisplayName("Tuple creation one Task")
    @Test
    fun testTupleCreationOneTask() {
        val tuple = Tuple(
                Task()
        )
        assertTrue(tuple.isNotEmpty())
        assertEquals(1, tuple.size)
    }

    @DisplayName("Tuple Add Single")
    @Test
    fun testTupleAddSingle() {
        val task = testTask()
        val tuple = Tuple()

        @Suppress("USELESS_IS_CHECK")
        assertTrue(tuple.add(task) is Tuple)

        assertEquals(1, tuple.size)
        assertEquals(task, tuple[0])
        assertFalse(tuple[0].before.isVisible)
    }

    @DisplayName("Tuple Add Again")
    @Test
    fun testTupleAddAgain() {
        val task0 = Task("Task1")
        val task1 = Task("Task2")
        val tuple = Tuple()

        tuple.add(task0).add(task1)

        assertEquals(2, tuple.size)
        assertEquals(task0, tuple[0])
        assertEquals(task1, tuple[1])
        assertFalse(tuple[0].before.isVisible)
        assertTrue(tuple[1].before.isVisible)
        assertEquals(tuple[0].taskID, tuple[1].before.value)
    }

    @DisplayName("Tuple Add At")
    @Test
    fun testTupleAddAt() {
        val task = Task("Task")
        val tuple = Tuple()

        tuple
                .add(Task("Task0"))
                .add(Task("Task1"))
                .add(Task("Task2"))

        assertEquals(3, tuple.size)
        assertFalse(tuple[0].before.isVisible)
        assertTrue(tuple[1].before.isVisible)
        assertTrue(tuple[2].before.isVisible)
        assertEquals(tuple[0].taskID, tuple[1].before.value)
        assertEquals(tuple[1].taskID, tuple[2].before.value)

        tuple.addAt(1, task)

        assertEquals(4, tuple.size)
        assertEquals("Task0", tuple[0].title)
        assertEquals("Task", tuple[1].title)
        assertEquals("Task1", tuple[2].title)
        assertEquals("Task2", tuple[3].title)

        assertFalse(tuple[0].before.isVisible)
        assertTrue(tuple[1].before.isVisible)
        assertTrue(tuple[2].before.isVisible)
        assertTrue(tuple[3].before.isVisible)


        assertEquals(tuple[0].taskID, tuple[1].before.value)
        assertEquals(tuple[1].taskID, tuple[2].before.value)
        assertEquals(tuple[2].taskID, tuple[3].before.value)

        assertEquals("Task0", tuple[1].before.value.task().title)
        assertEquals("Task", tuple[2].before.value.task().title)

        assertThrows(IndexOutOfBoundsException::class.java, { tuple.addAt(7, Task()) })
    }

    @DisplayName("Tuple Add All vararg")
    @Test
    fun testTupleAddAllVararg() {
        val tasks = getTasks(5).toTypedArray()
        val tuple = Tuple()

        @Suppress("USELESS_IS_CHECK")
        assertTrue(tuple.addAll(*tasks) is Tuple)

        assertEquals(5, tuple.size)

        assertEquals(DEFAULT_BEFORE_PROPERTY, tuple[0].before)
        assertEquals(tuple[0].taskID, tuple[1].before.value)
        assertEquals(tuple[1].taskID, tuple[2].before.value)
        assertEquals(tuple[2].taskID, tuple[3].before.value)
        assertEquals(tuple[3].taskID, tuple[4].before.value)
    }

    @DisplayName("Tuple Add All collection")
    @Test
    fun testTupleAddAllCollection() {
        val tasks = getTasks(5)
        val tuple = Tuple()

        tuple.addAll(tasks)
        assertEquals(5, tuple.size)

        assertEquals(DEFAULT_BEFORE_PROPERTY, tuple[0].before)
        assertEquals(tuple[0].taskID, tuple[1].before.value)
        assertEquals(tuple[1].taskID, tuple[2].before.value)
        assertEquals(tuple[2].taskID, tuple[3].before.value)
        assertEquals(tuple[3].taskID, tuple[4].before.value)

        tuple.addAll(getTasks(3))

        assertEquals(tuple[4].taskID, tuple[5].before.value)
        assertEquals(tuple[5].taskID, tuple[6].before.value)
        assertEquals(tuple[6].taskID, tuple[7].before.value)
    }

    @DisplayName("Tuple Add All At Vararg")
    @Test
    fun testTupleAddAllAtVararg() {
        val tuple = Tuple().addAll(getTasks(3))

        assertEquals(3, tuple.size)

        assertEquals(DEFAULT_BEFORE_PROPERTY, tuple[0].before)
        assertEquals(tuple[0].taskID, tuple[1].before.value)
        assertEquals(tuple[1].taskID, tuple[2].before.value)

        @Suppress("USELESS_IS_CHECK")
        assertTrue(tuple.addAllAt(1, *getTasks(2).toTypedArray()) is Tuple)

        assertEquals(DEFAULT_BEFORE_PROPERTY, tuple[0].before)
        assertEquals(tuple[0].taskID, tuple[1].before.value)
        assertEquals(tuple[1].taskID, tuple[2].before.value)
        assertEquals(tuple[2].taskID, tuple[3].before.value)
        assertEquals(tuple[3].taskID, tuple[4].before.value)
    }

    @DisplayName("Tuple Add All At collection")
    @Test
    fun testTupleAddAllAtCollection() {
        val tuple = Tuple().addAll(getTasks(3))

        assertEquals(3, tuple.size)

        assertEquals(DEFAULT_BEFORE_PROPERTY, tuple[0].before)
        assertEquals(tuple[0].taskID, tuple[1].before.value)
        assertEquals(tuple[1].taskID, tuple[2].before.value)

        tuple.addAllAt(1, getTasks(2))

        assertEquals(DEFAULT_BEFORE_PROPERTY, tuple[0].before)
        assertEquals(tuple[0].taskID, tuple[1].before.value)
        assertEquals(tuple[1].taskID, tuple[2].before.value)
        assertEquals(tuple[2].taskID, tuple[3].before.value)
        assertEquals(tuple[3].taskID, tuple[4].before.value)

        assertThrows(IndexOutOfBoundsException::class.java, { tuple.addAllAt(7, getTasks(5)) })
    }

    @DisplayName("Tuple Add If")
    @Test
    fun testTupleAddIf() {
        val tuple = Tuple().addAll(getTasks(3))

        tuple.addIf(
                listOf(
                        Task("My Task"),
                        Task(),
                        Task("Another Task")
                ),
                { it.title.isNotBlank() }
        )

        assertEquals(5, tuple.size)
        assertEquals(DEFAULT_BEFORE_PROPERTY, tuple[0].before)
        assertEquals(tuple[0].taskID, tuple[1].before.value)
        assertEquals(tuple[1].taskID, tuple[2].before.value)
        assertEquals(tuple[2].taskID, tuple[3].before.value)
        assertEquals(tuple[3].taskID, tuple[4].before.value)

        tuple.forEach { assertTrue(it.title.isNotBlank()) }
    }

}