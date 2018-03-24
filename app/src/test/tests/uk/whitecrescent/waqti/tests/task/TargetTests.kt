package uk.whitecrescent.waqti.tests.task

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import uk.whitecrescent.waqti.code.Constraint
import uk.whitecrescent.waqti.code.DEFAULT_TARGET
import uk.whitecrescent.waqti.code.HIDDEN
import uk.whitecrescent.waqti.code.Property
import uk.whitecrescent.waqti.code.SHOWING
import uk.whitecrescent.waqti.code.UNMET
import uk.whitecrescent.waqti.tests.TestUtils.testTask

@DisplayName("Target Tests")
class TargetTests {

    @DisplayName("Target Default Values")
    @Test
    fun testTaskTargetDefaultValues() {
        val task = testTask()
        assertFalse(task.target is Constraint)
        assertEquals(DEFAULT_TARGET, task.target.value)
        assertFalse(task.target.isVisible)
    }

    @DisplayName("Set Target Property using setTargetProperty")
    @Test
    fun testTaskSetTargetProperty() {
        val task = testTask()
                .setTargetProperty(
                        Property(SHOWING, "Test Target")
                )

        assertFalse(task.target is Constraint)
        assertEquals("Test Target", task.target.value)
        assertTrue(task.target.isVisible)


        task.hideTarget()
        assertEquals(Property(HIDDEN, DEFAULT_TARGET), task.target)
    }

    @DisplayName("Set Target Property using setTargetPropertyValue")
    @Test
    fun testTaskSetTargetPropertyValue() {
        val task = testTask()
                .setTargetPropertyValue(
                        "Test Target"
                )

        assertFalse(task.target is Constraint)
        assertEquals("Test Target", task.target.value)
        assertTrue(task.target.isVisible)

        task.hideTarget()
        assertEquals(Property(HIDDEN, DEFAULT_TARGET), task.target)
    }

    @DisplayName("Set Target Constraint using setTargetProperty")
    @Test
    fun testTaskSetTargetPropertyWithConstraint() {
        val task = testTask()
                .setTargetProperty(
                        Constraint(SHOWING, "Test Target", UNMET)
                )

        assertTrue(task.target is Constraint)
        assertEquals("Test Target", task.target.value)
        assertTrue(task.target.isVisible)
        assertFalse((task.target as Constraint).isMet)
    }

    @DisplayName("Set Target Constraint using setTargetConstraint")
    @Test
    fun testTaskSetTargetConstraint() {
        val task = testTask()
                .setTargetConstraint(
                        Constraint(SHOWING, "Test Target", UNMET)
                )

        assertTrue(task.target is Constraint)
        assertEquals("Test Target", task.target.value)
        assertTrue(task.target.isVisible)
        assertFalse((task.target as Constraint).isMet)
    }

    @DisplayName("Set Target Constraint using setTargetConstraintValue")
    @Test
    fun testTaskSetTargetConstraintValue() {
        val task = testTask()
                .setTargetConstraintValue("Test Target")

        assertTrue(task.target is Constraint)
        assertEquals("Test Target", task.target.value)
        assertTrue(task.target.isVisible)
        assertFalse((task.target as Constraint).isMet)
    }

    @DisplayName("Set Target Property failable")
    @Test
    fun testTaskSetTargetPropertyFailable() {
        val task = testTask()
                .setTargetPropertyValue("Test Target")

        assertFalse(task.isFailable)
        assertFalse(task.target is Constraint)
        assertEquals("Test Target", task.target.value)
        assertTrue(task.target.isVisible)
    }

    @DisplayName("Set Target Constraint failable")
    @Test
    fun testTaskSetTargetConstraintFailable() {
        val task = testTask()
                .setTargetConstraint(Constraint(SHOWING, "Test Target", UNMET))


        assertTrue(task.isFailable)
        assertTrue(task.target is Constraint)
        assertEquals("Test Target", task.target.value)
        assertTrue(task.target.isVisible)
        assertFalse((task.target as Constraint).isMet)
    }
}