package uk.whitecrescent.waqti.tests.task

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import uk.whitecrescent.waqti.code.Constraint
import uk.whitecrescent.waqti.code.DEFAULT_TASK_ID
import uk.whitecrescent.waqti.code.HIDDEN
import uk.whitecrescent.waqti.code.Property
import uk.whitecrescent.waqti.code.SHOWING
import uk.whitecrescent.waqti.code.Task
import uk.whitecrescent.waqti.code.TaskState
import uk.whitecrescent.waqti.code.TaskStateException
import uk.whitecrescent.waqti.code.UNMET
import uk.whitecrescent.waqti.code.database
import uk.whitecrescent.waqti.code.sleep
import uk.whitecrescent.waqti.tests.TestUtils.getTasks
import uk.whitecrescent.waqti.tests.TestUtils.testTask

class BeforeTests {

    @DisplayName("Before Default Values")
    @Test
    fun testTaskBeforeDefaultValues() {
        val task = testTask()
        assertFalse(task.before is Constraint)
        assertEquals(DEFAULT_TASK_ID, task.before.value)
        assertFalse(task.before.isVisible)
    }

    @DisplayName("Set Before Property using setBeforeProperty")
    @Test
    fun testTaskSetBeforeProperty() {
        val beforeTask = Task("Before Task")
        val task = testTask()
                .setBeforeProperty(
                        Property(SHOWING, beforeTask.taskID)
                )

        assertFalse(task.before is Constraint)
        assertEquals(beforeTask.taskID, task.before.value)
        assertEquals(beforeTask, database.get(task.before.value))
        assertTrue(task.before.isVisible)


        task.hideBefore()
        assertEquals(Property(HIDDEN, DEFAULT_TASK_ID), task.before)
    }

    @DisplayName("Set Before Property using setBeforeValue ID")
    @Test
    fun testTaskSetBeforeValueID() {
        val beforeTask = Task("Before Task")
        val task = testTask()
                .setBeforePropertyValue(
                        beforeTask.taskID
                )

        assertFalse(task.before is Constraint)
        assertEquals(beforeTask.taskID, task.before.value)
        assertEquals(beforeTask, database.get(task.before.value))
        assertTrue(task.before.isVisible)

        task.hideBefore()
        assertEquals(Property(HIDDEN, DEFAULT_TASK_ID), task.before)
    }

    @DisplayName("Set Before Property using setBeforeValue Task")
    @Test
    fun testTaskSetBeforeValueTask() {
        val beforeTask = Task("Before Task")
        val task = testTask()
                .setBeforePropertyValue(
                        beforeTask
                )

        assertFalse(task.before is Constraint)
        assertEquals(beforeTask.taskID, task.before.value)
        assertEquals(beforeTask, database.get(task.before.value))
        assertTrue(task.before.isVisible)

        task.hideBefore()
        assertEquals(Property(HIDDEN, DEFAULT_TASK_ID), task.before)
    }

    @DisplayName("Set Before Constraint using setBeforeProperty")
    @Test
    fun testTaskSetBeforePropertyWithConstraint() {
        val beforeTask = Task("Before Task")
        val task = testTask()
                .setBeforeProperty(
                        Constraint(SHOWING, beforeTask.taskID, UNMET)
                )

        assertTrue(task.before is Constraint)
        assertEquals(beforeTask.taskID, task.before.value)
        assertEquals(beforeTask, database.get(task.before.value))
        assertTrue(task.before.isVisible)
        assertFalse((task.before as Constraint).isMet)
    }

    @DisplayName("Set Before Constraint using setBeforeConstraint")
    @Test
    fun testTaskSetBeforeConstraint() {
        val beforeTask = Task("Before Task")
        val task = testTask()
                .setBeforeConstraint(
                        Constraint(SHOWING, beforeTask.taskID, UNMET)
                )

        assertTrue(task.before is Constraint)
        assertEquals(beforeTask.taskID, task.before.value)
        assertEquals(beforeTask, database.get(task.before.value))
        assertTrue(task.before.isVisible)
        assertFalse((task.before as Constraint).isMet)
    }

    @DisplayName("Set Before Constraint using setBeforeConstraintValue ID")
    @Test
    fun testTaskSetBeforeConstraintValueID() {
        val beforeTask = Task("Before Task")
        val task = testTask()
                .setBeforeConstraintValue(beforeTask.taskID)

        assertTrue(task.before is Constraint)
        assertEquals(beforeTask.taskID, task.before.value)
        assertEquals(beforeTask, database.get(task.before.value))
        assertTrue(task.before.isVisible)
        assertFalse((task.before as Constraint).isMet)
    }

