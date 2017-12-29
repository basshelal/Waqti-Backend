package uk.whitecrescent.timemanagementsystem.test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import uk.whitecrescent.timemanagementsystem.code.*
import java.time.Duration
import java.time.LocalDateTime

class TestSuite {

    @DisplayName("Runner")
    @Test
    fun runner() {
        val tuple = Tuple(Task("Task1"), Task("Task2"), Task("Task3"))

        val tasks = tuple.tasks
        val task1 = tasks[0]
        val task2 = tasks[1]
        val task3 = tasks[2]

        assertNull(task1.before.value)

        assertEquals(task1, task2.before.value)
        assertEquals(task2, task1.after.value)

        assertEquals(task2, task3.before.value)
        assertEquals(task3, task2.after.value)

        assertNull(task3.after.value)

        println(tuple.tasks)
    }

    @DisplayName("Task")
    @Nested
    inner class TaskTests {

        @DisplayName("Task title")
        @Test
        fun testTaskTitle() {
            val task = Task("My Task")
            assertEquals("My Task", task.title)
            task.title = "My Task modified"
            assertEquals("My Task modified", task.title)
        }

        @DisplayName("Property and Constraint switching")
        @Test
        fun testPropertyAndConstraintSwitching() {
            val task = Task("My Task")
            assertFalse(task.time is Constraint)

            // Change time Property to Constraint and check
            task.time = task.time.toConstraint()
            assertTrue(task.time is Constraint)

            // Change time Constraint back to a Property
            task.time = (task.time as Constraint).toProperty()
            assertFalse(task.time is Constraint)
            assertThrows(ClassCastException::class.java, { (task.time as Constraint).isMet })
        }

        @DisplayName("Task time Property")
        @Test
        fun testTaskTimeProperty() {
            // Initial Task time Property values
            val task = Task("My Task")
            assertFalse(task.time is Constraint)
            assertEquals(DEFAULT_TIME, task.time.value)
            assertFalse(task.time.isVisible)

            // Change time Property and check it is visible and still not a Constraint
            task.time = Property(SHOWING, LocalDateTime.of(1970, 1, 1, 1, 1))
            assertFalse(task.time is Constraint)
            assertEquals(LocalDateTime.of(1970, 1, 1, 1, 1), task.time.value)
            assertTrue(task.time.isVisible)
        }

        @DisplayName("Task time Constraint")
        @Test
        fun testTaskTimeConstraint() {
            // Initial Task time Property values
            val task = Task("My Task")
            assertFalse(task.time is Constraint)
            assertEquals(DEFAULT_TIME, task.time.value)
            assertFalse(task.time.isVisible)

            // Change time Property to a Constraint and check it matches default Constraint values
            task.time = task.time.toConstraint()
            assertEquals(DEFAULT_TIME, task.time.value)
            assertFalse(task.time.isVisible)
            assertFalse((task.time as Constraint).isMet)

            // Change time Constraint to some values and check
            task.time = Constraint(SHOWING, LocalDateTime.of(1970, 1, 1, 1, 1), MET)
            assertEquals(LocalDateTime.of(1970, 1, 1, 1, 1), task.time.value)
            assertTrue(task.time.isVisible)
            assertTrue((task.time as Constraint).isMet)
        }

        @DisplayName("Task duration Property")
        @Test
        fun testTaskDurationProperty() {
            // Initial Task duration Property values
            val task = Task("My Task")
            assertFalse(task.duration is Constraint)
            assertEquals(DEFAULT_DURATION, task.duration.value)
            assertFalse(task.duration.isVisible)

            // Change duration Property and check it is visible and still not a Constraint
            task.duration = Property(SHOWING, Duration.ofSeconds(15))
            assertFalse(task.duration is Constraint)
            assertEquals(Duration.ofSeconds(15), task.duration.value)
            assertTrue(task.duration.isVisible)
        }

        @DisplayName("Task duration Constraint")
        @Test
        fun testTaskDurationConstraint() {
            // Initial Task duration Property values
            val task = Task("My Task")
            assertFalse(task.duration is Constraint)
            assertEquals(DEFAULT_DURATION, task.duration.value)
            assertFalse(task.duration.isVisible)

            // Change duration Property to a Constraint and check it matches default Constraint values
            task.duration = task.duration.toConstraint()
            assertEquals(DEFAULT_DURATION, task.duration.value)
            assertFalse(task.duration.isVisible)
            assertFalse((task.duration as Constraint).isMet)

            // Change duration Constraint to some values and check
            task.duration = Constraint(SHOWING, Duration.ofSeconds(15), MET)
            assertEquals(Duration.ofSeconds(15), task.duration.value)
            assertTrue(task.duration.isVisible)
            assertTrue((task.duration as Constraint).isMet)
        }

        @DisplayName("Task priority Property")
        @Test
        fun testTaskPriorityProperty() {
            // Initial Task priority Property values
            val task = Task("My Task")
            assertFalse(task.priority is Constraint)
            assertEquals(DEFAULT_PRIORITY, task.priority.value)
            assertFalse(task.priority.isVisible)

            // Change priority Property and check it is visible and still not a Constraint
            task.priority = Property(SHOWING, Priority.createNewPriority("High"))
            assertFalse(task.priority is Constraint)
            assertEquals(Priority.getPriorityByName("High"), task.priority.value)
            assertTrue(task.priority.isVisible)
        }

        @DisplayName("Task priority Constraint")
        @Test
        fun testTaskPriorityConstraint() {
            // Initial Task priority Property values
            val task = Task("My Task")
            assertFalse(task.priority is Constraint)
            assertEquals(DEFAULT_PRIORITY, task.priority.value)
            assertFalse(task.priority.isVisible)

            // Change priority Property to a Constraint and check it matches default Constraint values
            task.priority = task.priority.toConstraint()
            assertEquals(DEFAULT_PRIORITY, task.priority.value)
            assertFalse(task.priority.isVisible)
            assertFalse((task.priority as Constraint).isMet)

            // Change priority Constraint to some values and check
            task.priority = Constraint(SHOWING, Priority.createNewPriority("High"), MET)
            assertEquals(Priority.getPriorityByName("High"), task.priority.value)
            assertTrue(task.priority.isVisible)
            assertTrue((task.priority as Constraint).isMet)
        }

        @DisplayName("Task label Property")
        @Test
        fun testTaskLabelProperty() {
            // Initial Task label Property values
            val task = Task("My Task")
            assertFalse(task.label is Constraint)
            assertEquals(DEFAULT_LABEL, task.label.value)
            assertFalse(task.label.isVisible)

            // Change label Property and check it is visible and still not a Constraint
            task.label = Property(SHOWING, Label.createNewLabel("Label1"))
            assertFalse(task.label is Constraint)
            assertEquals(Label.getLabelByName("Label1"), task.label.value)
            assertTrue(task.label.isVisible)
        }

        @DisplayName("Task label Constraint")
        @Test
        fun testTaskLabelConstraint() {
            // Initial Task label Property values
            val task = Task("My Task")
            assertFalse(task.label is Constraint)
            assertEquals(DEFAULT_LABEL, task.label.value)
            assertFalse(task.label.isVisible)

            // Change label Property to a Constraint and check it matches default Constraint values
            task.label = task.label.toConstraint()
            assertEquals(DEFAULT_LABEL, task.label.value)
            assertFalse(task.label.isVisible)
            assertFalse((task.label as Constraint).isMet)

            // Change label Constraint to some values and check
            task.label = Constraint(SHOWING, Label.createNewLabel("Label1"), MET)
            assertEquals(Label.getLabelByName("Label1"), task.label.value)
            assertTrue(task.label.isVisible)
            assertTrue((task.label as Constraint).isMet)
        }

        @DisplayName("Task optional Property")
        @Test
        fun testTaskOptionalProperty() {
            // Initial Task optional Property values
            val task = Task("My Task")
            assertFalse(task.optional is Constraint)
            assertEquals(DEFAULT_OPTIONAL, task.optional.value)
            assertFalse(task.optional.isVisible)

            // Change optional Property and check it is visible and still not a Constraint
            task.optional = Property(SHOWING, true)
            assertFalse(task.optional is Constraint)
            assertEquals(true, task.optional.value)
            assertTrue(task.optional.isVisible)
        }

        @DisplayName("Task optional Constraint")
        @Test
        fun testTaskOptionalConstraint() {
            // Initial Task optional Property values
            val task = Task("My Task")
            assertFalse(task.optional is Constraint)
            assertEquals(DEFAULT_OPTIONAL, task.optional.value)
            assertFalse(task.optional.isVisible)

            // Change optional Property to a Constraint and check it matches default Constraint values
            task.optional = task.optional.toConstraint()
            assertEquals(DEFAULT_OPTIONAL, task.optional.value)
            assertFalse(task.optional.isVisible)
            assertFalse((task.optional as Constraint).isMet)

            // Change optional Constraint to some values and check
            task.optional = Constraint(SHOWING, true, MET)
            assertEquals(true, task.optional.value)
            assertTrue(task.optional.isVisible)
            assertTrue((task.optional as Constraint).isMet)
        }

        @DisplayName("Task description Property")
        @Test
        fun testTaskDescriptionProperty() {
            // Initial Task description Property values
            val task = Task("My Task")
            assertFalse(task.description is Constraint)
            assertEquals(DEFAULT_DESCRIPTION, task.description.value)
            assertFalse(task.description.isVisible)

            // Change description Property and check it is visible and still not a Constraint
            task.description = Property(SHOWING, StringBuilder("Description"))
            assertFalse(task.description is Constraint)
            assertEquals(StringBuilder("Description").toString(), task.description.value.toString())
            assertTrue(task.description.isVisible)
        }

        @DisplayName("Task description Constraint")
        @Test
        fun testTaskDescriptionConstraint() {
            // Initial Task description Property values
            val task = Task("My Task")
            assertFalse(task.description is Constraint)
            assertEquals(DEFAULT_DESCRIPTION, task.description.value)
            assertFalse(task.description.isVisible)

            // Change description Property to a Constraint and check it matches default Constraint values
            task.description = task.description.toConstraint()
            assertEquals(DEFAULT_DESCRIPTION, task.description.value)
            assertFalse(task.description.isVisible)
            assertFalse((task.description as Constraint).isMet)

            // Change description Constraint to some values and check
            task.description = Constraint(SHOWING, StringBuilder("Description"), MET)
            assertEquals(StringBuilder("Description").toString(), task.description.value.toString())
            assertTrue(task.description.isVisible)
            assertTrue((task.description as Constraint).isMet)
        }

        @DisplayName("Task checklist Property")
        @Test
        fun testTaskCheckListProperty() {
            // Initial Task checkList Property values
            val task = Task("My Task")
            assertFalse(task.checkList is Constraint)
            assertEquals(DEFAULT_CHECKLIST, task.checkList.value)
            assertFalse(task.checkList.isVisible)

            // Change checkList Property and check it is visible and still not a Constraint
            task.checkList = Property(SHOWING, CheckList("1", "2", "3"))
            assertFalse(task.checkList is Constraint)
            assertEquals(CheckList("1", "2", "3"), task.checkList.value)
            assertTrue(task.checkList.isVisible)
        }

        @DisplayName("Task checklist Constraint")
        @Test
        fun testTaskCheckListConstraint() {
            // Initial Task checkList Property values
            val task = Task("My Task")
            assertFalse(task.checkList is Constraint)
            assertEquals(DEFAULT_CHECKLIST, task.checkList.value)
            assertFalse(task.checkList.isVisible)

            // Change checkList Property to a Constraint and check it matches default Constraint values
            task.checkList = task.checkList.toConstraint()
            assertEquals(DEFAULT_CHECKLIST, task.checkList.value)
            assertFalse(task.checkList.isVisible)
            assertFalse((task.checkList as Constraint).isMet)

            // Change checkList Constraint to some values and check
            task.checkList = Constraint(SHOWING, CheckList("1", "2", "3"), MET)
            assertEquals(CheckList("1", "2", "3"), task.checkList.value)
            assertTrue(task.checkList.isVisible)
            assertTrue((task.checkList as Constraint).isMet)
        }

        @DisplayName("Task deadline Property")
        @Test
        fun testTaskDeadlineProperty() {
            // Initial Task deadline Property values
            val task = Task("My Task")
            assertFalse(task.deadline is Constraint)
            assertEquals(DEFAULT_DEADLINE, task.deadline.value)
            assertFalse(task.deadline.isVisible)

            // Change deadline Property and check it is visible and still not a Constraint
            task.deadline = Property(SHOWING, LocalDateTime.of(1970, 1, 1, 1, 1))
            assertFalse(task.deadline is Constraint)
            assertEquals(LocalDateTime.of(1970, 1, 1, 1, 1), task.deadline.value)
            assertTrue(task.deadline.isVisible)
        }

        @DisplayName("Task deadline Constraint")
        @Test
        fun testTaskDeadlineConstraint() {
            // Initial Task deadline Property values
            val task = Task("My Task")
            assertFalse(task.deadline is Constraint)
            assertEquals(DEFAULT_DEADLINE, task.deadline.value)
            assertFalse(task.deadline.isVisible)

            // Change deadline Property to a Constraint and check it matches default Constraint values
            task.deadline = task.deadline.toConstraint()
            assertEquals(DEFAULT_DEADLINE, task.deadline.value)
            assertFalse(task.deadline.isVisible)
            assertFalse((task.deadline as Constraint).isMet)

            // Change deadline Constraint to some values and check
            task.deadline = Constraint(SHOWING, LocalDateTime.of(1970, 1, 1, 1, 1), MET)
            assertEquals(LocalDateTime.of(1970, 1, 1, 1, 1), task.deadline.value)
            assertTrue(task.deadline.isVisible)
            assertTrue((task.deadline as Constraint).isMet)
        }

        @DisplayName("Task target Property")
        @Test
        fun testTaskTargetProperty() {
            // Initial Task target Property values
            val task = Task("My Task")
            assertFalse(task.target is Constraint)
            assertEquals(DEFAULT_TARGET, task.target.value)
            assertFalse(task.target.isVisible)

            // Change target Property and check it is visible and still not a Constraint
            task.target = Property(SHOWING, "Target!")
            assertFalse(task.target is Constraint)
            assertEquals("Target!", task.target.value)
            assertTrue(task.target.isVisible)
        }

        @DisplayName("Task target Constraint")
        @Test
        fun testTaskTargetConstraint() {
            // Initial Task target Property values
            val task = Task("My Task")
            assertFalse(task.target is Constraint)
            assertEquals(DEFAULT_TARGET, task.target.value)
            assertFalse(task.target.isVisible)

            // Change target Property to a Constraint and check it matches default Constraint values
            task.target = task.target.toConstraint()
            assertEquals(DEFAULT_TARGET, task.target.value)
            assertFalse(task.target.isVisible)
            assertFalse((task.target as Constraint).isMet)

            // Change target Constraint to some values and check
            task.target = Constraint(SHOWING, "Target!", MET)
            assertEquals("Target!", task.target.value)
            assertTrue(task.target.isVisible)
            assertTrue((task.target as Constraint).isMet)
        }

        @DisplayName("Task Before Constraint")
        @Test
        fun testTaskBeforeConstraint() {
            val task = Task("My Task")
            assertNull(task.before.value)

            val beforeTask = Task("Before Task")

            task.before = Constraint(SHOWING, beforeTask, UNMET)
            assertNotNull(task.before.value)
            assertEquals(beforeTask, task.before.value)
            assertTrue(task.before.isVisible)
            assertFalse(task.before.isMet)

        }

        @DisplayName("Task kill with non met Constraints")
        @Test
        fun testTaskKillWithNonMetConstraints() {
            val task = Task("My Task")
            task.checkList = Property(SHOWING, CheckList("1", "2", "3"))
            task.checkList = task.checkList.toConstraint()
            task.duration = Property(SHOWING, Duration.ofHours(2))
            task.duration = task.duration.toConstraint()
            assertEquals(2, task.getAllUnmetAndVisibleConstraints().size)
            assertTrue(task.isKillable)
            assertThrows(IllegalStateException::class.java, { task.kill() })

            (task.checkList as Constraint).isMet = MET
            (task.duration as Constraint).isMet = MET
            assertTrue(task.getAllUnmetAndVisibleConstraints().isEmpty())
            task.kill()
            assertEquals(TaskState.KILLED, task.getTaskState())
            assertFalse(task.canKill())
        }
    }

}
