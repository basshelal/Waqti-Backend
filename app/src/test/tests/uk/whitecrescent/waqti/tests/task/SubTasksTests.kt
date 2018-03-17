package uk.whitecrescent.waqti.tests.task

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import uk.whitecrescent.waqti.code.Constraint
import uk.whitecrescent.waqti.code.DEFAULT_SUB_TASKS
import uk.whitecrescent.waqti.code.DEFAULT_SUB_TASKS_PROPERTY
import uk.whitecrescent.waqti.code.HIDDEN
import uk.whitecrescent.waqti.code.Property
import uk.whitecrescent.waqti.code.SHOWING
import uk.whitecrescent.waqti.code.Task
import uk.whitecrescent.waqti.code.TaskState
import uk.whitecrescent.waqti.code.TaskStateException
import uk.whitecrescent.waqti.code.UNMET
import uk.whitecrescent.waqti.code.database
import uk.whitecrescent.waqti.code.sleep
import uk.whitecrescent.waqti.code.taskIDsToTasks
import uk.whitecrescent.waqti.code.tasksToTaskIDs
import uk.whitecrescent.waqti.tests.TestUtils.getTasks
import uk.whitecrescent.waqti.tests.TestUtils.testTask

class SubTasksTests {

    @DisplayName("SubTasks Default Values")
    @Test
    fun testTaskSubTasksDefaultValues() {
        val task = testTask()
        assertFalse(task.subTasks is Constraint)
        assertEquals(DEFAULT_SUB_TASKS, task.subTasks.value)
        assertFalse(task.subTasks.isVisible)
    }

    @DisplayName("Set SubTasks Property using setSubTasksProperty")
    @Test
    fun testTaskSetSubTasksProperty() {
        val subTasks = arrayListOf(
                Task("SubTask1"),
                Task("SubTask2"),
                Task("SubTask3")
        )
        val subTasksIDs = ArrayList(tasksToTaskIDs(subTasks))
        val task = testTask()
                .setSubTasksProperty(
                        Property(SHOWING, subTasksIDs)
                )

        assertFalse(task.subTasks is Constraint)
        assertEquals(subTasksIDs, task.subTasks.value)
        assertTrue(task.subTasks.isVisible)


        task.hideSubTasks()
        assertEquals(Property(HIDDEN, DEFAULT_SUB_TASKS), task.subTasks)
    }

    @DisplayName("Set SubTasks Property using setSubTasksValue")
    @Test
    fun testTaskSetSubTasksValue() {
        val subTasks = arrayListOf(
                Task("SubTask1"),
                Task("SubTask2"),
                Task("SubTask3")
        )
        val subTasksIDs = ArrayList(tasksToTaskIDs(subTasks))
        val task = testTask()
                .setSubTasksPropertyValue(
                        subTasksIDs
                )

        assertFalse(task.subTasks is Constraint)
        assertEquals(subTasksIDs, task.subTasks.value)
        assertTrue(task.subTasks.isVisible)

        task.hideSubTasks()
        assertEquals(Property(HIDDEN, DEFAULT_SUB_TASKS), task.subTasks)
    }

    @DisplayName("Set SubTasks Constraint using setSubTasksProperty")
    @Test
    fun testTaskSetSubTasksPropertyWithConstraint() {
        val subTasks = arrayListOf(
                Task("SubTask1"),
                Task("SubTask2"),
                Task("SubTask3")
        )
        val subTasksIDs = ArrayList(tasksToTaskIDs(subTasks))

        val task = testTask()
                .setSubTasksProperty(
                        Constraint(SHOWING, subTasksIDs, UNMET)
                )

        assertTrue(task.subTasks is Constraint)
        assertEquals(subTasksIDs, task.subTasks.value)
        assertTrue(task.subTasks.isVisible)
        assertFalse((task.subTasks as Constraint).isMet)
    }

