package uk.whitecrescent.waqti.collections

import uk.whitecrescent.waqti.Listable
import uk.whitecrescent.waqti.task.Task
import java.util.function.Consumer

class TestTuple(vararg elements: Task) {

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
    private fun order(): TestTuple {
        return this
    }

    fun getAll(): List<Listable> {
        return emptyList()
    }

    operator fun plus(element: Task) = add(element)

    operator fun minus(element: Task) = removeElement(element)

    protected val list = ArrayList<Task>(elements.size)

    val size: Int
        get() = list.size

    val nextIndex: Int
        get() = list.lastIndex + 1

    init {
        this.initAddAll(*elements)
    }

    fun initAddAll(vararg elements: Task) {

    }

    operator fun set(index: Int, element: Task) = addAt(index, element)

    operator fun get(index: Int) = list[index]

    operator fun get(element: Task) = list.find { it == element }

    fun add(element: Task) {
        list.add(element)
    }

    fun addAt(index: Int, element: Task) = list.add(index, element)

    fun addAll(vararg elements: Task) {
        list.addAll(elements)
    }

    fun addAll(collection: Collection<Task>) {
        list.addAll(collection)
    }

    fun addAllAt(index: Int, vararg elements: Task) = list.addAll(index, elements.toList())

    fun addAllAt(index: Int, collection: Collection<Task>) = list.addAll(index, collection)

    fun removeElement(element: Task) {
        list.remove(element)
    }

    fun removeAll(vararg elements: Task) {
        list.removeAll(elements)
    }

    fun removeAll(collection: Collection<Task>) {
        list.removeAll(collection)
    }

    fun removeAt(index: Int) = list.removeAt(index)

    fun clear() = list.clear()

    fun growTo(size: Int) = list.ensureCapacity(size)

    // TODO: 30-Mar-18 maybe this can be cleaned up since ArrayList resizes automatically
    fun move(fromIndex: Int, toIndex: Int) {
        when {
            toIndex > size - 1 || toIndex < 0 || fromIndex > size - 1 || fromIndex < 0 -> {
                throw IllegalArgumentException("Index doesn't exist!")
            }
            fromIndex == toIndex -> return

            fromIndex < toIndex -> {
                val itemToMove = list[fromIndex]
                for ((i, element) in list.filter { list.indexOf(it) in (fromIndex + 1)..toIndex }.withIndex()) {
                    list[fromIndex + i] = element
                }
                list[toIndex] = itemToMove
            }
            fromIndex > toIndex -> {
                val itemToMove = list[fromIndex]
                for ((i, element) in list.filter { list.indexOf(it) in toIndex..(fromIndex - 1) }.asReversed().withIndex()) {
                    list[fromIndex - i] = element
                }
                list[toIndex] = itemToMove
            }
        }
    }

    fun move(from: Task, to: Task) = move(indexOf(from), indexOf(to))

    fun swap(thisIndex: Int, thatIndex: Int) {
        val `this` = this[thisIndex]
        val that = this[thatIndex]
        this.addAt(thatIndex, `this`)
        this.addAt(thisIndex, that)
    }

    fun swap(`this`: Task, that: Task) = swap(indexOf(`this`), indexOf(that))

    fun moveAll(vararg elements: Task, toIndex: Int = this.nextIndex) {
        val found = this.getAll(*elements)
        if (found.isNotEmpty()) {
            this.removeAll(found)
            this.addAllAt(toIndex, found)
        }
    }

    fun removeRange(fromIndex: Int, toIndex: Int) {
        list.removeAll(this.subList(fromIndex, toIndex))
    }

    fun sort(comparator: Comparator<Task>): AbstractWaqtiList<Task>? {
        return null
    }

    fun getAll(vararg elements: Task): List<Task> {
        return emptyList()
    }

    fun getAll(collection: Collection<Task>): List<Task> {
        return emptyList()
    }

