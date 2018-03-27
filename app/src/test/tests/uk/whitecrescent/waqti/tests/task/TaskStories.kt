package uk.whitecrescent.waqti.tests.task

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import uk.whitecrescent.waqti.code.Checklist
import uk.whitecrescent.waqti.code.Label
import uk.whitecrescent.waqti.code.MANDATORY
import uk.whitecrescent.waqti.code.Priority
import uk.whitecrescent.waqti.code.Task
import uk.whitecrescent.waqti.code.TaskStateException
import uk.whitecrescent.waqti.code.today

// TODO: 27-Mar-18 Finish this too
@DisplayName("Task Stories")
class TaskStories {

    @DisplayName("Story 1")
    @Test
    fun testStory1() {
        val task = Task("Meet Dr. Tam to discuss project")
                .setTimeConstraintValue(today().plusDays(1).atTime(12, 0))
                .setOptionalValue(MANDATORY)
                .setTargetConstraintValue("Find clear idea of how to structure system for Project")

    }

    @DisplayName("Story 2")
    @Test
    fun testStory2() {

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
        // TODO: 26-Mar-18 Test Time and Duration together, will they work together ?
    }
}