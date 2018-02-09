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

    }

    @DisplayName("Task Tests")
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

        @DisplayName("Task creation using chaining by Values")
        @Test
        fun testTaskCreationUsingChainingByValues() {
            val task = Task("My Task")
                    .setTimeValue(LocalDateTime.of(1970, 1, 1, 1, 1))
                    .setDurationValue(Duration.ofSeconds(15))
                    .setPriorityValue(Priority.createNewPriority("High"))
                    .setLabelValue(Label.createNewLabel("Label1"))
                    .setOptionalValue(true)
                    .setDescriptionValue(StringBuilder("Description"))
                    .setChecklistValue(CheckList("1", "2", "3"))
                    .setDeadlineValue(LocalDateTime.of(1970, 1, 1, 1, 1))
                    .setTargetValue("Target!")

            assertEquals(LocalDateTime.of(1970, 1, 1, 1, 1), task.time.value)
            assertTrue(task.time.isVisible)

            assertEquals(Duration.ofSeconds(15), task.duration.value)
            assertTrue(task.duration.isVisible)

            assertEquals(Priority.getPriorityByName("High"), task.priority.value)
            assertTrue(task.priority.isVisible)

            assertEquals(Label.getLabelByName("Label1"), task.label.value)
            assertTrue(task.label.isVisible)

            assertEquals(true, task.optional.value)
            assertTrue(task.optional.value)

            assertEquals(StringBuilder("Description").toString(), task.description.value.toString())
            assertTrue(task.description.isVisible)

            assertEquals(CheckList("1", "2", "3"), task.checkList.value)
            assertTrue(task.checkList.isVisible)

            assertEquals(LocalDateTime.of(1970, 1, 1, 1, 1), task.deadline.value)
            assertTrue(task.checkList.isVisible)

            assertEquals("Target!", task.target.value)
            assertTrue(task.target.isVisible)

            assertTrue(task.getAllShowingConstraints().isEmpty())
        }

        @DisplayName("Task creation using chaining by Properties")
        @Test
        fun testTaskCreationUsingChainingByProperties() {
            val task = Task("My Task")
                    .setTimeProperty(Property(SHOWING, LocalDateTime.of(1970, 1, 1, 1, 1)))
                    .setDurationProperty(Property(SHOWING, Duration.ofSeconds(15)))
                    .setPriorityProperty(Property(SHOWING, Priority.createNewPriority("High")))
                    .setLabelProperty(Property(SHOWING, Label.createNewLabel("Label1")))
                    .setOptionalProperty(Property(SHOWING, true))
                    .setDescriptionProperty(Property(SHOWING, StringBuilder("Description")))
                    .setChecklistProperty(Property(SHOWING, CheckList("1", "2", "3")))
                    .setDeadlineProperty(Property(SHOWING, LocalDateTime.of(1970, 1, 1, 1, 1)))
                    .setTargetProperty(Property(SHOWING, "Target!"))

            assertEquals(LocalDateTime.of(1970, 1, 1, 1, 1), task.time.value)
            assertTrue(task.time.isVisible)

            assertEquals(Duration.ofSeconds(15), task.duration.value)
            assertTrue(task.duration.isVisible)

            assertEquals(Priority.getPriorityByName("High"), task.priority.value)
            assertTrue(task.priority.isVisible)

            assertEquals(Label.getLabelByName("Label1"), task.label.value)
            assertTrue(task.label.isVisible)

            assertEquals(true, task.optional.value)
            assertTrue(task.optional.value)

            assertEquals(StringBuilder("Description").toString(), task.description.value.toString())
            assertTrue(task.description.isVisible)

            assertEquals(CheckList("1", "2", "3"), task.checkList.value)
            assertTrue(task.checkList.isVisible)

            assertEquals(LocalDateTime.of(1970, 1, 1, 1, 1), task.deadline.value)
            assertTrue(task.checkList.isVisible)

            assertEquals("Target!", task.target.value)
            assertTrue(task.target.isVisible)

            assertTrue(task.getAllShowingConstraints().isEmpty())
        }

        // Task Properties

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

            // Hide time and check that it resets to default values
            task.hideTime()
            assertFalse(task.time is Constraint)
            assertEquals(DEFAULT_TIME, task.time.value)
            assertFalse(task.time.isVisible)
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

            // Try to hide time when it is a Constraint
            assertThrows(IllegalStateException::class.java, { task.hideTime() })
            assertTrue(task.time is Constraint)
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

            // Hide duration and check that it resets to default values
            task.hideDuration()
            assertFalse(task.duration is Constraint)
            assertEquals(DEFAULT_DURATION, task.duration.value)
            assertFalse(task.duration.isVisible)

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

            // Try to hide duration when it is a Constraint
            assertThrows(IllegalStateException::class.java, { task.hideDuration() })
            assertTrue(task.duration is Constraint)
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

            // Hide priority and check that it resets to default values
            task.hidePriority()
            assertFalse(task.priority is Constraint)
            assertEquals(DEFAULT_PRIORITY, task.priority.value)
            assertFalse(task.priority.isVisible)
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

            // Try to hide priority when it is a Constraint
            assertThrows(IllegalStateException::class.java, { task.hidePriority() })
            assertTrue(task.priority is Constraint)
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

            // Hide label and check that it resets to default values
            task.hideLabel()
            assertFalse(task.label is Constraint)
            assertEquals(DEFAULT_LABEL, task.label.value)
            assertFalse(task.label.isVisible)
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

            // Try to label time when it is a Constraint
            assertThrows(IllegalStateException::class.java, { task.hideLabel() })
            assertTrue(task.label is Constraint)
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

            // Hide optional and check that it resets to default values
            task.hideOptional()
            assertFalse(task.optional is Constraint)
            assertEquals(DEFAULT_OPTIONAL, task.optional.value)
            assertFalse(task.optional.isVisible)
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

            // Try to hide optional when it is a Constraint
            assertThrows(IllegalStateException::class.java, { task.hideOptional() })
            assertTrue(task.optional is Constraint)
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

            // Hide description and check that it resets to default values
            task.hideDescription()
            assertFalse(task.description is Constraint)
            assertEquals(DEFAULT_DESCRIPTION, task.description.value)
            assertFalse(task.description.isVisible)
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

            // Try to hide description when it is a Constraint
            assertThrows(IllegalStateException::class.java, { task.hideDescription() })
            assertTrue(task.description is Constraint)
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

            // Hide checklist and check that it resets to default values
            task.hideChecklist()
            assertFalse(task.checkList is Constraint)
            assertEquals(DEFAULT_CHECKLIST, task.checkList.value)
            assertFalse(task.checkList.isVisible)
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

            // Try to hide checklist when it is a Constraint
            assertThrows(IllegalStateException::class.java, { task.hideChecklist() })
            assertTrue(task.checkList is Constraint)
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

            // Hide deadline and check that it resets to default values
            task.hideDeadline()
            assertFalse(task.deadline is Constraint)
            assertEquals(DEFAULT_DEADLINE, task.deadline.value)
            assertFalse(task.deadline.isVisible)
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

            // Try to hide deadline when it is a Constraint
            assertThrows(IllegalStateException::class.java, { task.hideDeadline() })
            assertTrue(task.deadline is Constraint)
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

            // Hide target and check that it resets to default values
            task.hideTarget()
            assertFalse(task.target is Constraint)
            assertEquals(DEFAULT_TARGET, task.target.value)
            assertFalse(task.target.isVisible)
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

            // Try to hide target when it is a Constraint
            assertThrows(IllegalStateException::class.java, { task.hideTarget() })
            assertTrue(task.target is Constraint)
        }

        // Task Lifecycle

        @DisplayName("Task kill with non met Constraints")
        @Test
        fun testTaskKillWithNonMetConstraints() {
            val task = Task("My Task")
            task.checkList = Property(SHOWING, CheckList("1", "2", "3"))
            task.checkList = task.checkList.toConstraint()
            task.duration = Property(SHOWING, Duration.ofHours(2))
            task.duration = task.duration.toConstraint()
            assertEquals(2, task.getAllUnmetAndShowingConstraints().size)
            assertTrue(task.isKillable)
            assertThrows(IllegalStateException::class.java, { task.kill() })

            (task.checkList as Constraint).isMet = MET
            (task.duration as Constraint).isMet = MET
            assertTrue(task.getAllUnmetAndShowingConstraints().isEmpty())
            task.kill()
            assertEquals(TaskState.KILLED, task.getTaskState())
            assertFalse(task.canKill())
        }
    }

    @DisplayName("Checklist")
    @Test
    fun testChecklist() {
        val checkList = CheckList("Zero", "One", "Two", "Three", "Four")
        assertEquals(5, checkList.size())

        checkList.addItem("Five")
        assertEquals(6, checkList.size())

        checkList.checkItem(5)
        assertTrue(checkList[5].isChecked)
        assertEquals(6, checkList.size())

        checkList.deleteItem(5)
        assertEquals(5, checkList.size())

        checkList.addItem(ListItem("FiveAgain", true))
        assertEquals(6, checkList.size())

        checkList.uncheckItem(5)
        assertFalse(checkList[5].isChecked)

        val checklist2 = CheckList("Zero","One", "Two", "Three")
        println(checklist2)
        checklist2[0] = "ZEROOOOOO!"
        println(checklist2)

    }

    @DisplayName("Checklist Tests")
    @Nested
    inner class ChecklistTests {

    }

}
