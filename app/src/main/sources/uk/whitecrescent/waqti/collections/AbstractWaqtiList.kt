package uk.whitecrescent.waqti.collections

import java.util.function.Consumer

abstract class AbstractWaqtiList<E> : WaqtiList<E> {

    //region Properties

    private val initialSize = 10

    @OverrideRecommended //for the initial size being done at construction time
    protected open val list = ArrayList<E>(initialSize)

    @NoOverrideRecommended
    override val size: Int
        get() = list.size

    @NoOverrideRecommended
    val nextIndex: Int
        get() = list.lastIndex + 1

    //endregion Properties

    //region Operators

    @NoOverrideRecommended
    override operator fun get(index: Int) = list[index]

    override operator fun get(element: E): E {
        val found = list.find { it == element }
        if (found == null) {
            throw ElementNotFoundException("$element")
        } else
            return found
    }

    @NoOverrideRecommended
    override operator fun contains(element: E) = list.contains(element)

    //endregion Operators

    //region Add

    override fun add(element: E): WaqtiCollection<E> {
        list.add(element)
        return this
    }

    override fun addAt(index: Int, element: E): WaqtiList<E> {
        list.add(index, element)
        return this
    }

    override fun addAll(vararg elements: E) = addAll(elements.toList())

    override fun addAll(collection: Collection<E>): WaqtiCollection<E> {
        list.addAll(collection)
        return this
    }

    override fun addAllAt(index: Int, vararg elements: E) = addAllAt(index, elements.toList())

    override fun addAllAt(index: Int, collection: Collection<E>): WaqtiList<E> {
        list.addAll(index, collection)
        return this
    }

    override fun addIf(collection: Collection<E>, predicate: (E) -> Boolean): WaqtiCollection<E> {
        this.addAll(collection.filter(predicate))
        return this
    }

    //endregion Add

    //region Update

    @NoOverrideRecommended
    override fun update(old: E, new: E) = updateAt(indexOf(old), new)

    override fun updateAt(oldIndex: Int, newElement: E): WaqtiList<E> {
        this.removeAt(oldIndex)
        this.addAt(oldIndex, newElement)
        return this
    }

    override fun updateAllTo(collection: Collection<E>, new: E): WaqtiCollection<E> {
        val list = ArrayList<Int>(collection.size)
        for (toUpdate in collection) {
            for (element in this) {
                if (element == toUpdate) {
                    list.add(indexOf(element))
                }
            }
        }
        list.forEach { this.updateAt(it, new) }

        return this
    }

    //endregion Update

    //region Remove

    // Doesn't throw Exception if not found!
    override fun remove(element: E): WaqtiCollection<E> {
        list.remove(element)
        return this
    }

    override fun removeAt(index: Int): WaqtiList<E> {
        list.removeAt(index)
        return this
    }

    override fun removeAll(vararg elements: E) = removeAll(elements.toList())

    override fun removeAll(collection: Collection<E>): WaqtiCollection<E> {
        list.removeAll(collection)
        return this
    }

    override fun removeIf(predicate: (E) -> Boolean): WaqtiCollection<E> {
        list.removeIf(predicate)
        return this
    }

    override fun clear(): WaqtiCollection<E> {
        list.clear()
        return this
    }

    //Removes including at fromIndex but excluding at toIndex, just like subList
    //if numbers are equal won't do anything, again like subList
    //if from > to, unlike subList will still work, will remove reversed, remove in the range, including from and
    // excluding to see this.subList
    override fun removeRange(fromIndex: Int, toIndex: Int): WaqtiList<E> {
        this.removeAll(this.subList(fromIndex, toIndex))
        return this
    }

    //endregion Remove

    //region Query

    override fun getAll(vararg elements: E) = getAll(elements.toList())

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

    @NoOverrideRecommended
    override fun toList() = list.toList()

    override fun containsAll(vararg elements: E) = this.containsAll(elements.toList())

    override fun containsAll(elements: Collection<E>) = list.containsAll(elements)

