package uk.whitecrescent.waqti.tests.task

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import uk.whitecrescent.waqti.code.Constraint
import uk.whitecrescent.waqti.code.DEFAULT_PRIORITY
import uk.whitecrescent.waqti.code.HIDDEN
import uk.whitecrescent.waqti.code.Priority
import uk.whitecrescent.waqti.code.Property
import uk.whitecrescent.waqti.code.SHOWING
import uk.whitecrescent.waqti.code.UNMET
import uk.whitecrescent.waqti.tests.TestUtils.testTask

class PriorityTests {

    //Before All
    companion object {
        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            Priority.getOrCreatePriority("TestPriority", 69)
        }
    }

    @DisplayName("Priority Default Values")
    @Test
    fun testTaskPriorityDefaultValues() {
        val task = testTask()
        assertFalse(task.priority is Constraint)
        assertEquals(DEFAULT_PRIORITY, task.priority.value)
        assertFalse(task.priority.isVisible)
    }

    @DisplayName("Set Priority Property using setPriorityProperty")
    @Test
    fun testTaskSetPriorityProperty() {
        val task = testTask()
                .setPriorityProperty(Property(SHOWING, Priority.getPriority("TestPriority", 69)))

        assertFalse(task.priority is Constraint)
        assertEquals(Priority.getPriority("TestPriority", 69), task.priority.value)
        assertTrue(task.priority.isVisible)


        task.hidePriority()
        assertEquals(Property(HIDDEN, DEFAULT_PRIORITY), task.priority)
    }

    @DisplayName("Set Priority Property using setPriorityValue")
    @Test
    fun testTaskSetPriorityValue() {
        val task = testTask()
                .setPriorityValue(Priority.getPriority("TestPriority", 69))

        assertFalse(task.priority is Constraint)
        assertEquals(Priority.getPriority("TestPriority", 69), task.priority.value)
        assertTrue(task.priority.isVisible)

        task.hidePriority()
        assertEquals(Property(HIDDEN, DEFAULT_PRIORITY), task.priority)
    }

    @DisplayName("Set Priority Constraint")
    @Test
    fun testTaskSetPriorityConstraint() {
        val task = testTask()
                .setPriorityProperty(Constraint(SHOWING, Priority.getPriority("TestPriority", 69), UNMET))

        assertFalse(task.priority is Constraint)
        assertTrue(task.getAllUnmetAndShowingConstraints().isEmpty())
        assertEquals(Priority.getPriority("TestPriority", 69), task.priority.value)
        assertTrue(task.priority.isVisible)
        assertThrows(ClassCastException::class.java,
                { assertTrue((task.priority as Constraint).isMet == true) })

    }
}