package uk.whitecrescent.waqti.tests

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import uk.whitecrescent.waqti.code.Checklist
import uk.whitecrescent.waqti.code.Constraint
import uk.whitecrescent.waqti.code.DEFAULT_CHECKLIST
import uk.whitecrescent.waqti.code.DEFAULT_DEADLINE
import uk.whitecrescent.waqti.code.DEFAULT_DESCRIPTION
import uk.whitecrescent.waqti.code.DEFAULT_LABEL
import uk.whitecrescent.waqti.code.DEFAULT_OPTIONAL
import uk.whitecrescent.waqti.code.DEFAULT_TARGET
import uk.whitecrescent.waqti.code.Label
import uk.whitecrescent.waqti.code.ListItem
import uk.whitecrescent.waqti.code.MET
import uk.whitecrescent.waqti.code.Priority
import uk.whitecrescent.waqti.code.Property
import uk.whitecrescent.waqti.code.SHOWING
import uk.whitecrescent.waqti.code.Task
import uk.whitecrescent.waqti.code.TaskState
import uk.whitecrescent.waqti.code.TaskStateException
import java.time.Duration
import java.time.LocalDateTime

@DisplayName("Task Tests")
class TaskTests {


    @DisplayName("Test")
    @Test
    fun test() {

    }

    //region Task Properties and Constraints

    //region Label

    @DisplayName("Task label Property")
    @Test
    fun testTaskLabelProperty() {
        // Initial Task label Property values
        val task = Task("My Task")
        Assertions.assertFalse(task.label is Constraint)
        Assertions.assertEquals(DEFAULT_LABEL, task.label.value)
        Assertions.assertFalse(task.label.isVisible)

        // Change label Property and check it is visible and still not a Constraint
        task.setLabelProperty(Property(SHOWING, Label.createNewLabel("Label1")))
        Assertions.assertFalse(task.label is Constraint)
        Assertions.assertEquals(Label.getLabelByName("Label1"), task.label.value)
        Assertions.assertTrue(task.label.isVisible)

        // Hide label and check that it resets to default values
        task.hideLabel()
        Assertions.assertFalse(task.label is Constraint)
        Assertions.assertEquals(DEFAULT_LABEL, task.label.value)
        Assertions.assertFalse(task.label.isVisible)
    }

    @DisplayName("Task label Constraint")
    @Test
    fun testTaskLabelConstraint() {
        // Initial Task label Property values
        val task = Task("My Task")
        Assertions.assertFalse(task.label is Constraint)
        Assertions.assertEquals(DEFAULT_LABEL, task.label.value)
        Assertions.assertFalse(task.label.isVisible)

        // Change label Property to a Constraint and check it matches default Constraint values
        task.setLabelConstraint(task.label.toConstraint())
        Assertions.assertEquals(DEFAULT_LABEL, task.label.value)
        Assertions.assertFalse(task.label.isVisible)
        Assertions.assertFalse((task.label as Constraint).isMet)

        // Change label Constraint to some values and check
        task.setLabelConstraint(Constraint(SHOWING, Label.createNewLabel("Label1"), MET))
        Assertions.assertEquals(Label.getLabelByName("Label1"), task.label.value)
        Assertions.assertTrue(task.label.isVisible)
        Assertions.assertTrue((task.label as Constraint).isMet)

        // Try to label time when it is a Constraint
        Assertions.assertThrows(IllegalStateException::class.java, { task.hideLabel() })
        Assertions.assertTrue(task.label is Constraint)
    }

    //endregion

    //region Optional

    @DisplayName("Task optional Property")
    @Test
    fun testTaskOptionalProperty() {
        // Initial Task optional Property values
        val task = Task("My Task")
        Assertions.assertFalse(task.optional is Constraint)
        Assertions.assertEquals(DEFAULT_OPTIONAL, task.optional.value)
        Assertions.assertFalse(task.optional.isVisible)

        // Change optional Property and check it is visible and still not a Constraint
        task.setOptionalProperty(Property(SHOWING, true))
        Assertions.assertFalse(task.optional is Constraint)
        Assertions.assertEquals(true, task.optional.value)
        Assertions.assertTrue(task.optional.isVisible)

        // Hide optional and check that it resets to default values
        task.hideOptional()
        Assertions.assertFalse(task.optional is Constraint)
        Assertions.assertEquals(DEFAULT_OPTIONAL, task.optional.value)
        Assertions.assertFalse(task.optional.isVisible)
    }