    @DisplayName("Set Before Constraint using setBeforeConstraintValue Task")
    @Test
    fun testTaskSetBeforeConstraintValueTask() {
        val beforeTask = Task("Before Task")
        val task = testTask()
                .setBeforeConstraintValue(beforeTask)

        assertTrue(task.before is Constraint)
        assertEquals(beforeTask.taskID, task.before.value)
        assertEquals(beforeTask, database.get(task.before.value))
        assertTrue(task.before.isVisible)
        assertFalse((task.before as Constraint).isMet)
    }

    @DisplayName("Set Before Property failable")
    @Test
    fun testTaskSetBeforePropertyFailable() {
        val beforeTask = Task("Before Task")
        val task = testTask()
                .setBeforePropertyValue(beforeTask)

        assertFalse(task.isFailable)
        assertFalse(task.before is Constraint)
        assertEquals(beforeTask.taskID, task.before.value)
        assertTrue(task.before.isVisible)
    }

    @DisplayName("Set Before Constraint failable")
    @Test
    fun testTaskSetBeforeConstraintFailable() {
        val beforeTask = Task("Before Task")
        val task = testTask()
                .setBeforeConstraint(
                        Constraint(SHOWING, beforeTask.taskID, UNMET)
                )

        assertTrue(task.isFailable)
        assertTrue(task.before is Constraint)
        assertEquals(beforeTask.taskID, task.before.value)
        assertTrue(task.before.isVisible)
        assertFalse((task.before as Constraint).isMet)
    }

    @DisplayName("Kill with Before Property")
    @Test
    fun testTaskKillWithBeforeProperty() {
        val beforeTask = Task("Before Task")
        val task = testTask()
                .setBeforePropertyValue(beforeTask)

        task.kill()

        assertEquals(TaskState.KILLED, task.getTaskState())
    }

    @DisplayName("Kill with Before Constraint")
    @Test
    fun testTaskKillWithBeforeConstraint() {
        val beforeTask = Task("Before Task")
        val task = testTask()
                .setBeforeConstraintValue(beforeTask)

        assertThrows(TaskStateException::class.java, { task.kill() })

        assertFalse(task.getTaskState() == TaskState.KILLED)

        database.get(beforeTask.taskID)!!.kill()

        sleep(1)

        task.kill()

        assertTrue(task.getTaskState() == TaskState.KILLED)

    }

    @DisplayName("Fail Before Constraint")
    @Test
    fun testTaskFailBeforeConstraint() {
        val beforeTask = Task("Before Task")
        beforeTask.isFailable = true
        val task = testTask()
                .setBeforeConstraintValue(beforeTask)

        assertThrows(TaskStateException::class.java, { task.kill() })

        assertFalse(task.getTaskState() == TaskState.KILLED)

        database.get(beforeTask.taskID)!!.fail()

        sleep(2)

        assertThrows(TaskStateException::class.java, { task.kill() })

        assertTrue(task.getTaskState() == TaskState.FAILED)

    }

    @DisplayName("Set Before Constraint on many Tasks")
    @Test
    fun testTaskSetBeforeConstraintOnManyTasks() {
        val beforeTask = Task("Before Task")
        val tasks = getTasks(1000)
        tasks.forEach { it.setBeforeConstraintValue(beforeTask) }

        assertThrows(TaskStateException::class.java, { tasks.forEach { it.kill() } })

        database.get(beforeTask.taskID)!!.kill()

        sleep(2)

        tasks.forEach { it.kill() }

    }

    @DisplayName("Before Un-constraining")
    @Test
    fun testTaskBeforeUnConstraining() {
        val beforeTask = Task("Before Task")
        val task = testTask()
                .setBeforeConstraintValue(beforeTask)

        sleep(1)
        assertThrows(TaskStateException::class.java, { task.kill() })
        assertTrue(task.getAllUnmetAndShowingConstraints().size == 1)
        task.setBeforeProperty((task.before as Constraint).toProperty())

        sleep(1)

        assertTrue(task.getAllUnmetAndShowingConstraints().isEmpty())
        task.kill()
        assertEquals(TaskState.KILLED, task.getTaskState())
    }

    @DisplayName("Before Constraint Re-Set")
    @Test
    fun testTaskBeforeConstraintReSet() {
        val beforeTask = Task("Before Task")
        val task = testTask()
                .setBeforeConstraintValue(beforeTask)

        sleep(1)
        assertThrows(TaskStateException::class.java, { task.kill() })
        assertTrue(task.getAllUnmetAndShowingConstraints().size == 1)

        val newBeforeTask = Task("New Before Task")

        task.setBeforeConstraintValue(newBeforeTask)
        assertEquals(newBeforeTask.taskID, task.before.value)

        newBeforeTask.kill()

        sleep(1)

        task.kill()
        assertEquals(TaskState.KILLED, task.getTaskState())
    }

}