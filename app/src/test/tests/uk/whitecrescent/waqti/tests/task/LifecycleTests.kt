package uk.whitecrescent.waqti.tests.task

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import uk.whitecrescent.waqti.code.TaskState
import uk.whitecrescent.waqti.code.Time
import uk.whitecrescent.waqti.code.now
import uk.whitecrescent.waqti.code.sleep
import uk.whitecrescent.waqti.tests.TestUtils.testTask

@DisplayName("Lifecycle Tests")
class LifecycleTests {

    // Natural Process

    @DisplayName("EXISTING to SLEEPING")
    @Test
    fun testTaskExistingToSleeping() {
        val task = testTask()

        assertEquals(TaskState.EXISTING, task.state)

        task.sleep()

        assertEquals(TaskState.SLEEPING, task.state)
    }

    @DisplayName("SLEEPING to EXISTING")
    @Test
    fun testTaskSleepingToExisting() {
        val task = testTask()
                .setTimeConstraintValue(Time.from(now().plusSeconds(3)))

        sleep(1)

        assertEquals(TaskState.SLEEPING, task.state)

        sleep(3)

        assertEquals(TaskState.EXISTING, task.state)

    }

    @DisplayName("EXISTING to FAILED")
    @Test
    fun testTaskExistingToFailed() {
        val task = testTask()

        assertEquals(TaskState.EXISTING, task.state)

        task.setDeadlineConstraintValue(now().plusSeconds(2))

        assertEquals(TaskState.EXISTING, task.state)

        sleep(4)

        assertEquals(TaskState.FAILED, task.state)
    }

    @DisplayName("FAILED to SLEEPING")
    @Test
    fun testTaskFailedToSleeping() {
        val task = testTask()

        assertEquals(TaskState.EXISTING, task.state)
        task.setDeadlineConstraintValue(now().plusSeconds(2))
        assertEquals(TaskState.EXISTING, task.state)

        sleep(4)

        assertEquals(TaskState.FAILED, task.state)

        task.sleep()

        assertEquals(TaskState.SLEEPING, task.state)
    }

    @DisplayName("EXISTING to KILLED")
    @Test
    fun testTaskExistingToKilled() {
        val task = testTask()

        assertEquals(TaskState.EXISTING, task.state)

        task.setDeadlineConstraintValue(now().plusDays(7))

        assertEquals(TaskState.EXISTING, task.state)

        task.kill()

        assertEquals(TaskState.KILLED, task.state)
    }

    // Un-natural Process

}