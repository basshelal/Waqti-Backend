package uk.whitecrescent.waqti.tests.task

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import uk.whitecrescent.waqti.code.Constraint
import uk.whitecrescent.waqti.code.DEFAULT_TIME
import uk.whitecrescent.waqti.code.HIDDEN
import uk.whitecrescent.waqti.code.Property
import uk.whitecrescent.waqti.code.SHOWING
import uk.whitecrescent.waqti.code.TaskState
import uk.whitecrescent.waqti.code.TaskStateException
import uk.whitecrescent.waqti.code.Time
import uk.whitecrescent.waqti.code.UNMET
import uk.whitecrescent.waqti.code.now
import uk.whitecrescent.waqti.code.sleep
import uk.whitecrescent.waqti.tests.TestUtils.getTasks
import uk.whitecrescent.waqti.tests.TestUtils.testTask

@DisplayName("Time Tests")
class TimeTests {

    @DisplayName("Time Default Values")
    @Test
    fun testTaskTimeDefaultValues() {
        val task = testTask()
        assertFalse(task.time is Constraint)
        assertEquals(DEFAULT_TIME, task.time.value)
        assertFalse(task.time.isVisible)
    }

    @DisplayName("Set Time Property using setTimeProperty")
    @Test
    fun testTaskSetTimeProperty() {
        val task = testTask()
                .setTimeProperty(
                        Property(SHOWING, Time.of(1970, 1, 1, 1, 1))
                )

        assertFalse(task.time is Constraint)
        assertEquals(Time.of(1970, 1, 1, 1, 1), task.time.value)
        assertTrue(task.time.isVisible)


        task.hideTime()
        assertEquals(Property(HIDDEN, DEFAULT_TIME), task.time)
    }

    @DisplayName("Set Time Property using setTimeValue")
    @Test
    fun testTaskSetTimeValue() {
        val task = testTask()
                .setTimePropertyValue(
                        Time.of(1970, 1, 1, 1, 1)
                )

        assertFalse(task.time is Constraint)
        assertEquals(Time.of(1970, 1, 1, 1, 1), task.time.value)
        assertTrue(task.time.isVisible)

        task.hideTime()
        assertEquals(Property(HIDDEN, DEFAULT_TIME), task.time)
    }

    @DisplayName("Set Time Constraint using setTimeProperty")
    @Test
    fun testTaskSetTimePropertyWithConstraint() {
        val task = testTask()
                .setTimeProperty(
                        Constraint(SHOWING, Time.of(1970, 1, 1, 1, 1), UNMET)
                )

        assertTrue(task.time is Constraint)
        assertEquals(Time.of(1970, 1, 1, 1, 1), task.time.value)
        assertTrue(task.time.isVisible)
        assertFalse((task.time as Constraint).isMet)
    }

    @DisplayName("Set Time Constraint using setTimeConstraint")
    @Test
    fun testTaskSetTimeConstraint() {
        val task = testTask()
                .setTimeConstraint(
                        Constraint(SHOWING, Time.of(1970, 1, 1, 1, 1), UNMET)
                )

        assertTrue(task.time is Constraint)
        assertEquals(Time.of(1970, 1, 1, 1, 1), task.time.value)
        assertTrue(task.time.isVisible)
        assertFalse((task.time as Constraint).isMet)
    }

    @DisplayName("Set Time Constraint using setTimeConstraintValue")
    @Test
    fun testTaskSetTimeConstraintValue() {
        val task = testTask()
                .setTimeConstraintValue(Time.of(1970, 1, 1, 1, 1))

        assertTrue(task.time is Constraint)
        assertEquals(Time.of(1970, 1, 1, 1, 1), task.time.value)
        assertTrue(task.time.isVisible)
        assertFalse((task.time as Constraint).isMet)
    }

    @DisplayName("Set Time Property before now")
    @Test
    fun testTaskSetTimePropertyBeforeNow() {
        val time = now().minusSeconds(3)
        val task = testTask()
                .setTimePropertyValue(time)

        assertFalse(task.isFailable)
        assertFalse(task.time is Constraint)
        assertEquals(time, task.time.value)
        assertTrue(task.time.isVisible)
    }

    @DisplayName("Set Time Property after now")
    @Test
    fun testTaskSetTimePropertyAfterNow() {
        val time = now().plusSeconds(3)
        val task = testTask()
                .setTimePropertyValue(time)

        assertFalse(task.isFailable)
        assertFalse(task.time is Constraint)
        assertEquals(time, task.time.value)
        assertTrue(task.time.isVisible)
    }

    @DisplayName("Set Time Constraint after now")
    @Test
    fun testTaskSetTimeConstraintAfterNow() {
        val time = now().plusSeconds(2)
        val task = testTask()
                .setTimeConstraint(Constraint(SHOWING, time, UNMET))

        assertTrue(task.state == TaskState.SLEEPING)
        assertTrue(task.isFailable)
        assertTrue(task.time is Constraint)
        assertEquals(time, task.time.value)
        assertTrue(task.time.isVisible)
        assertFalse((task.time as Constraint).isMet)

        sleep(4)

        assertTrue(task.state == TaskState.EXISTING)
        assertTrue((task.time as Constraint).isMet)
    }

    @DisplayName("Set Time Constraint before now")
    @Test
    fun testTaskSetTimeConstraintBeforeNow() {
        val time = now().minusSeconds(2)
        val task = testTask()
                .setTimeConstraint(Constraint(SHOWING, time, UNMET))
        assertTrue(task.state != TaskState.SLEEPING)
        assertFalse(task.isFailable)
        assertTrue(task.time is Constraint)
        assertEquals(time, task.time.value)
        assertTrue(task.time.isVisible)
        assertFalse((task.time as Constraint).isMet)
        sleep(3)
        assertTrue(task.state == TaskState.EXISTING)
    }

    @DisplayName("Set Time Constraint after now on many Tasks")
    @Test
    fun testTaskSetTimeConstraintAfterNowOnManyTasks() {
        val time = now().plusSeconds(3)
        val tasks = getTasks(100)
        tasks.forEach { it.setTimeConstraintValue(time) }

        sleep(4)

        tasks.forEach { assertTrue(it.state == TaskState.EXISTING) }

    }

    @DisplayName("Time Un-constraining")
    @Test
    fun testTaskTimeUnConstraining() {
        val task = testTask()
                .setTimeConstraintValue(Time.from(now().plusDays(7)))
        sleep(2)
        assertEquals(TaskState.SLEEPING, task.state)
        assertThrows(TaskStateException::class.java, { task.kill() })
        assertTrue(task.getAllUnmetAndShowingConstraints().size == 1)
        task.setTimeProperty((task.time as Constraint).toProperty())

        sleep(2)

        assertTrue(task.getAllUnmetAndShowingConstraints().isEmpty())
        assertEquals(TaskState.EXISTING, task.state)
    }

    @DisplayName("Time Constraint Re-Set")
    @Test
    fun testTaskTimeConstraintReSet() {
        val task = testTask()
                .setTimeConstraintValue(Time.from(now().plusDays(7)))

        sleep(1)
        assertThrows(TaskStateException::class.java, { task.kill() })
        assertTrue(task.getAllUnmetAndShowingConstraints().size == 1)

        val newTime = now().plusSeconds(2)

        task.setTimeConstraintValue(newTime)
        assertEquals(newTime, task.time.value)

        sleep(3)

        assertEquals(TaskState.EXISTING, task.state)
    }

}