    @DisplayName("Set SubTasks Constraint using setSubTasksConstraint")
    @Test
    fun testTaskSetSubTasksConstraint() {
        val subTasks = arrayListOf(
                Task("SubTask1"),
                Task("SubTask2"),
                Task("SubTask3")
        )
        val subTasksIDs = ArrayList(tasksToTaskIDs(subTasks))

        val task = testTask()
                .setSubTasksConstraint(
                        Constraint(SHOWING, subTasksIDs, UNMET)
                )

        assertTrue(task.subTasks is Constraint)
        assertEquals(subTasksIDs, task.subTasks.value)
        assertTrue(task.subTasks.isVisible)
        assertFalse((task.subTasks as Constraint).isMet)
    }

    @DisplayName("Set SubTasks Constraint using setSubTasksConstraintValue Task")
    @Test
    fun testTaskSetSubTasksConstraintValueTask() {
        val subTasks = arrayListOf(
                Task("SubTask1"),
                Task("SubTask2"),
                Task("SubTask3")
        )
        val subTasksIDs = ArrayList(tasksToTaskIDs(subTasks))

        val task = testTask()
                .setSubTasksConstraintValue(subTasksIDs)

        assertTrue(task.subTasks is Constraint)
        assertEquals(subTasksIDs, task.subTasks.value)
        assertTrue(task.subTasks.isVisible)
        assertFalse((task.subTasks as Constraint).isMet)
    }

    @DisplayName("Set SubTasks Property failable")
    @Test
    fun testTaskSetSubTasksPropertyFailable() {
        val subTasks = arrayListOf(
                Task("SubTask1"),
                Task("SubTask2"),
                Task("SubTask3")
        )
        val subTasksIDs = ArrayList(tasksToTaskIDs(subTasks))

        val task = testTask()
                .setSubTasksPropertyValue(subTasksIDs)

        assertFalse(task.isFailable)
        assertFalse(task.subTasks is Constraint)
        assertEquals(subTasksIDs, task.subTasks.value)
        assertTrue(task.subTasks.isVisible)
    }

    @DisplayName("Set SubTasks Constraint failable")
    @Test
    fun testTaskSetSubTasksConstraintFailable() {
        val subTasks = arrayListOf(
                Task("SubTask1"),
                Task("SubTask2"),
                Task("SubTask3")
        )
        val subTasksIDs = ArrayList(tasksToTaskIDs(subTasks))

        val task = testTask()
                .setSubTasksConstraint(
                        Constraint(SHOWING, subTasksIDs, UNMET)
                )

        assertTrue(task.isFailable)
        assertTrue(task.subTasks is Constraint)
        assertEquals(subTasksIDs, task.subTasks.value)
        assertTrue(task.subTasks.isVisible)
        assertFalse((task.subTasks as Constraint).isMet)
    }

    //TODO Something is broken with addSubTasks()
    @DisplayName("Add SubTasks")
    @Test
    fun testTaskAddSubTasks() {
        val task = testTask().addSubTasks(
                Task("SubTask1"),
                Task("SubTask2"),
                Task("SubTask3")
        )

        val subTasksIDs = ArrayList(tasksToTaskIDs(task.getSubTasksList()))

        assertFalse(task.subTasks is Constraint)
        assertEquals(subTasksIDs, task.subTasks.value)
        assertTrue(task.subTasks.isVisible)

        task.hideSubTasks()
        assertEquals(Property(HIDDEN, DEFAULT_SUB_TASKS), task.subTasks)
        assertEquals(DEFAULT_SUB_TASKS_PROPERTY, task.subTasks)
    }

    @DisplayName("Get SubTasks List")
    @Test
    fun testTaskGetSubTasksList() {
        val task = testTask().addSubTasks(
                Task("SubTask1"),
                Task("SubTask2"),
                Task("SubTask3")
        )

        val subTasksIDs = ArrayList(tasksToTaskIDs(task.getSubTasksList()))
        assertEquals(subTasksIDs, task.getSubTasksIDsList())
        assertEquals(taskIDsToTasks(subTasksIDs),task.getSubTasksList())

    }