    @DisplayName("Task optional Constraint")
    @Test
    fun testTaskOptionalConstraint() {
        // Initial Task optional Property values
        val task = Task("My Task")
        Assertions.assertFalse(task.optional is Constraint)
        Assertions.assertEquals(DEFAULT_OPTIONAL, task.optional.value)
        Assertions.assertFalse(task.optional.isVisible)

        // Change optional Property to a Constraint and check it matches default Constraint values
        task.setOptionalConstraint(task.optional.toConstraint())
        Assertions.assertEquals(DEFAULT_OPTIONAL, task.optional.value)
        Assertions.assertFalse(task.optional.isVisible)
        Assertions.assertFalse((task.optional as Constraint).isMet)

        // Change optional Constraint to some values and check
        task.setOptionalConstraint(Constraint(SHOWING, true, MET))
        Assertions.assertEquals(true, task.optional.value)
        Assertions.assertTrue(task.optional.isVisible)
        Assertions.assertTrue((task.optional as Constraint).isMet)

        // Try to hide optional when it is a Constraint
        Assertions.assertThrows(IllegalStateException::class.java, { task.hideOptional() })
        Assertions.assertTrue(task.optional is Constraint)
    }

    //endregion

    //region Description

    @DisplayName("Task description Property")
    @Test
    fun testTaskDescriptionProperty() {
        // Initial Task description Property values
        val task = Task("My Task")
        Assertions.assertFalse(task.description is Constraint)
        Assertions.assertEquals(DEFAULT_DESCRIPTION, task.description.value)
        Assertions.assertFalse(task.description.isVisible)

        // Change description Property and check it is visible and still not a Constraint
        task.setDescriptionProperty(Property(SHOWING, StringBuilder("Description")))
        Assertions.assertFalse(task.description is Constraint)
        Assertions.assertEquals(StringBuilder("Description").toString(), task.description.value.toString())
        Assertions.assertTrue(task.description.isVisible)

        // Hide description and check that it resets to default values
        task.hideDescription()
        Assertions.assertFalse(task.description is Constraint)
        Assertions.assertEquals(DEFAULT_DESCRIPTION, task.description.value)
        Assertions.assertFalse(task.description.isVisible)
    }

    @DisplayName("Task description Constraint")
    @Test
    fun testTaskDescriptionConstraint() {
        // Initial Task description Property values
        val task = Task("My Task")
        Assertions.assertFalse(task.description is Constraint)
        Assertions.assertEquals(DEFAULT_DESCRIPTION, task.description.value)
        Assertions.assertFalse(task.description.isVisible)

        // Change description Property to a Constraint and check it matches default Constraint values
        task.setDescriptionConstraint(task.description.toConstraint())
        Assertions.assertEquals(DEFAULT_DESCRIPTION, task.description.value)
        Assertions.assertFalse(task.description.isVisible)
        Assertions.assertFalse((task.description as Constraint).isMet)

        // Change description Constraint to some values and check
        task.setDescriptionConstraint(Constraint(SHOWING, StringBuilder("Description"), MET))
        Assertions.assertEquals(StringBuilder("Description").toString(), task.description.value.toString())
        Assertions.assertTrue(task.description.isVisible)
        Assertions.assertTrue((task.description as Constraint).isMet)

        // Try to hide description when it is a Constraint
        Assertions.assertThrows(IllegalStateException::class.java, { task.hideDescription() })
        Assertions.assertTrue(task.description is Constraint)
    }

    //endregion

    //region Checklist

    @DisplayName("Task checklist Property")
    @Test
    fun testTaskCheckListProperty() {
        // Initial Task checklist Property values
        val task = Task("My Task")
        Assertions.assertFalse(task.checklist is Constraint)
        Assertions.assertEquals(DEFAULT_CHECKLIST, task.checklist.value)
        Assertions.assertFalse(task.checklist.isVisible)

        // Change checklist Property and check it is visible and still not a Constraint
        task.setChecklistProperty(Property(SHOWING, Checklist("1", "2", "3")))
        Assertions.assertFalse(task.checklist is Constraint)
        Assertions.assertEquals(Checklist("1", "2", "3"), task.checklist.value)
        Assertions.assertTrue(task.checklist.isVisible)

        // Hide checklist and check that it resets to default values
        task.hideChecklist()
        Assertions.assertFalse(task.checklist is Constraint)
        Assertions.assertEquals(DEFAULT_CHECKLIST, task.checklist.value)
        Assertions.assertFalse(task.checklist.isVisible)
    }

