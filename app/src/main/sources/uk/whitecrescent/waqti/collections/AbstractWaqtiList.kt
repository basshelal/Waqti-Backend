package uk.whitecrescent.waqti.collections

import java.util.function.Consumer

abstract class AbstractWaqtiList<E>(vararg elements: E) : List<E>, WaqtiCollection<E> {

    protected val list = ArrayList<E>(elements.size)

    override val size: Int
        get() = list.size

    val nextIndex: Int
        get() = list.lastIndex + 1

    init {
        this.initAddAll(*elements)
    }

    abstract fun initAddAll(vararg elements: E)

    operator fun set(index: Int, element: E) = addAt(index, element)

    override operator fun get(index: Int) = list[index]

    override operator fun get(element: E) = list.find { it == element }

    override fun add(element: E) {
        list.add(element)
    }

    open fun addAt(index: Int, element: E) = list.add(index, element)

    override fun addAll(vararg elements: E) {
        list.addAll(elements)
    }

    override fun addAll(collection: Collection<E>) {
        list.addAll(collection)
    }

    fun addAllAt(index: Int, vararg elements: E) = list.addAll(index, elements.toList())

    fun addAllAt(index: Int, collection: Collection<E>) = list.addAll(index, collection)

    override fun remove(element: E) {
        list.remove(element)
    }

    override fun removeAll(vararg elements: E) {
        list.removeAll(elements)
    }

    override fun removeAll(collection: Collection<E>) {
        list.removeAll(collection)
    }

    open fun removeAt(index: Int) = list.removeAt(index)

    override fun clear() = list.clear()

    open fun growTo(size: Int) = list.ensureCapacity(size)

    // TODO: 30-Mar-18 maybe this can be cleaned up since ArrayList resizes automatically
    open fun move(fromIndex: Int, toIndex: Int) {
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

    open fun move(from: E, to: E) = move(indexOf(from), indexOf(to))

    open fun swap(thisIndex: Int, thatIndex: Int) {
        val `this` = this[thisIndex]
        val that = this[thatIndex]
        this.addAt(thatIndex, `this`)
        this.addAt(thisIndex, that)
    }

    open fun swap(`this`: E, that: E) = swap(indexOf(`this`), indexOf(that))

    fun moveAll(vararg elements: E, toIndex: Int = this.nextIndex) {
        val found = this.getAll(*elements)
        if (found.isNotEmpty()) {
            this.removeAll(found)
            this.addAllAt(toIndex, found)
        }
    }

    fun removeRange(fromIndex: Int, toIndex: Int) {
        list.removeAll(this.subList(fromIndex, toIndex))
    }

    override fun sort(comparator: Comparator<E>): AbstractWaqtiList<E> {
        list.sortWith(comparator)
        return this
    }

    override fun getAll(vararg elements: E): List<E> {
        val result = ArrayList<E>(elements.size)
        for (fromElement in elements) {
            for (thisElement in this) {
                if (fromElement == thisElement) {
                    result.add(thisElement)
                }
            }
        }
        return result.toList()
    }

    override fun getAll(collection: Collection<E>): List<E> {
        val result = ArrayList<E>(collection.size)
        for (fromElement in collection) {
            for (thisElement in this) {
                if (fromElement == thisElement) {
                    result.add(thisElement)
                }
            }
        }
        return result.toList()
    }

    companion object {
        fun <E> moveElement(listFrom: AbstractWaqtiList<E>, listTo: AbstractWaqtiList<E>,
                            element: E, toIndex: Int = listTo.nextIndex) {
            val found = listFrom[element]
            if (found != null) {
                listTo.addAt(toIndex, found)
                listFrom.remove(found)
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

    override fun toList() = list.toList()

    override fun contains(element: E) = list.contains(element)

    override fun containsAll(elements: Collection<E>) = list.containsAll(elements)

    override fun isEmpty() = list.isEmpty()

    override fun iterator() = list.iterator()

    override fun parallelStream() = list.parallelStream()

    override fun spliterator() = list.spliterator()

    override fun stream() = list.stream()

    override fun indexOf(element: E): Int {
        if (list.indexOf(element) == -1) {
            throw IllegalArgumentException("Element ${element} not found")
        } else return list.indexOf(element)
    }

    override fun lastIndexOf(element: E) = list.lastIndexOf(element)

    override fun listIterator() = list.listIterator()

    override fun listIterator(index: Int) = list.listIterator(index)

    override fun subList(fromIndex: Int, toIndex: Int) = list.subList(fromIndex, toIndex)

    override fun forEach(action: Consumer<in E>?) = list.forEach(action)

}