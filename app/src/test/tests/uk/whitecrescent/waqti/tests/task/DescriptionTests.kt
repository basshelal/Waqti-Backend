package uk.whitecrescent.waqti.tests.task

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import uk.whitecrescent.waqti.code.Constraint
import uk.whitecrescent.waqti.code.DEFAULT_DESCRIPTION
import uk.whitecrescent.waqti.code.HIDDEN
import uk.whitecrescent.waqti.code.Property
import uk.whitecrescent.waqti.code.SHOWING
import uk.whitecrescent.waqti.code.UNMET
import uk.whitecrescent.waqti.tests.TestUtils.testTask

@DisplayName("Description Tests")
class DescriptionTests {

    @DisplayName("Description Default Values")
    @Test
    fun testTaskDescriptionDefaultValues() {
        val task = testTask()
        assertFalse(task.description is Constraint)
        assertEquals(DEFAULT_DESCRIPTION, task.description.value)
        assertFalse(task.description.isVisible)
    }

    @DisplayName("Set Description Property using setDescriptionProperty")
    @Test
    fun testTaskSetDescriptionProperty() {
        val task = testTask()
                .setDescriptionProperty(Property(SHOWING, "Test Description"))

        assertFalse(task.description is Constraint)
        assertEquals("Test Description", task.description.value)
        assertTrue(task.description.isVisible)


        task.hideDescription()
        assertEquals(Property(HIDDEN, DEFAULT_DESCRIPTION), task.description)
    }

    @DisplayName("Set Description Property using setDescriptionValue")
    @Test
    fun testTaskSetDescriptionValue() {
        val task = testTask()
                .setDescriptionValue("Test Description")

        assertFalse(task.description is Constraint)
        assertEquals("Test Description", task.description.value)
        assertTrue(task.description.isVisible)

        task.hideDescription()
        assertEquals(Property(HIDDEN, DEFAULT_DESCRIPTION), task.description)
    }

    @DisplayName("Set Description Constraint")
    @Test
    fun testTaskSetDescriptionConstraint() {
        val task = testTask()
                .setDescriptionProperty(Constraint(SHOWING, "Test Description", UNMET))

        assertFalse(task.description is Constraint)
        assertTrue(task.getAllUnmetAndShowingConstraints().isEmpty())
        assertEquals("Test Description", task.description.value)
        assertTrue(task.description.isVisible)
        assertThrows(ClassCastException::class.java,
                { assertTrue((task.description as Constraint).isMet == true) })

    }
}