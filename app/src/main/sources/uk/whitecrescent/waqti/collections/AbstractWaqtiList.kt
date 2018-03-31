package uk.whitecrescent.waqti.collections

import java.util.function.Consumer

abstract class AbstractWaqtiList<E> : WaqtiList<E> {

    private val initialSize = 10

    protected val list = ArrayList<E>(initialSize)

    override val size: Int
        get() = list.size

    val nextIndex: Int
        get() = list.lastIndex + 1

    override operator fun get(index: Int) = list[index]

    override operator fun get(element: E) = list.find { it == element }

    override fun add(element: E): WaqtiCollection<E> {
        list.add(element)
        return this
    }

    override fun addAt(index: Int, element: E): WaqtiList<E> {
        list.add(index, element)
        return this
    }

    override fun addAll(vararg elements: E): WaqtiCollection<E> {
        list.addAll(elements)
        return this
    }

    override fun addAll(collection: Collection<E>): WaqtiCollection<E> {
        list.addAll(collection)
        return this
    }

    override fun addAllAt(index: Int, vararg elements: E): WaqtiList<E> {
        list.addAll(index, elements.toList())
        return this
    }

    override fun addAllAt(index: Int, collection: Collection<E>): WaqtiList<E> {
        list.addAll(index, collection)
        return this
    }

    override fun remove(element: E): WaqtiCollection<E> {
        list.remove(element)
        return this
    }

    override fun removeAll(vararg elements: E): WaqtiCollection<E> {
        list.removeAll(elements)
        return this
    }

    override fun removeAll(collection: Collection<E>): WaqtiCollection<E> {
        list.removeAll(collection)
        return this
    }

    override fun removeAt(index: Int): WaqtiList<E> {
        list.removeAt(index)
        return this
    }

    override fun removeIf(predicate: (E) -> Boolean): WaqtiCollection<E> {
        list.removeIf(predicate)
        return this
    }

    override fun addIf(collection: Collection<E>, predicate: (E) -> Boolean): WaqtiCollection<E> {
        this.addAll(collection.filter(predicate))
        return this
    }

    override fun clear(): WaqtiCollection<E> {
        list.clear()
        return this
    }

    override fun growTo(size: Int): WaqtiList<E> {
        list.ensureCapacity(size)
        return this
    }

    // TODO: 30-Mar-18 maybe this can be cleaned up since ArrayList resizes automatically
    override fun move(fromIndex: Int, toIndex: Int): WaqtiList<E> {
        when {
            toIndex > size - 1 || toIndex < 0 || fromIndex > size - 1 || fromIndex < 0 -> {
                throw IllegalArgumentException("Index doesn't exist!")
            }
            fromIndex == toIndex -> return this

            fromIndex < toIndex -> {
                val itemToMove = list[fromIndex]
                for ((i, element) in list.filter { list.indexOf(it) in (fromIndex + 1)..toIndex }.withIndex()) {
                    list[fromIndex + i] = element
                }
                list[toIndex] = itemToMove
                return this
            }
            fromIndex > toIndex -> {
                val itemToMove = list[fromIndex]
                for ((i, element) in list.filter { list.indexOf(it) in toIndex..(fromIndex - 1) }.asReversed().withIndex()) {
                    list[fromIndex - i] = element
                }
                list[toIndex] = itemToMove
                return this
            }
            else -> {
                throw IllegalArgumentException("Unknown error when trying to move $fromIndex to $toIndex in $this ")
            }
        }
    }

    override fun move(from: E, to: E) = move(indexOf(from), indexOf(to))

    override fun swap(thisIndex: Int, thatIndex: Int): WaqtiList<E> {
        val `this` = this[thisIndex]
        val that = this[thatIndex]
        this.addAt(thatIndex, `this`)
        this.addAt(thisIndex, that)
        return this
    }

    override fun swap(`this`: E, that: E) = swap(indexOf(`this`), indexOf(that))

    override fun moveAllTo(vararg elements: E, toIndex: Int): WaqtiList<E> {
        val found = this.getAll(*elements)
        if (found.isNotEmpty()) {
            this.removeAll(found)
            this.addAllAt(toIndex, found)
        }
        return this
    }

    override fun removeRange(fromIndex: Int, toIndex: Int): WaqtiList<E> {
        list.removeAll(this.subList(fromIndex, toIndex))
        return this
    }

    override fun sort(comparator: Comparator<E>): WaqtiCollection<E> {
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

    override fun toList() = list.toList()

    override operator fun contains(element: E) = list.contains(element)

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

        fun <E> join(thisList: AbstractWaqtiList<E>, intoList: AbstractWaqtiList<E>): List<E> {
            intoList.addAll(thisList)
            val result = ArrayList<E>(intoList.size + thisList.size)
            result.addAll(intoList)
            result.addAll(thisList)
            return result.toList()
        }
    }


}