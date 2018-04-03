package uk.whitecrescent.waqti.tests.task

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import uk.whitecrescent.waqti.now
import uk.whitecrescent.waqti.sleep
import uk.whitecrescent.waqti.task.Checklist
import uk.whitecrescent.waqti.task.Constraint
import uk.whitecrescent.waqti.task.Label
import uk.whitecrescent.waqti.task.MANDATORY
import uk.whitecrescent.waqti.task.MET
import uk.whitecrescent.waqti.task.Priority
import uk.whitecrescent.waqti.task.Task
import uk.whitecrescent.waqti.task.TaskState
import uk.whitecrescent.waqti.task.TaskStateException
import uk.whitecrescent.waqti.tasks
import uk.whitecrescent.waqti.today
import java.time.Duration

// TODO: 27-Mar-18 Finish this too
@Disabled
@DisplayName("Task Stories")
class TaskStories {

    @DisplayName("Story 1")
    @Test
    fun testStory1() {
        val task = Task("Meet Dr. Tam to discuss project")
                .setTimeConstraintValue(today().plusDays(1).atTime(12, 0))
                .setOptionalValue(MANDATORY)
                .setTargetConstraintValue("Find clear idea of how to structure system for Project")

        // simulate time has come
        task.setTimeConstraintValue(now().minusSeconds(30))

        sleep(2)

        assertEquals(TaskState.EXISTING, task.state)
        assertThrows(TaskStateException::class.java, { task.kill() })

        (task.target as Constraint).isMet = MET

        task.kill()

        assertEquals(TaskState.KILLED, task.state)
    }

    @DisplayName("Story 2")
    @Test
    fun testStory2() {
        val eatTask = Task("Have Breakfast")
                .setTimePropertyValue(today().atTime(9, 0))

        val meditateTask = Task("Meditate after food")
                .setBeforeConstraintValue(eatTask)
                .setTimeConstraintValue(today().atTime(10, 0))
                .setDurationConstraintValue(Duration.ofMinutes(10))
                .setOptionalValue(MANDATORY)

        // simulate eat is done and meditate time is here
        eatTask.kill()
        meditateTask.setTimeConstraintValue(now().minusMinutes(10))

        sleep(2)

        assertEquals(TaskState.EXISTING, meditateTask.state)
        assertThrows(TaskStateException::class.java, { meditateTask.kill() })

        // shorten duration for testing
        meditateTask.setDurationConstraintValue(Duration.ofSeconds(2))

        sleep(2)

        assertThrows(TaskStateException::class.java, { meditateTask.kill() })

        meditateTask.startTimer()

        sleep(4)

        meditateTask.kill()

        assertEquals(TaskState.KILLED, meditateTask.state)
    }

    @DisplayName("Story 3")
    @Test
    fun testStory3() {

        val task = Task("Finish Software Engineering Assignment 1")
                .setDeadlineConstraintValue(today().plusDays(7).atTime(16, 0))
                .addLabels(Label.getOrCreateLabel("University"))
                .setPriorityValue(Priority.getOrCreatePriority("High", 1))
                .addSubTasksConstraint(
                        Task("Write User Requirements")
                                .setDeadlineConstraintValue(today().plusDays(3).atTime(23, 55))
                                .setDescriptionValue("Gather user requirements from surveys and write up document")
                                .setChecklistConstraintValue(Checklist("Gather data", "Write Document")),
                        Task("Write code for requirements")
                                .setDeadlineConstraintValue(today().plusDays(5).atTime(23, 55)),
                        Task("Write effective Unit tests for code")
                                .setDeadlineConstraintValue(today().plusDays(6).atTime(23, 55)),
                        Task("Submit Assignment")
                                .setDeadlineConstraintValue(today().plusDays(7).atTime(12, 0))
                )

        assertThrows(TaskStateException::class.java, { task.kill() })

        sleep(1)

        task.subTasks.value.tasks()[0].checklist.value.checkItem(0)
        task.subTasks.value.tasks()[0].checklist.value.checkItem(1)

        sleep(1)

        task.subTasks.value.tasks()[0].kill()
        task.subTasks.value.tasks()[1].kill()
        task.subTasks.value.tasks()[2].kill()

        //simulate time has gone past deadline for last subTask
        task.subTasks.value.tasks()[3].setDeadlineConstraintValue(now().minusMinutes(30))

        sleep(2)

        assertEquals(TaskState.FAILED, task.subTasks.value.tasks()[3].state)
        assertEquals(TaskState.FAILED, task.state)

    }
}