    @DisplayName("Kill with SubTasks Property")
    @Test
    fun testTaskKillWithSubTasksProperty() {
        val subTasks = arrayListOf(
                Task("SubTask1"),
                Task("SubTask2"),
                Task("SubTask3")
        )
        val subTasksIDs = ArrayList(tasksToTaskIDs(subTasks))

        val task = testTask()
                .setSubTasksPropertyValue(subTasksIDs)

        task.kill()

        assertEquals(TaskState.KILLED, task.getTaskState())
    }

    @DisplayName("Kill with SubTasks Constraint")
    @Test
    fun testTaskKillWithSubTasksConstraint() {
        val subTasks = arrayListOf(
                Task("SubTask1"),
                Task("SubTask2"),
                Task("SubTask3")
        )
        val subTasksIDs = ArrayList(tasksToTaskIDs(subTasks))

        val task = testTask()
                .setSubTasksConstraintValue(subTasksIDs)

        assertThrows(TaskStateException::class.java, { task.kill() })

        assertFalse(task.getTaskState() == TaskState.KILLED)

        database.get(subTasksIDs[0])!!.kill()
        database.get(subTasksIDs[1])!!.kill()

        assertThrows(TaskStateException::class.java, { task.kill() })

        database.get(subTasksIDs[2])!!.kill()

        sleep(2)

        task.kill()

        assertTrue(task.getTaskState() == TaskState.KILLED)

    }

    @DisplayName("Fail SubTasks Constraint")
    @Test
    fun testTaskFailSubTasksConstraint() {
        val subTasks = arrayListOf(
                Task("SubTask1"),
                Task("SubTask2"),
                Task("SubTask3")
        )
        val subTasksIDs = ArrayList(tasksToTaskIDs(subTasks))

        subTasks.forEach { it.isFailable = true }

        val task = testTask()
                .setSubTasksConstraintValue(subTasksIDs)

        assertThrows(TaskStateException::class.java, { task.kill() })

        assertFalse(task.getTaskState() == TaskState.KILLED)

        database.get(subTasksIDs[0])!!.fail()
        database.get(subTasksIDs[1])!!.kill()
        database.get(subTasksIDs[2])!!.kill()

        sleep(2)

        assertThrows(TaskStateException::class.java, { task.kill() })

        assertTrue(task.getTaskState() == TaskState.FAILED)

    }

    @DisplayName("SubTasks Constraint on many Tasks")
    @Test
    fun testTaskSetSubTasksConstraintOnManyTasks() {
        val subTasks = arrayListOf(
                Task("SubTask1"),
                Task("SubTask2"),
                Task("SubTask3")
        )
        val subTasksIDs = ArrayList(tasksToTaskIDs(subTasks))

        val tasks = getTasks(1000)
        tasks.forEach { it.setSubTasksConstraintValue(subTasksIDs) }

        assertThrows(TaskStateException::class.java, { tasks.forEach { it.kill() } })

        database.get(subTasksIDs[0])!!.kill()
        database.get(subTasksIDs[1])!!.kill()
        database.get(subTasksIDs[2])!!.kill()

        sleep(2)

        tasks.forEach { it.kill() }

    }

    @DisplayName("SubTasks depth")
    @Test
    fun testTaskSubTasksDepth() {
        val root = Task("Root")
        val level1 = Task("Level1")
        val level2 = Task("Level2")
        val level3 = Task("Level3")
        val level4 = Task("Level4")

        level3.setSubTasksPropertyValue(arrayListOf(level4.taskID))
        level2.setSubTasksPropertyValue(arrayListOf(level3.taskID))
        level1.setSubTasksPropertyValue(arrayListOf(level2.taskID))
        root.setSubTasksPropertyValue(arrayListOf(level1.taskID))

        val subtasks = taskIDsToTasks(root.subTasks.value)

        println(subtasks)

    }

    //TODO test with SubTasks befores and activity within a Task's subtasks

}