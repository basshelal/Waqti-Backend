package uk.whitecrescent.waqti.tests.task

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import uk.whitecrescent.waqti.code.Constraint
import uk.whitecrescent.waqti.code.DEFAULT_OPTIONAL
import uk.whitecrescent.waqti.code.OPTIONAL
import uk.whitecrescent.waqti.code.SHOWING
import uk.whitecrescent.waqti.code.TaskStateException
import uk.whitecrescent.waqti.code.UNMET
import uk.whitecrescent.waqti.tests.TestUtils.testTask
import java.time.Duration

class OptionalTests {

    @DisplayName("Optional Default Values")
    @Test
    fun testTaskOptionalDefaultValues() {
        val task = testTask()
        assertFalse(task.optional is Constraint)
        assertEquals(DEFAULT_OPTIONAL, task.optional.value)
        assertFalse(task.optional.isVisible)
    }

    @DisplayName("Optional failable")
    @Test
    fun testTaskOptionalSetAndCheckFailable() {
        val task = testTask()
                .setDurationConstraintValue(Duration.ofSeconds(60))

        assertTrue(task.isFailable)
        assertThrows(TaskStateException::class.java, { task.kill() })

        task.setOptionalConstraint(Constraint(SHOWING, OPTIONAL, UNMET))

        assertFalse(task.isFailable)
        assertThrows(TaskStateException::class.java, { task.fail() })


    }
}