package uk.whitecrescent.waqti.tests.task

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import uk.whitecrescent.waqti.code.Checklist
import uk.whitecrescent.waqti.code.Label
import uk.whitecrescent.waqti.code.MANDATORY
import uk.whitecrescent.waqti.code.OPTIONAL
import uk.whitecrescent.waqti.code.Priority
import uk.whitecrescent.waqti.code.Task
import uk.whitecrescent.waqti.code.Time
import uk.whitecrescent.waqti.code.now
import uk.whitecrescent.waqti.code.sleep
import uk.whitecrescent.waqti.code.tasksToTaskIDs
import java.time.Duration

@DisplayName("Other Task Tests")
class OtherTests {

    @DisplayName("Task Title")
    @Test
    fun testTaskTitle() {
        val task = Task("My Task")
        assertEquals("My Task", task.title)
        task.title = "My Task Updated"
        assertEquals("My Task Updated", task.title)
    }

    @DisplayName("Task HashCode Equal")
    @Test
    fun testTaskHashCode() {
        val task = Task("My Task")
        val task1 = Task("My Task")
        assertEquals(task1.hashCode(), task.hashCode())
        task1.title = "My Task "
        assertNotEquals(task1.hashCode(), task.hashCode())
    }

    @DisplayName("Task HashCode Not Equal")
    @Test
    fun testTaskHashCodeNotEqual() {
        val task = Task("My Task")
        val task1 = Task("My Task ")
        assertNotEquals(task1.hashCode(), task.hashCode())
    }

    @DisplayName("Task Equals Title")
    @Test
    fun testTaskEqualsTitle() {
        assertTrue(Task("My Task") == Task("My Task"))
        assertFalse(Task("Task") == Task("Task "))
    }

    @DisplayName("Task Equals State")
    @Test
    fun testTaskEqualsState() {
        val time = Time.from(now().plusDays(7))
        val task1 = Task("Task").setTimeConstraintValue(time)
        val task2 = Task("Task")

        assertNotEquals(task1.getTaskState(), task2.getTaskState())

        assertFalse(task1 == task2)
        task2.setTimeConstraintValue(time)
        assertTrue(task1 == task2)
    }

    @DisplayName("Task Equals isFailable")
    @Test
    fun testTaskEqualsIsFailable() {
        val task1 = Task("Task")
        val task2 = Task("Task")

        task1.isFailable = true
        task2.isFailable = true

        assertTrue(task1 == task2)

        task1.isFailable = true
        task2.isFailable = false

        assertNotEquals(task1.isFailable, task2.isFailable)
        assertFalse(task1 == task2)
    }

    @DisplayName("Task Equals isKillable")
    @Test
    fun testTaskEqualsIsKillable() {
        val task1 = Task("Task")
        val task2 = Task("Task")

        task1.isKillable = true
        task2.isKillable = true

        assertTrue(task1 == task2)

        task1.isKillable = true
        task2.isKillable = false

        assertNotEquals(task1.isKillable, task2.isKillable)
        assertFalse(task1 == task2)
    }

    @DisplayName("Task Equals Age and Failed Times")
    @Test
    fun testTaskEqualsAgeAndFailedTimes() {
        val task1 = Task("Task")
        val task2 = Task("Task")

        task1.isFailable = true
        task2.isFailable = true

        assertTrue(task1 == task2)

        task1.fail()

        assertNotEquals(task1.age, task2.age)
        assertNotEquals(task1.failedTimes, task2.failedTimes)

        assertFalse(task1 == task2)
    }

    @DisplayName("Task Equals Killed Time")
    @Test
    fun testTaskEqualsKilledTime() {
        val task1 = Task("Task")
        val task2 = Task("Task")

        assertTrue(task1 == task2)

        task1.kill()

        sleep(1)

        task2.kill()

        assertNotEquals(task1.killedTime, task2.killedTime)
        assertFalse(task1 == task2)
    }

    @DisplayName("Task Equals Time")
    @Test
    fun testTaskEqualsTime() {
        val time = Time.from(now().plusDays(7))
        val task1 = Task("Task").setTimePropertyValue(time)
        val task2 = Task("Task").setTimePropertyValue(time)

        assertTrue(task1 == task2)

        task1.setTimeProperty(task1.time.toConstraint())
        task2.setTimeProperty(task2.time.toConstraint())

        assertTrue(task1 == task2)

        task1.setTimePropertyValue(time.plusNanos(200))
        task2.setTimePropertyValue(time.plusNanos(300))

        assertNotEquals(task1.time.value, task2.time.value)
        assertFalse(task1 == task2)
    }

    @DisplayName("Task Equals Duration")
    @Test
    fun testTaskEqualsDuration() {
        val duration = Duration.ofSeconds(30)
        val task1 = Task("Task").setDurationPropertyValue(duration)
        val task2 = Task("Task").setDurationPropertyValue(duration)

        assertTrue(task1 == task2)

        task1.setDurationProperty(task1.duration.toConstraint())
        task2.setDurationProperty(task2.duration.toConstraint())

        assertTrue(task1 == task2)

        task1.setDurationPropertyValue(duration.plusNanos(200))
        task2.setDurationPropertyValue(duration.plusNanos(300))

        assertNotEquals(task1.duration.value, task2.duration.value)
        assertFalse(task1 == task2)
    }

