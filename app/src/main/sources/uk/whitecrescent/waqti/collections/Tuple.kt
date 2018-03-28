package uk.whitecrescent.waqti.collections

import uk.whitecrescent.waqti.Listable
import uk.whitecrescent.waqti.task.Task
import java.util.Vector

class Tuple(vararg tasks: Task) : WaqtiCollection<Task>, Listable {

    val list = Vector<Task>() // TODO: 28-Mar-18 Vector or ArrayList? Do we need thread safety?

    override fun addAll(collection: Collection<Task>) {
        addAll(*collection.toTypedArray())
    }

    override fun add(element: Task) {
        addAt(list.lastIndex + 1, element)
    }

    override fun remove(element: Task) {

    }

    override fun removeAt(index: Int) {

    }

    override fun clear() {

    }

    override fun sort(): WaqtiCollection<Task> {

        return this
    }

    override fun toList(): List<Task> {
        return list.toList()
    }

    override val size: Int
        get() = list.size

    override fun contains(element: Task) = list.contains(element)

    override fun containsAll(elements: Collection<Task>) = list.containsAll(elements)

    override fun isEmpty() = list.isEmpty()

    override operator fun set(index: Int, element: Task) {

    }

    override operator fun get(index: Int) = list[index]

    override operator fun get(element: Task) = list.find { it == element }

    init {
        addAll(*tasks)
    }

    fun constrainAll() {
        for (index in 1..list.lastIndex) {
            list[index].setBeforeConstraintValue(list[index - 1].taskID)
        }
    }

    fun unConstrainAll() {
        for (index in 1..list.lastIndex) {
            list[index].setBeforePropertyValue(list[index - 1].taskID)
        }
    }

    override fun addAll(vararg elements: Task) {
        when {
            elements.size < 1 -> {
                throw IllegalStateException("Tuple must have at least 1 Task!")
            }
            elements.size > 1 -> {
                list.addAll(elements.asList())
                for (index in 1..list.lastIndex) {
                    list[index].setBeforePropertyValue(list[index - 1].taskID)
                }
            }
        }
    }

    override fun addAt(index: Int, element: Task) {
        list.add(index, element)
        list[index].setBeforePropertyValue(list[index - 1])
        list[index + 1].setBeforePropertyValue(list[index])
    }

    // Adds the passed in Task to the end of the Tuple
    fun addToEndAndConstrain(task: Task) {
        addAndConstrainAt(list.lastIndex + 1, task)
    }

    fun addAndConstrainAt(index: Int, task: Task) {
        list.add(index, task)
        list[index].setBeforeConstraintValue(list[index - 1])
        list[index + 1].setBeforeConstraintValue(list[index])
    }

    fun killTaskAt(index: Int) {
        when {
            index < 0 -> {
                throw IndexOutOfBoundsException("Index cannot be 0")
            }
            index > list.size - 1 -> {
                throw IndexOutOfBoundsException("Index cannot exceed ${list.size - 1}")
            }
            else -> {
                list[index].kill()
            }
        }
    }

    fun killTask(task: Task) {
        val task0 = get(task)
        when {
            task0 == null -> {
                throw IllegalStateException("Task not found!")
            }
            else -> {
                task0.kill()
            }
        }
    }

    override fun iterator() = list.iterator()

}
