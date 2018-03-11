package uk.whitecrescent.waqti.tests.task

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import uk.whitecrescent.waqti.code.Constraint
import uk.whitecrescent.waqti.code.DEFAULT_DEADLINE
import uk.whitecrescent.waqti.code.HIDDEN
import uk.whitecrescent.waqti.code.Property
import uk.whitecrescent.waqti.code.SHOWING
import uk.whitecrescent.waqti.code.TaskState
import uk.whitecrescent.waqti.code.TaskStateException
import uk.whitecrescent.waqti.code.Time
import uk.whitecrescent.waqti.code.UNMET
import uk.whitecrescent.waqti.code.now
import uk.whitecrescent.waqti.code.sleep
import uk.whitecrescent.waqti.tests.TestUtils
import uk.whitecrescent.waqti.tests.TestUtils.testTask
import java.time.Duration

class DeadlineTests {

    @DisplayName("Deadline Default Values")
    @Test
    fun testTaskDeadlineDefaultValues() {
        val task = testTask()
        assertFalse(task.deadline is Constraint)
        assertEquals(DEFAULT_DEADLINE, task.deadline.value)
        assertFalse(task.deadline.isVisible)
    }

    @DisplayName("Set Deadline Property using setDeadlineProperty")
    @Test
    fun testTaskSetDeadlineProperty() {
        val task = testTask()
                .setDeadlineProperty(
                        Property(SHOWING, Time.of(1970, 1, 1, 1, 1))
                )

        assertFalse(task.deadline is Constraint)
        assertEquals(Time.of(1970, 1, 1, 1, 1), task.deadline.value)
        assertTrue(task.deadline.isVisible)


        task.hideDeadline()
        assertEquals(Property(HIDDEN, DEFAULT_DEADLINE), task.deadline)
    }

    @DisplayName("Set Deadline Property using setDeadlinePropertyValue")
    @Test
    fun testTaskSetDeadlinePropertyValue() {
        val task = testTask()
                .setDeadlinePropertyValue(
                        Time.of(1970, 1, 1, 1, 1)
                )

        assertFalse(task.deadline is Constraint)
        assertEquals(Time.of(1970, 1, 1, 1, 1), task.deadline.value)
        assertTrue(task.deadline.isVisible)

        task.hideDeadline()
        assertEquals(Property(HIDDEN, DEFAULT_DEADLINE), task.deadline)
    }

    @DisplayName("Set Deadline Constraint using setDeadlineProperty")
    @Test
    fun testTaskSetDeadlinePropertyWithConstraint() {
        val task = testTask()
                .setDeadlineProperty(
                        Constraint(SHOWING, Time.of(1970, 1, 1, 1, 1), UNMET)
                )

        assertTrue(task.deadline is Constraint)
        assertEquals(Time.of(1970, 1, 1, 1, 1), task.deadline.value)
        assertTrue(task.deadline.isVisible)
        assertFalse((task.deadline as Constraint).isMet)
    }

    @DisplayName("Set Deadline Constraint using setDeadlineConstraint")
    @Test
    fun testTaskSetDeadlineConstraint() {
        val task = testTask()
                .setDeadlineConstraint(
                        Constraint(SHOWING, Time.of(1970, 1, 1, 1, 1), UNMET)
                )

        assertTrue(task.deadline is Constraint)
        assertEquals(Time.of(1970, 1, 1, 1, 1), task.deadline.value)
        assertTrue(task.deadline.isVisible)
        assertFalse((task.deadline as Constraint).isMet)
    }

    @DisplayName("Set Deadline Constraint using setDeadlineConstraintValue")
    @Test
    fun testTaskSetDeadlineConstraintValue() {
        val task = testTask()
                .setDeadlineConstraintValue(Time.of(1970, 1, 1, 1, 1))

        assertTrue(task.deadline is Constraint)
        assertEquals(Time.of(1970, 1, 1, 1, 1), task.deadline.value)
        assertTrue(task.deadline.isVisible)
        assertFalse((task.deadline as Constraint).isMet)
    }

    @DisplayName("Set Deadline Property failable")
    @Test
    fun testTaskSetDurationPropertyFailable() {
        val deadline = now().plusSeconds(10)
        val task = testTask()
                .setDeadlinePropertyValue(deadline)

        assertFalse(task.isFailable)
        assertFalse(task.deadline is Constraint)
        assertEquals(deadline, task.deadline.value)
        assertTrue(task.deadline.isVisible)
    }

    @DisplayName("Set Deadline Constraint failable")
    @Test
    fun testTaskSetDeadlineConstraintFailable() {
        val deadline = now().plusSeconds(2)
        val task = testTask()
                .setDeadlineConstraint(Constraint(SHOWING, deadline, UNMET))


        assertTrue(task.isFailable)
        assertTrue(task.deadline is Constraint)
        assertEquals(deadline, task.deadline.value)
        assertTrue(task.deadline.isVisible)
        assertFalse((task.deadline as Constraint).isMet)
        assertThrows(TaskStateException::class.java, { task.kill() })

        sleep(3)

        task.kill()

        assertEquals(TaskState.KILLED, task.getTaskState())
        assertTrue((task.deadline as Constraint).isMet)
    }

    @DisplayName("Set Duration Constraint on many Tasks")
    @Test
    fun testTaskSetDurationConstraintOnManyTasks() {
        val duration = Duration.ofSeconds(3)
        val tasks = TestUtils.getTasks(100)
        tasks.forEach { it.setDurationConstraintValue(duration) }

        sleep(4)

        tasks.forEach { it.kill() }

    }

    @DisplayName("Get Duration Left")
    @Test
    fun testTaskGetDurationLeft() {
        val timeDue = now().plusSeconds(3)

        val task = testTask()
                .setDurationConstraintValue(Duration.ofSeconds(3))
        sleep(1)

        assertEquals(Duration.between(now(), timeDue).seconds, task.getDurationLeft().seconds)
    }

    @DisplayName("Get Duration Left Default")
    @Test
    fun testTaskGetDurationLeftDefault() {
        val task = testTask()
        Assertions.assertThrows(IllegalStateException::class.java, { task.getDurationLeft() })
    }

}