    @DisplayName("Task checklist Constraint")
    @Test
    fun testTaskCheckListConstraint() {
        // Initial Task checklist Property values
        val task = Task("My Task")
        Assertions.assertFalse(task.checklist is Constraint)
        Assertions.assertEquals(DEFAULT_CHECKLIST, task.checklist.value)
        Assertions.assertFalse(task.checklist.isVisible)

        // Change checklist Property to a Constraint and check it matches default Constraint values
        task.setChecklistConstraint(task.checklist.toConstraint())
        Assertions.assertEquals(DEFAULT_CHECKLIST, task.checklist.value)
        Assertions.assertFalse(task.checklist.isVisible)
        Assertions.assertFalse((task.checklist as Constraint).isMet)

        // Change checklist Constraint to some values and check
        task.setChecklistConstraint(Constraint(SHOWING, Checklist("1", "2", "3"), MET))
        Assertions.assertEquals(Checklist("1", "2", "3"), task.checklist.value)
        Assertions.assertTrue(task.checklist.isVisible)
        Assertions.assertTrue((task.checklist as Constraint).isMet)

        // Try to hide checklist when it is a Constraint
        Assertions.assertThrows(IllegalStateException::class.java, { task.hideChecklist() })
        Assertions.assertTrue(task.checklist is Constraint)
    }

    //endregion

    //region Deadline

    @DisplayName("Task deadline Property")
    @Test
    fun testTaskDeadlineProperty() {
        // Initial Task deadline Property values
        val task = Task("My Task")
        Assertions.assertFalse(task.deadline is Constraint)
        Assertions.assertEquals(DEFAULT_DEADLINE, task.deadline.value)
        Assertions.assertFalse(task.deadline.isVisible)

        // Change deadline Property and check it is visible and still not a Constraint
        task.setDeadlineProperty(Property(SHOWING, LocalDateTime.of(1970, 1, 1, 1, 1)))
        Assertions.assertFalse(task.deadline is Constraint)
        Assertions.assertEquals(LocalDateTime.of(1970, 1, 1, 1, 1), task.deadline.value)
        Assertions.assertTrue(task.deadline.isVisible)

        // Hide deadline and check that it resets to default values
        task.hideDeadline()
        Assertions.assertFalse(task.deadline is Constraint)
        Assertions.assertEquals(DEFAULT_DEADLINE, task.deadline.value)
        Assertions.assertFalse(task.deadline.isVisible)
    }

    @DisplayName("Task deadline Constraint")
    @Test
    fun testTaskDeadlineConstraint() {
        // Initial Task deadline Property values
        val task = Task("My Task")
        Assertions.assertFalse(task.deadline is Constraint)
        Assertions.assertEquals(DEFAULT_DEADLINE, task.deadline.value)
        Assertions.assertFalse(task.deadline.isVisible)

        // Change deadline Property to a Constraint and check it matches default Constraint values
        task.setDeadlineConstraint(task.deadline.toConstraint())
        Assertions.assertEquals(DEFAULT_DEADLINE, task.deadline.value)
        Assertions.assertFalse(task.deadline.isVisible)
        Assertions.assertFalse((task.deadline as Constraint).isMet)

        // Change deadline Constraint to some values and check
        task.setDeadlineConstraint(Constraint(SHOWING, LocalDateTime.of(1970, 1, 1, 1, 1), MET))
        Assertions.assertEquals(LocalDateTime.of(1970, 1, 1, 1, 1), task.deadline.value)
        Assertions.assertTrue(task.deadline.isVisible)
        Assertions.assertTrue((task.deadline as Constraint).isMet)

        // Try to hide deadline when it is a Constraint
        Assertions.assertThrows(IllegalStateException::class.java, { task.hideDeadline() })
        Assertions.assertTrue(task.deadline is Constraint)
    }

    //endregion

    //region Target

    @DisplayName("Task target Property")
    @Test
    fun testTaskTargetProperty() {
        // Initial Task target Property values
        val task = Task("My Task")
        Assertions.assertFalse(task.target is Constraint)
        Assertions.assertEquals(DEFAULT_TARGET, task.target.value)
        Assertions.assertFalse(task.target.isVisible)

        // Change target Property and check it is visible and still not a Constraint
        task.setTargetProperty(Property(SHOWING, "Target!"))
        Assertions.assertFalse(task.target is Constraint)
        Assertions.assertEquals("Target!", task.target.value)
        Assertions.assertTrue(task.target.isVisible)

        // Hide target and check that it resets to default values
        task.hideTarget()
        Assertions.assertFalse(task.target is Constraint)
        Assertions.assertEquals(DEFAULT_TARGET, task.target.value)
        Assertions.assertFalse(task.target.isVisible)
    }

