package uk.whitecrescent.waqti.tests.task

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import uk.whitecrescent.waqti.code.Constraint
import uk.whitecrescent.waqti.code.DEFAULT_DURATION
import uk.whitecrescent.waqti.code.DEFAULT_DURATION_PROPERTY
import uk.whitecrescent.waqti.code.HIDDEN
import uk.whitecrescent.waqti.code.Property
import uk.whitecrescent.waqti.code.SHOWING
import uk.whitecrescent.waqti.code.TaskState
import uk.whitecrescent.waqti.code.TaskStateException
import uk.whitecrescent.waqti.code.TimeUnit
import uk.whitecrescent.waqti.code.UNMET
import uk.whitecrescent.waqti.code.now
import uk.whitecrescent.waqti.code.sleep
import uk.whitecrescent.waqti.tests.TestUtils
import uk.whitecrescent.waqti.tests.TestUtils.testTask
import java.time.Duration

@DisplayName("Duration Tests")
class DurationTests {

    // Before All
    companion object {
        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            TimeUnit.getOrCreateTimeUnit("TestTimeUnit", Duration.ofMinutes(10))
        }
    }

    @DisplayName("TimeUnits")
    @Test
    fun testTimeUnit() {
        TimeUnit.getOrCreateTimeUnit("Pomodoro", Duration.ofMinutes(25))
        assertEquals(Duration.ofMinutes(50),
                TimeUnit.toJavaDuration(
                        TimeUnit.getTimeUnit("Pomodoro", Duration.ofMinutes(25)),
                        2))
        TimeUnit.getOrCreateTimeUnit("Half-Hour", Duration.ofMinutes(30))
        assertEquals(Duration.ofHours(2),
                TimeUnit.toJavaDuration(
                        TimeUnit.getTimeUnit("Half-Hour", Duration.ofMinutes(30)),
                        4))
    }

    @DisplayName("Duration Default Values")
    @Test
    fun testTaskDurationDefaultValues() {
        val task = testTask()
        assertFalse(task.duration is Constraint)
        assertEquals(DEFAULT_DURATION, task.duration.value)
        assertFalse(task.duration.isVisible)
    }

    @DisplayName("Set Duration Property using setDurationProperty")
    @Test
    fun testTaskSetDurationProperty() {
        val task = testTask()
                .setDurationProperty(
                        Property(SHOWING, Duration.ofDays(7))
                )

        assertFalse(task.duration is Constraint)
        assertEquals(Duration.ofDays(7), task.duration.value)
        assertTrue(task.duration.isVisible)


        task.hideDuration()
        assertEquals(Property(HIDDEN, DEFAULT_DURATION), task.duration)
    }

    @DisplayName("Set Duration Property using setDurationPropertyValue")
    @Test
    fun testTaskSetDurationPropertyValue() {
        val task = testTask()
                .setDurationPropertyValue(
                        Duration.ofDays(7)
                )

        assertFalse(task.duration is Constraint)
        assertEquals(Duration.ofDays(7), task.duration.value)
        assertTrue(task.duration.isVisible)

        task.hideDuration()
        assertEquals(Property(HIDDEN, DEFAULT_DURATION), task.duration)
    }

    @DisplayName("Set Duration Constraint using setDurationProperty")
    @Test
    fun testTaskSetDurationPropertyWithConstraint() {
        val task = testTask()
                .setDurationProperty(
                        Constraint(SHOWING, Duration.ofDays(7), UNMET)
                )

        assertTrue(task.duration is Constraint)
        assertEquals(Duration.ofDays(7), task.duration.value)
        assertTrue(task.duration.isVisible)
        assertFalse((task.duration as Constraint).isMet)
    }

    @DisplayName("Set Duration Constraint using setDurationConstraint")
    @Test
    fun testTaskSetDurationConstraint() {
        val task = testTask()
                .setDurationConstraint(
                        Constraint(SHOWING, Duration.ofDays(7), UNMET)
                )

        assertTrue(task.duration is Constraint)
        assertEquals(Duration.ofDays(7), task.duration.value)
        assertTrue(task.duration.isVisible)
        assertFalse((task.duration as Constraint).isMet)
    }

    @DisplayName("Set Duration Constraint using setDurationConstraintValue")
    @Test
    fun testTaskSetDurationConstraintValue() {
        val task = testTask()
                .setDurationConstraintValue(Duration.ofDays(7))

        assertTrue(task.duration is Constraint)
        assertEquals(Duration.ofDays(7), task.duration.value)
        assertTrue(task.duration.isVisible)
        assertFalse((task.duration as Constraint).isMet)
    }

    @DisplayName("Set Duration Property using setDurationProperty with TimeUnits")
    @Test
    fun testTaskSetDurationPropertyWithTimeUnits() {
        val task = testTask()
                .setDurationPropertyTimeUnits(
                        Property(SHOWING, TimeUnit.getTimeUnit("TestTimeUnit", Duration.ofMinutes(10))),
                        6
                )

        assertFalse(task.duration is Constraint)
        assertEquals(Duration.ofHours(1), task.duration.value)
        assertTrue(task.duration.isVisible)


        task.hideDuration()
        assertEquals(Property(HIDDEN, DEFAULT_DURATION), task.duration)
    }

    @DisplayName("Set Duration Property using setDurationValue with TimeUnits")
    @Test
    fun testTaskSetDurationValueWithTimeUnits() {
        val task = testTask()
                .setDurationPropertyTimeUnitsValue(
                        TimeUnit.getTimeUnit("TestTimeUnit", Duration.ofMinutes(10)),
                        6
                )

        assertFalse(task.duration is Constraint)
        assertEquals(Duration.ofHours(1), task.duration.value)
        assertTrue(task.duration.isVisible)

        task.hideDuration()
        assertEquals(Property(HIDDEN, DEFAULT_DURATION), task.duration)
    }

    @DisplayName("Set Duration Constraint using setDurationProperty with TimeUnits")
    @Test
    fun testTaskSetDurationPropertyWithConstraintWithTimeUnits() {
        val task = testTask()
                .setDurationPropertyTimeUnits(
                        Constraint(SHOWING, TimeUnit.getTimeUnit("TestTimeUnit", Duration.ofMinutes(10)), UNMET),
                        6
                )

        assertTrue(task.duration is Constraint)
        assertEquals(Duration.ofHours(1), task.duration.value)
        assertTrue(task.duration.isVisible)
        assertFalse((task.duration as Constraint).isMet)
    }

    @DisplayName("Set Duration Constraint using setDurationConstraint with TimeUnits")
    @Test
    fun testTaskSetDurationConstraintWithTimeUnits() {
        val task = testTask()
                .setDurationConstraintTimeUnits(
                        Constraint(SHOWING, TimeUnit.getTimeUnit("TestTimeUnit", Duration.ofMinutes(10)), UNMET),
                        6
                )

        assertTrue(task.duration is Constraint)
        assertEquals(Duration.ofHours(1), task.duration.value)
        assertTrue(task.duration.isVisible)
        assertFalse((task.duration as Constraint).isMet)
    }

    @DisplayName("Set Duration Constraint using setDurationConstraintValue with TimeUnits")
    @Test
    fun testTaskSetDurationConstraintValueWithTimeUnits() {
        val task = testTask()
                .setDurationConstraintTimeUnitsValue(TimeUnit.getTimeUnit("TestTimeUnit", Duration.ofMinutes(10)), 6)

        assertTrue(task.duration is Constraint)
        assertEquals(Duration.ofHours(1), task.duration.value)
        assertTrue(task.duration.isVisible)
        assertFalse((task.duration as Constraint).isMet)
    }

    @DisplayName("Set Duration Property failable")
    @Test
    fun testTaskSetDurationPropertyFailable() {
        val duration = Duration.ofSeconds(10)
        val task = testTask()
                .setDurationPropertyValue(duration)

        assertFalse(task.isFailable)
        assertFalse(task.duration is Constraint)
        assertEquals(duration, task.duration.value)
        assertTrue(task.duration.isVisible)
    }

    @DisplayName("Set Duration Constraint failable")
    @Test
    fun testTaskSetDurationConstraintFailable() {
        val duration = Duration.ofSeconds(2)
        val task = testTask()
                .setDurationConstraint(Constraint(SHOWING, duration, UNMET))


        assertTrue(task.isFailable)
        assertTrue(task.duration is Constraint)
        assertEquals(duration, task.duration.value)
        assertTrue(task.duration.isVisible)
        assertFalse((task.duration as Constraint).isMet)
        assertThrows(TaskStateException::class.java, { task.kill() })

        sleep(4)

        task.kill()

        assertEquals(TaskState.KILLED, task.state)
        assertTrue((task.duration as Constraint).isMet)
    }

    @DisplayName("Set Duration Constraint on many Tasks")
    @Test
    fun testTaskSetDurationConstraintOnManyTasks() {
        val duration = Duration.ofSeconds(2)
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
        assertThrows(IllegalStateException::class.java, { task.getDurationLeft() })
    }

    @DisplayName("Duration Un-constraining")
    @Test
    fun testTaskDurationUnConstraining() {
        val task = testTask()
                .setDurationConstraintValue(Duration.ofDays(7))

        sleep(1)
        assertThrows(TaskStateException::class.java, { task.kill() })
        assertTrue(task.getAllUnmetAndShowingConstraints().size == 1)
        task.setDurationProperty((task.duration as Constraint).toProperty())

        sleep(1)

        assertTrue(task.getAllUnmetAndShowingConstraints().isEmpty())
        task.kill()
        assertEquals(TaskState.KILLED, task.state)
    }

    @DisplayName("Duration Constraint Re-Set")
    @Test
    fun testTaskDurationConstraintReSet() {
        val task = testTask()
                .setDurationConstraintValue(Duration.ofDays(7))

        sleep(1)
        assertThrows(TaskStateException::class.java, { task.kill() })
        assertTrue(task.getAllUnmetAndShowingConstraints().size == 1)

        val newDuration = Duration.ofSeconds(2)

        task.setDurationConstraintValue(newDuration)
        assertEquals(newDuration, task.duration.value)

        sleep(4)

        task.kill()
        assertEquals(TaskState.KILLED, task.state)
    }

    @DisplayName("Duration Hiding")
    @Test
    fun testDurationHiding() {
        val duration = Duration.ofDays(7)

        val task = testTask()
                .setDurationPropertyValue(duration)
        assertEquals(duration, task.duration.value)

        task.hideDuration()
        assertEquals(DEFAULT_DURATION_PROPERTY, task.duration)

        task.setDurationConstraintValue(duration)
        assertEquals(duration, task.duration.value)
        assertThrows(IllegalStateException::class.java, { task.hideDuration() })
    }

}