    companion object {
        fun <E> moveElement(listFrom: AbstractWaqtiList<E>, listTo: AbstractWaqtiList<E>,
                            element: E, toIndex: Int = listTo.nextIndex) {
            val found = listFrom[element]
            if (found != null) {
                listTo.addAt(toIndex, found)
                listFrom.removeElement(found)
            }
        }

        fun <E> moveElements(listFrom: AbstractWaqtiList<E>, listTo: AbstractWaqtiList<E>,
                             vararg elements: E, toIndex: Int = listTo.nextIndex) {
            val found = listFrom.getAll(*elements)
            if (found.isNotEmpty()) {
                listTo.addAllAt(toIndex, found)
                listFrom.removeAll(found)
            }
        }

        fun <E> moveElements(listFrom: AbstractWaqtiList<E>, listTo: AbstractWaqtiList<E>,
                             elements: Collection<E>, toIndex: Int = listTo.nextIndex) {
            val found = listFrom.getAll(elements)
            if (found.isNotEmpty()) {
                listTo.addAllAt(toIndex, found)
                listFrom.removeAll(found)
            }
        }

        fun <E> copyElement(listFrom: AbstractWaqtiList<E>, listTo: AbstractWaqtiList<E>,
                            element: E, toIndex: Int = listTo.nextIndex) {
            val found = listFrom[element]
            if (found != null) {
                listTo.addAt(toIndex, found)
            }
        }

        fun <E> copyElements(listFrom: AbstractWaqtiList<E>, listTo: AbstractWaqtiList<E>,
                             vararg elements: E, toIndex: Int = listTo.nextIndex) {
            val found = listFrom.getAll(*elements)
            if (found.isNotEmpty()) {
                listTo.addAllAt(toIndex, found)
            }
        }

        fun <E> copyElements(listFrom: AbstractWaqtiList<E>, listTo: AbstractWaqtiList<E>,
                             elements: Collection<E>, toIndex: Int = listTo.nextIndex) {
            val found = listFrom.getAll(elements)
            if (found.isNotEmpty()) {
                listTo.addAllAt(toIndex, found)
            }
        }

        fun <E> swapElements(listLeft: AbstractWaqtiList<E>, listRight: AbstractWaqtiList<E>,
                             elementsLeft: List<E>, elementsRight: List<E>,
                             indexIntoLeft: Int = listLeft.nextIndex, indexIntoRight: Int = listRight.nextIndex) {
            val foundLeft = listLeft.getAll(elementsLeft)
            val foundRight = listRight.getAll(elementsRight)
            when {
                foundLeft.isNotEmpty() && foundRight.isNotEmpty() -> {
                    listLeft.addAllAt(indexIntoLeft, foundRight)
                    listRight.addAllAt(indexIntoRight, foundLeft)
                    listLeft.removeAll(foundLeft)
                    listRight.removeAll(foundRight)
                }
                foundLeft.isEmpty() && foundRight.isNotEmpty() -> {
                    moveElements(listRight, listLeft, foundRight, indexIntoLeft)
                }
                foundLeft.isNotEmpty() && foundRight.isEmpty() -> {
                    moveElements(listLeft, listRight, foundLeft, indexIntoRight)
                }
            }
        }

        fun <E> merge(thisList: AbstractWaqtiList<E>, intoList: AbstractWaqtiList<E>): List<E> {
            intoList.addAll(thisList)
            val result = ArrayList<E>(intoList.size + thisList.size)
            result.addAll(intoList)
            result.addAll(thisList)
            return result.toList()
        }
    }

    fun toList() = list.toList()

    fun contains(element: Task) = list.contains(element)

    fun containsAll(elements: Collection<Task>) = list.containsAll(elements)

    fun isEmpty() = list.isEmpty()

    fun iterator() = list.iterator()

    fun parallelStream() = list.parallelStream()

    fun spliterator() = list.spliterator()

    fun stream() = list.stream()

    fun indexOf(element: Task): Int {
        if (list.indexOf(element) == -1) {
            throw IllegalArgumentException("Element ${element} not found")
        } else return list.indexOf(element)
    }

    fun lastIndexOf(element: Task) = list.lastIndexOf(element)

    fun listIterator() = list.listIterator()

    fun listIterator(index: Int) = list.listIterator(index)

    fun subList(fromIndex: Int, toIndex: Int) = list.subList(fromIndex, toIndex)

    fun forEach(action: Consumer<in Task>?) = list.forEach(action)
}

class WList(vararg elements: Task): AbstractWaqtiList<Task>() {
    fun initAddAll(vararg elements: Task) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}