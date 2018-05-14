package uk.whitecrescent.waqti.tests.task

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import uk.whitecrescent.waqti.Duration
import uk.whitecrescent.waqti.Time
import uk.whitecrescent.waqti.now
import uk.whitecrescent.waqti.sleep
import uk.whitecrescent.waqti.task.Checklist
import uk.whitecrescent.waqti.task.Constraint
import uk.whitecrescent.waqti.task.Label
import uk.whitecrescent.waqti.task.MANDATORY
import uk.whitecrescent.waqti.task.OPTIONAL
import uk.whitecrescent.waqti.task.Priority
import uk.whitecrescent.waqti.task.Task
import uk.whitecrescent.waqti.taskIDs
import uk.whitecrescent.waqti.toArrayList

@DisplayName("Other Task Tests")
class OtherTask {

    @DisplayName("Task Title")
    @Test
    fun testTaskTitle() {
        val task = Task()
        assertEquals("", task.title)

        val task1 = Task("My Task")
        assertEquals("My Task", task1.title)
        task1.title = "My Task Updated"
        assertEquals("My Task Updated", task1.title)
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
        val time = Time.from(now.plusDays(7))
        val task1 = Task("Task").setTimeConstraintValue(time)
        val task2 = Task("Task")

        assertNotEquals(task1.state, task2.state)

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

        assertTrue(task1.age == 1)
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
        val time = Time.from(now.plusDays(7))
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
        val deadline = Time.from(now.plusDays(7))
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
        val subTasks = arrayListOf(Task("SubTask1"), Task("SubTask2")).taskIDs()
        val task1 = Task("Task").setSubTasksPropertyValue(subTasks.toArrayList)
        val task2 = Task("Task").setSubTasksPropertyValue(subTasks.toArrayList)

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
                "state: ${task.state}\n\tP:\n\tC:\n", task.toString())
    }

    @DisplayName("Task UnConstrainAll")
    @Test
    fun testTaskUnConstrainAll() {
        val beforeTask = Task("Before")
        val subTask1 = Task("SubTask1")
        val subTask2 = Task("SubTask2")

        val task = Task("My Task")
                .setTimePropertyValue(Time.of(2018, 5, 5, 5, 5))
                .setDurationPropertyValue(Duration.ofMinutes(30))
                .setPriorityValue(Priority.getOrCreatePriority("Priority", 5))
                .setLabelsValue(Label.getOrCreateLabel("Label1"), Label.getOrCreateLabel("Label2"))
                .setOptionalValue(OPTIONAL)
                .setDescriptionValue("Description")
                .setChecklistPropertyValue(Checklist("ZERO", "ONE", "TWO"))
                .setDeadlinePropertyValue(Time.of(2018, 6, 6, 6, 6))
                .setTargetConstraintValue("My Target")
                .setBeforePropertyValue(beforeTask)
                .setSubTasksPropertyValue(arrayListOf(subTask1, subTask2).taskIDs().toArrayList)

        task.unConstrainAll()

        assertTrue(task.getAllShowingConstraints().isEmpty())
        assertTrue(task.getAllUnmetAndShowingConstraints().isEmpty())

        assertFalse(task.time is Constraint)
        assertEquals(Time.of(2018, 5, 5, 5, 5), task.time.value)
        assertTrue(task.time.isVisible)

        assertFalse(task.duration is Constraint)
        assertEquals(Duration.ofMinutes(30), task.duration.value)
        assertTrue(task.duration.isVisible)

        assertFalse(task.priority is Constraint)
        assertEquals(Priority.getOrCreatePriority("Priority", 5), task.priority.value)
        assertTrue(task.priority.isVisible)

        assertFalse(task.labels is Constraint)
        assertEquals(arrayListOf(Label.getOrCreateLabel("Label1"), Label.getOrCreateLabel("Label2")), task.labels.value)
        assertTrue(task.labels.isVisible)

        assertFalse(task.optional is Constraint)
        assertEquals(OPTIONAL, task.optional.value)
        assertTrue(task.optional.isVisible)

        assertFalse(task.description is Constraint)
        assertEquals("Description", task.description.value)
        assertTrue(task.description.isVisible)

        assertFalse(task.checklist is Constraint)
        assertEquals(Checklist("ZERO", "ONE", "TWO"), task.checklist.value)
        assertTrue(task.checklist.isVisible)

        assertFalse(task.deadline is Constraint)
        assertEquals(Time.of(2018, 6, 6, 6, 6), task.deadline.value)
        assertTrue(task.deadline.isVisible)

        assertFalse(task.target is Constraint)
        assertEquals("My Target", task.target.value)
        assertTrue(task.target.isVisible)

        assertFalse(task.before is Constraint)
        assertEquals(beforeTask.taskID, task.before.value)
        assertTrue(task.before.isVisible)

        assertFalse(task.subTasks is Constraint)
        assertEquals(arrayListOf(subTask1, subTask2).taskIDs(), task.subTasks.value)
        assertTrue(task.subTasks.isVisible)
    }

}