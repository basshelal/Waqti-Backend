package uk.whitecrescent.waqti.tests.task

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import uk.whitecrescent.waqti.code.Constraint
import uk.whitecrescent.waqti.code.DEFAULT_DURATION
import uk.whitecrescent.waqti.code.MET
import uk.whitecrescent.waqti.code.Property
import uk.whitecrescent.waqti.code.SHOWING
import uk.whitecrescent.waqti.code.Task
import java.time.Duration

@DisplayName("Duration Tests")
class DurationTests {

    @DisplayName("Task duration Property")
    @Test
    fun testTaskDurationProperty() {
        // Initial Task duration Property values
        val task = Task("My Task")
        Assertions.assertFalse(task.duration is Constraint)
        Assertions.assertEquals(DEFAULT_DURATION, task.duration.value)
        Assertions.assertFalse(task.duration.isVisible)

        // Change duration Property and check it is visible and still not a Constraint
        task.setDurationProperty(Property(SHOWING, Duration.ofSeconds(15)))
        Assertions.assertFalse(task.duration is Constraint)
        Assertions.assertEquals(Duration.ofSeconds(15), task.duration.value)
        Assertions.assertTrue(task.duration.isVisible)

        // Hide duration and check that it resets to default values
        task.hideDuration()
        Assertions.assertFalse(task.duration is Constraint)
        Assertions.assertEquals(DEFAULT_DURATION, task.duration.value)
        Assertions.assertFalse(task.duration.isVisible)

    }

    @DisplayName("Task duration Constraint")
    @Test
    fun testTaskDurationConstraint() {
        // Initial Task duration Property values
        val task = Task("My Task")
        Assertions.assertFalse(task.duration is Constraint)
        Assertions.assertEquals(DEFAULT_DURATION, task.duration.value)
        Assertions.assertFalse(task.duration.isVisible)

        // Change duration Property to a Constraint and check it matches default Constraint values
        task.setDurationConstraint(task.duration.toConstraint())
        Assertions.assertEquals(DEFAULT_DURATION, task.duration.value)
        Assertions.assertFalse(task.duration.isVisible)
        Assertions.assertFalse((task.duration as Constraint).isMet)

        // Change duration Constraint to some values and check
        task.setDurationConstraint(Constraint(SHOWING, Duration.ofSeconds(15), MET))
        Assertions.assertEquals(Duration.ofSeconds(15), task.duration.value)
        Assertions.assertTrue(task.duration.isVisible)
        Assertions.assertTrue((task.duration as Constraint).isMet)

        // Try to hide duration when it is a Constraint
        Assertions.assertThrows(IllegalStateException::class.java, { task.hideDuration() })
        Assertions.assertTrue(task.duration is Constraint)
    }

}