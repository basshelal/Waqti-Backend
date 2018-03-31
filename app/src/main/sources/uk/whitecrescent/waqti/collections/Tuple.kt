package uk.whitecrescent.waqti.collections

import uk.whitecrescent.waqti.Listable
import uk.whitecrescent.waqti.task.Task
import uk.whitecrescent.waqti.toTasks

class Tuple(vararg tasks: Task) : AbstractWaqtiList<Task>(), Listable {
    constructor(tuple: Tuple) : this(*tuple.toTypedArray())
    constructor(collection: Collection<Task>) : this(*collection.toTypedArray())
    constructor(list: List<Tuple>) : this(*list.toTasks())

    init {
        list.addAll(tasks)
        unConstrainAll()
    }

    override val size: Int
        get() = list.size

    override fun sort(comparator: Comparator<Task>): AbstractWaqtiList<Task> {
        super.sort(comparator)
        return this.order()
    }

    override fun join(collection: Collection<Task>): WaqtiCollection<Task> {
        if (collection !is Tuple) {
            throw ClassCastException("Cannot merge Tuple with non Tuple")
        } else {
            val result = Tuple(*this.toTypedArray())
            result.addAll(collection.getAll())
            return result
        }
    }

    fun mergeToList(listable: Listable): List<Listable> {
        val result = ArrayList<Listable>(listable.toListables().size + this.list.size)
        result.addAll(this.list)
        result.addAll(listable.toListables())
        return result.toList()
    }

    override fun toListables() = this.toList()

    override fun contains(element: Task) = list.contains(element)

    override fun containsAll(elements: Collection<Task>) = list.containsAll(elements)

    override fun isEmpty() = list.isEmpty()

    override fun addAll(vararg elements: Task): WaqtiCollection<Task> {
        // TODO: 29-Mar-18 fix this
        when {
            elements.isNotEmpty() -> {
                list.addAll(elements)
                if (list.size > 1) {
                    for (index in 1..list.lastIndex) {
                        list[index].setBeforePropertyValue(list[index - 1].taskID)
                    }
                }
            }
        }
        return this
    }

    override fun addAt(index: Int, element: Task): WaqtiList<Task> {
        list.add(index, element)
        list[index].setBeforePropertyValue(list[index - 1])
        list[index + 1].setBeforePropertyValue(list[index])
        return this
    }

    fun constrainAll() {
        if (list.size > 1) {
            for (index in 1..list.lastIndex) {
                list[index].setBeforeConstraintValue(list[index - 1].taskID)
            }
        }
    }

    fun constrainAt(index: Int) {
        when {
            index < 0 -> {
                throw IndexOutOfBoundsException("Index cannot be 0")
            }
            index > list.size - 1 -> {
                throw IndexOutOfBoundsException("Index cannot exceed ${list.size - 1}")
            }
            list.size == 1 -> {
                throw IndexOutOfBoundsException("Cannot Constrain, there is only 1 Task in Tuple")
            }
            else -> {
                list[index].setBeforeConstraintValue(list[index - 1])
            }
        }
    }

    fun constrain(element: Task) {
        val found = get(element)
        when {
            found == null -> {
                throw IllegalStateException("Task not found!")
            }
            else -> {
                constrainAt(indexOf(found))
            }
        }
    }

    fun unConstrainAll() {
        if (list.size > 1) {
            for (index in 1..list.lastIndex) {
                list[index].setBeforePropertyValue(list[index - 1].taskID)
            }
        }
    }

    fun unConstrainAt(index: Int) {
        when {
            index < 0 -> {
                throw IndexOutOfBoundsException("Index cannot be 0")
            }
            index > list.size - 1 -> {
                throw IndexOutOfBoundsException("Index cannot exceed ${list.size - 1}")
            }
            list.size == 1 -> {
                throw IndexOutOfBoundsException("Cannot Constrain, there is only 1 Task in Tuple")
            }
            else -> {
                list[index].setBeforePropertyValue(list[index - 1])
            }
        }
    }

    fun unConstrain(element: Task) {
        val found = get(element)
        when {
            found == null -> {
                throw IllegalStateException("Task not found!")
            }
            else -> {
                unConstrainAt(indexOf(found))
            }
        }
    }

    fun addAndConstrain(task: Task) {
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
        val found = get(task)
        when {
            found == null -> {
                throw IllegalStateException("Task not found!")
            }
            else -> {
                found.kill()
            }
        }
    }

    // TODO: 28-Mar-18 I don't know how useful this even is at all
    private fun order(): Tuple {
        if (this.list.minus(this.list.first())
                        .filterIndexed { index, _ -> this.list[index].before.value != this.list[index - 1].taskID }
                        .isNotEmpty()) {
            val tasks = this.getAll()
            this.clear()
            this.addAll(tasks)
        }
        return this
    }

}