    @DisplayName("Task Equals Priority")
    @Test
    fun testTaskEqualsPriority() {
        val priority = Priority.getOrCreatePriority("Priority", 1)
        val task1 = Task("Task").setPriorityValue(priority)
        val task2 = Task("Task").setPriorityValue(priority)

        assertTrue(task1 == task2)

        task2.setPriorityValue(Priority.getOrCreatePriority("Priority ", 1))

        assertNotEquals(task1.priority.value, task2.priority.value)
        assertFalse(task1 == task2)
    }

    @DisplayName("Task Equals Labels")
    @Test
    fun testTaskEqualsLabels() {
        val label = Label.getOrCreateLabel("Label")
        val task1 = Task("Task").setLabelsValue(label)
        val task2 = Task("Task").setLabelsValue(label)

        assertTrue(task1 == task2)

        task2.setLabelsValue(Label.getOrCreateLabel("Label "))

        assertNotEquals(task1.labels.value, task2.labels.value)
        assertFalse(task1 == task2)
    }

    @DisplayName("Task Equals Optional")
    @Test
    fun testTaskEqualsOptional() {
        val task1 = Task("Task").setOptionalValue(OPTIONAL)
        val task2 = Task("Task").setOptionalValue(OPTIONAL)

        assertTrue(task1 == task2)

        task2.setOptionalValue(MANDATORY)

        assertNotEquals(task1.optional.value, task2.optional.value)
        assertFalse(task1 == task2)
    }

    @DisplayName("Task Equals Description")
    @Test
    fun testTaskEqualsDescription() {
        val task1 = Task("Task").setDescriptionValue("Description")
        val task2 = Task("Task").setDescriptionValue("Description")

        assertTrue(task1 == task2)

        task2.setDescriptionValue("Description ")

        assertNotEquals(task1.description.value, task2.description.value)
        assertFalse(task1 == task2)
    }

    @DisplayName("Task Equals Checklist")
    @Test
    fun testTaskEqualsChecklist() {
        val checklist = Checklist("ZERO", "ONE")
        val task1 = Task("Task").setChecklistPropertyValue(checklist)
        val task2 = Task("Task").setChecklistPropertyValue(checklist)

        assertTrue(task1 == task2)

        task1.setChecklistProperty(task1.checklist.toConstraint())
        task2.setChecklistProperty(task2.checklist.toConstraint())

        assertTrue(task1 == task2)

        val newChecklist = Checklist("ZERO")

        task1.setChecklistConstraintValue(newChecklist)

        assertNotEquals(task1.checklist.value, task2.checklist.value)
        assertFalse(task1 == task2)
    }

    @DisplayName("Task Equals Deadline")
    @Test
    fun testTaskEqualsDeadline() {
        val deadline = Time.from(now().plusDays(7))
        val task1 = Task("Task").setDeadlinePropertyValue(deadline)
        val task2 = Task("Task").setDeadlinePropertyValue(deadline)

        assertTrue(task1 == task2)

        task1.setDeadlineProperty(task1.deadline.toConstraint())
        task2.setDeadlineProperty(task2.deadline.toConstraint())

        assertTrue(task1 == task2)

        task1.setDeadlineConstraintValue(deadline.plusNanos(200))

        assertNotEquals(task1.deadline.value, task2.deadline.value)
        assertFalse(task1 == task2)
    }

    @DisplayName("Task Equals Target")
    @Test
    fun testTaskEqualsTarget() {
        val task1 = Task("Task").setTargetPropertyValue("Target")
        val task2 = Task("Task").setTargetPropertyValue("Target")

        assertTrue(task1 == task2)

        task1.setTargetProperty(task1.target.toConstraint())
        task2.setTargetProperty(task2.target.toConstraint())

        assertTrue(task1 == task2)

        task1.setTargetConstraintValue("Target ")

        assertNotEquals(task1.target.value, task2.target.value)
        assertFalse(task1 == task2)
    }

    @DisplayName("Task Equals Before")
    @Test
    fun testTaskEqualsBefore() {
        val before = Task("Before")
        val task1 = Task("Task").setBeforePropertyValue(before)
        val task2 = Task("Task").setBeforePropertyValue(before)

        assertTrue(task1 == task2)

        task1.setBeforeProperty(task1.before.toConstraint())
        task2.setBeforeProperty(task2.before.toConstraint())

        assertTrue(task1 == task2)

        task1.setBeforeConstraintValue(Task("Before"))

        assertNotEquals(task1.before.value, task2.before.value)
        assertFalse(task1 == task2)
    }

    @DisplayName("Task Equals SubTasks")
    @Test
    fun testTaskEqualsSubTasks() {
        val subTasks = tasksToTaskIDs(Task("SubTask1"), Task("SubTask2"))
        val task1 = Task("Task").setSubTasksPropertyValue(subTasks)
        val task2 = Task("Task").setSubTasksPropertyValue(subTasks)

        assertTrue(task1 == task2)

        task1.setSubTasksProperty(task1.subTasks.toConstraint())
        task2.setSubTasksProperty(task2.subTasks.toConstraint())

        assertTrue(task1 == task2)

        val newSubTasks = arrayListOf(subTasks.get(0))

        task1.setSubTasksConstraintValue(newSubTasks)

        assertNotEquals(task1.subTasks.value, task2.subTasks.value)
        assertFalse(task1 == task2)
    }

    @DisplayName("Task toString")
    @Test
    fun testTaskToString() {
        val task = Task("My Task")
        assertEquals("${task.title}\nID: ${task.taskID} " +
                "isKillable: ${task.isKillable} " +
                "isFailable: ${task.isFailable} " +
                "state: ${task.getTaskState()}\n\tP:\n\tC:\n", task.toString())
    }

}