    @DisplayName("Task target Constraint")
    @Test
    fun testTaskTargetConstraint() {
        // Initial Task target Property values
        val task = Task("My Task")
        Assertions.assertFalse(task.target is Constraint)
        Assertions.assertEquals(DEFAULT_TARGET, task.target.value)
        Assertions.assertFalse(task.target.isVisible)

        // Change target Property to a Constraint and check it matches default Constraint values
        task.setTargetConstraint(task.target.toConstraint())
        Assertions.assertEquals(DEFAULT_TARGET, task.target.value)
        Assertions.assertFalse(task.target.isVisible)
        Assertions.assertFalse((task.target as Constraint).isMet)

        // Change target Constraint to some values and check
        task.setTargetConstraint(Constraint(SHOWING, "Target!", MET))
        Assertions.assertEquals("Target!", task.target.value)
        Assertions.assertTrue(task.target.isVisible)
        Assertions.assertTrue((task.target as Constraint).isMet)

        // Try to hide target when it is a Constraint
        Assertions.assertThrows(IllegalStateException::class.java, { task.hideTarget() })
        Assertions.assertTrue(task.target is Constraint)
    }

    //endregion

    //endregion

    //region Task Lifecycle

    @DisplayName("Task State EXISTING to KILLED")
    @Test
    fun testTaskStateExisting() {
        val task = Task("My Task")
        Assertions.assertEquals(TaskState.EXISTING, task.getTaskState())
        task.kill()
        Assertions.assertEquals(TaskState.KILLED, task.getTaskState())
        Assertions.assertThrows(TaskStateException::class.java, { task.kill() })

        task.setDeadlineProperty(Property(SHOWING, LocalDateTime.now()))
        task.setDeadlineConstraint(task.deadline.toConstraint())
        Assertions.assertEquals(TaskState.KILLED, task.getTaskState())
        Assertions.assertEquals(1, task.getAllUnmetAndShowingConstraints().size)
        Assertions.assertFalse(task.canKill())
    }

    @DisplayName("Task kill with non met Constraints")
    @Test
    fun testTaskKillWithNonMetConstraints() {
        val task = Task("My Task")
        task.setChecklistProperty(Property(SHOWING, Checklist("1", "2", "3")))
        task.setChecklistConstraint(task.checklist.toConstraint())
        task.setDurationProperty(Property(SHOWING, Duration.ofHours(2)))
        task.setDurationConstraint(task.duration.toConstraint())
        Assertions.assertEquals(2, task.getAllUnmetAndShowingConstraints().size)
        Assertions.assertTrue(task.isKillable)
        Assertions.assertFalse(task.canKill())
        Assertions.assertThrows(TaskStateException::class.java, { task.kill() })

        (task.checklist as Constraint).isMet = MET
        (task.duration as Constraint).isMet = MET
        Assertions.assertTrue(task.getAllUnmetAndShowingConstraints().isEmpty())
        task.kill()
        Assertions.assertEquals(TaskState.KILLED, task.getTaskState())
        Assertions.assertFalse(task.canKill())
    }

    //endregion

    //region Other

    @DisplayName("Task title")
    @Test
    fun testTaskTitle() {
        val task = Task("My Task")
        Assertions.assertEquals("My Task", task.title)
        task.title = "My Task modified"
        Assertions.assertEquals("My Task modified", task.title)
    }

    @DisplayName("Property and Constraint switching")
    @Test
    fun testPropertyAndConstraintSwitching() {
        val task = Task("My Task")
        Assertions.assertFalse(task.time is Constraint)

        // Change time Property to Constraint and check
        task.setTimeConstraint(task.time.toConstraint())
        Assertions.assertTrue(task.time is Constraint)

        // Change time Constraint back to a Property
        task.setTimeProperty((task.time as Constraint).toProperty())
        Assertions.assertFalse(task.time is Constraint)
        Assertions.assertThrows(ClassCastException::class.java, { (task.time as Constraint).isMet })
    }