    @NoOverrideRecommended
    override fun isEmpty() = list.isEmpty()

    //first index of the element
    @NoOverrideRecommended
    override fun indexOf(element: E): Int {
        if (list.indexOf(element) == -1) {
            throw ElementNotFoundException("$element")
        } else return list.indexOf(element)
    }

    @NoOverrideRecommended
    override fun lastIndexOf(element: E): Int {
        if (list.lastIndexOf(element) == -1) {
            throw ElementNotFoundException("$element")
        } else return list.lastIndexOf(element)
    }

    @NoOverrideRecommended
    override fun allIndexesOf(element: E): List<Int> {
        val result = ArrayList<Int>()
        this.forEachIndexed { index, it -> if (it == element) result.add(index) }
        return result.toList()
    }

    @NoOverrideRecommended
    override fun subList(fromIndex: Int, toIndex: Int): List<E> {
        when {
            fromIndex < 0 || toIndex < 0 || fromIndex > nextIndex || toIndex > nextIndex -> {
                throw  IndexOutOfBoundsException("Cannot SubList $fromIndex  to $toIndex, limits are 0 and $nextIndex")
            }
            fromIndex > toIndex -> return list.subList(toIndex + 1, fromIndex + 1)
            fromIndex < toIndex -> return list.subList(fromIndex, toIndex)
            else -> return emptyList()
        }
    }

    //endregion Query

    //region Manipulate

    override fun move(fromIndex: Int, toIndex: Int): WaqtiList<E> {
        when {
            toIndex > size - 1 || toIndex < 0 || fromIndex > size - 1 || fromIndex < 0 -> {
                throw  IndexOutOfBoundsException("Cannot move $fromIndex  to $toIndex, limits are 0 and $nextIndex")
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

    @NoOverrideRecommended
    override fun move(from: E, to: E) = move(indexOf(from), indexOf(to))

    override fun swap(thisIndex: Int, thatIndex: Int): WaqtiList<E> {
        val `this` = this[thisIndex]
        val that = this[thatIndex]
        this.updateAt(thatIndex, `this`)
        this.updateAt(thisIndex, that)
        return this
    }

    @NoOverrideRecommended
    override fun swap(`this`: E, that: E) = swap(indexOf(`this`), indexOf(that))

    override fun moveAllTo(vararg elements: E, toIndex: Int) = moveAllTo(elements.toList(), toIndex)

    override fun moveAllTo(collection: Collection<E>, toIndex: Int): WaqtiList<E> {
        val found = this.getAll(collection)
        if (found.isNotEmpty()) {
            this.removeAll(found)
            this.addAllAt(toIndex, found)
        }
        return this
    }

    override fun sort(comparator: Comparator<E>): WaqtiCollection<E> {
        list.sortWith(comparator)
        return this
    }

    //endregion Manipulate

    //region List Utils

    @NoOverrideRecommended
    override fun growTo(size: Int): WaqtiList<E> {
        list.ensureCapacity(size)
        return this
    }

    @NoOverrideRecommended
    override fun iterator() = list.iterator()

    @NoOverrideRecommended
    override fun parallelStream() = list.parallelStream()

    @NoOverrideRecommended
    override fun spliterator() = list.spliterator()

    @NoOverrideRecommended
    override fun stream() = list.stream()

    @NoOverrideRecommended
    override fun listIterator() = list.listIterator()

    @NoOverrideRecommended
    override fun listIterator(index: Int) = list.listIterator(index)

    @NoOverrideRecommended
    override fun forEach(action: Consumer<in E>?) = list.forEach(action)

    @OverrideRecommended
    override fun join(collection: Collection<E>): WaqtiCollection<E> {
        return this
    }

    //endregion List Utils

    //region Companion

    // TODO: 02-Apr-18 Test these!
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

    //endregion Companion

    override fun hashCode(): Int {
        return list.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return list.equals(other)
    }

    override fun toString(): String {
        return list.toString()
    }
}