    @DisplayName("Task creation using chaining by Values")
    @Test
    fun testTaskCreationUsingChainingByValues() {
        val task = Task("My Task")
                .setTimePropertyValue(LocalDateTime.of(1970, 1, 1, 1, 1))
                .setDurationPropertyValue(Duration.ofSeconds(15))
                .setPriorityValue(Priority.getOrCreatePriority("High", 1))
                .setLabelValue(Label.createNewLabel("Label1"))
                .setOptionalValue(true)
                .setDescriptionValue(StringBuilder("Description"))
                .setChecklistValue(Checklist("1", "2", "3"))
                .setDeadlineValue(LocalDateTime.of(1970, 1, 1, 1, 1))
                .setTargetValue("Target!")

        Assertions.assertEquals(LocalDateTime.of(1970, 1, 1, 1, 1), task.time.value)
        Assertions.assertTrue(task.time.isVisible)

        Assertions.assertEquals(Duration.ofSeconds(15), task.duration.value)
        Assertions.assertTrue(task.duration.isVisible)

        Assertions.assertEquals(Priority.getPriority("High", 1), task.priority.value)
        Assertions.assertTrue(task.priority.isVisible)

        Assertions.assertEquals(Label.getLabelByName("Label1"), task.label.value)
        Assertions.assertTrue(task.label.isVisible)

        Assertions.assertEquals(true, task.optional.value)
        Assertions.assertTrue(task.optional.value)

        Assertions.assertEquals(StringBuilder("Description").toString(), task.description.value.toString())
        Assertions.assertTrue(task.description.isVisible)

        Assertions.assertEquals(Checklist("1", "2", "3"), task.checklist.value)
        Assertions.assertTrue(task.checklist.isVisible)

        Assertions.assertEquals(LocalDateTime.of(1970, 1, 1, 1, 1), task.deadline.value)
        Assertions.assertTrue(task.checklist.isVisible)

        Assertions.assertEquals("Target!", task.target.value)
        Assertions.assertTrue(task.target.isVisible)

        Assertions.assertTrue(task.getAllShowingConstraints().isEmpty())
    }

    @DisplayName("Task creation using chaining by Properties")
    @Test
    fun testTaskCreationUsingChainingByProperties() {
        val task = Task("My Task")
                .setTimeProperty(Property(SHOWING, LocalDateTime.of(1970, 1, 1, 1, 1)))
                .setDurationProperty(Property(SHOWING, Duration.ofSeconds(15)))
                .setPriorityProperty(Property(SHOWING, Priority.getOrCreatePriority("High", 1)))
                .setLabelProperty(Property(SHOWING, Label.createNewLabel("Label1")))
                .setOptionalProperty(Property(SHOWING, true))
                .setDescriptionProperty(Property(SHOWING, StringBuilder("Description")))
                .setChecklistProperty(Property(SHOWING, Checklist("1", "2", "3")))
                .setDeadlineProperty(Property(SHOWING, LocalDateTime.of(1970, 1, 1, 1, 1)))
                .setTargetProperty(Property(SHOWING, "Target!"))

        Assertions.assertEquals(LocalDateTime.of(1970, 1, 1, 1, 1), task.time.value)
        Assertions.assertTrue(task.time.isVisible)

        Assertions.assertEquals(Duration.ofSeconds(15), task.duration.value)
        Assertions.assertTrue(task.duration.isVisible)

        Assertions.assertEquals(Priority.getPriority("High", 1), task.priority.value)
        Assertions.assertTrue(task.priority.isVisible)

        Assertions.assertEquals(Label.getLabelByName("Label1"), task.label.value)
        Assertions.assertTrue(task.label.isVisible)

        Assertions.assertEquals(true, task.optional.value)
        Assertions.assertTrue(task.optional.value)

        Assertions.assertEquals(StringBuilder("Description").toString(), task.description.value.toString())
        Assertions.assertTrue(task.description.isVisible)

        Assertions.assertEquals(Checklist("1", "2", "3"), task.checklist.value)
        Assertions.assertTrue(task.checklist.isVisible)

        Assertions.assertEquals(LocalDateTime.of(1970, 1, 1, 1, 1), task.deadline.value)
        Assertions.assertTrue(task.checklist.isVisible)

        Assertions.assertEquals("Target!", task.target.value)
        Assertions.assertTrue(task.target.isVisible)

        Assertions.assertTrue(task.getAllShowingConstraints().isEmpty())
    }

    //endregion

    @DisplayName("Checklist")
    @Test
    fun testChecklist() {
        val checkList = Checklist("Zero", "One", "Two", "Three", "Four")
        Assertions.assertEquals(5, checkList.size())

        checkList.addItem("Five")
        Assertions.assertEquals(6, checkList.size())

        checkList.checkItem(5)
        Assertions.assertTrue(checkList[5].isChecked)
        Assertions.assertEquals(6, checkList.size())

        checkList.deleteItem(5)
        Assertions.assertEquals(5, checkList.size())

        checkList.addItem(ListItem("FiveAgain", true))
        Assertions.assertEquals(6, checkList.size())

        checkList.uncheckItem(5)
        Assertions.assertFalse(checkList[5].isChecked)

    }
}