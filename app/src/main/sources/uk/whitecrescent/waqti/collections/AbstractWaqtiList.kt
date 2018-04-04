package uk.whitecrescent.waqti.collections

import java.util.function.Consumer

// TODO: 03-Apr-18 Document this!
// TODO: 04-Apr-18 Consider deleting all the made up annotations, I don't know if we need them
abstract class AbstractWaqtiList<E> : WaqtiList<E> {

    //region Properties

    /**
     * The backing data structure of this list, an [java.util.ArrayList], this class is essentially a modified
     * ArrayList as it provides extra functionality as well as modifies some of the operations of the ArrayList
     * implementation like [java.util.ArrayList.indexOf] which returns -1 instead of an exception if the element is
     * not found.
     *
     * ## Override Recommended:
     * Override for changing the construction of the ArrayList to allow for an initial size or an initial collection,
     * this is recommended for efficiency reasons as the default construction may not be efficient.
     *
     * If not overridden then the construction of the list will be an empty ArrayList with the capacity of 15 which
     * can be slightly inefficient for certain applications.
     *
     * @see java.util.ArrayList
     */
    @OverrideRecommended //for the initial size being done at construction time
    protected open val list = ArrayList<E>(15)

    /**
     * The integer value representing the size of this list.
     *
     * ## No Override Recommended:
     * There is no need to override since this field is just taken from this class's `list`'s size.
     *
     * Should an override be done the internals of the implementor should not be affected however the caller may
     * receive false information.
     *
     * @see java.util.ArrayList.size
     */
    @NoOverrideRecommended
    override val size: Int
        get() = list.size

    /**
     * The integer value representing the next index in this list, this is the index after the last, the one where
     * new elements will be added to. This is very useful to define the index limits of this list, elements can only
     * be modified between 0 and this integer, if trying to add at an index greater than this (or less than 0) then
     * an [IndexOutOfBoundsException] will be thrown.
     *
     * For example if this list is `(A ,B ,C)` it has `size` 3, its last index is 2 (the size of the list - 1) and so
     * the next index is the one after that, index 3, where a new element will be added.
     *
     * ## No Override Recommended:
     * An override here is highly recommended against, the internals of this class rely on this value for accurate
     * limits or bounds of manipulation of elements with indexes.
     *
     * If an override is done then it is done at the programmer's risk, [IndexOutOfBoundsException] may be thrown
     * when the index isn't really out of bounds, since the throwing of [IndexOutOfBoundsException] here uses this
     * value.
     *
     * @see List.lastIndex
     */
    @NoOverrideRecommended
    val nextIndex: Int
        get() = list.lastIndex + 1

    //endregion Properties

    //region Operators

    /**
     * Gets the element in the passed in index, if the index is out of the bounds of this list in this case meaning
     * less than 0 or greater than the last index of the list then an [IndexOutOfBoundsException] will be thrown.
     *
     * ## No Override Recommended:
     * No Override is recommended since this implementation is just taken from the ArrayList's get method
     * [java.util.ArrayList.get]
     *
     * @see java.util.ArrayList.get
     * @param index the index of the element to return
     * @return the element at the index passed in
     * @throws IndexOutOfBoundsException if the index is out of the bounds of this list
     */
    @NoOverrideRecommended
    @Throws(IndexOutOfBoundsException::class)
    override operator fun get(index: Int) = list[index]

    /**
     * Gets the first instance of an element that is equal to the element passed in (note that this will depend on
     * that element's [Any.equals] method implementation).
     *
     * @see Iterable.find
     * @param element the element to get the first instance of an equal element in this list
     * @return the first instance of an element equal to the one passed in
     * @throws ElementNotFoundException if no element was found equal to the element passed in
     */
    @Throws(ElementNotFoundException::class)
    override operator fun get(element: E): E {
        val found = list.find { it == element }
        if (found == null) {
            throw ElementNotFoundException("$element")
        } else
            return found
    }

    /**
     * Checks to see if this list contains an element equal to the passed in element, returns true if this list
     * contains an element equal to the passed in element and false otherwise.
     *
     * ## No Override Recommended:
     * No Override is recommended since this implementation is just taken from the ArrayList's contains method
     * [java.util.ArrayList.contains]
     *
     * @see java.util.ArrayList.contains
     * @param element the element to check if it has an equal contained in this list
     * @return true if this list contains an element equal to the passed in element and false otherwise
     */
    @NoOverrideRecommended
    override operator fun contains(element: E) = list.contains(element)

    //endregion Operators

    //region Add

    /**
     * Adds the passed in element to the end of this list, this list's [nextIndex].
     *
     * @see java.util.ArrayList.add
     * @param element the element to add to this list
     * @return this list after adding the element
     */
    override fun add(element: E): WaqtiCollection<E> {
        list.add(element)
        return this
    }

    /**
     * Adds the passed in element at the passed in index, this index must be within the bounds otherwise an
     * [IndexOutOfBoundsException] will be thrown.
     *
     * When an element is added to the middle of a list, ie the list already contains an element at the passed in
     * index then the list will shift to accommodate its new element, for example if `C` is added to the list
     * `(A, B, D, E)` at index 2 then the list will become `(A, B, C, D, E)`, all elements at and after index 2 will
     * be shifted to the right to allow for element `C` to be at index 2.
     *
     * @see java.util.ArrayList.add
     * @param index the index at which the element will be added
     * @param element the element to be added at the index
     * @return this list after adding the element at the index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    @Throws(IndexOutOfBoundsException::class)
    override fun addAt(index: Int, element: E): WaqtiList<E> {
        if (index < 0 || index > nextIndex) {
            throw  IndexOutOfBoundsException("Cannot add $element at index $index, limits are 0 to $nextIndex")
        } else {
            list.add(index, element)
            return this
        }
    }

    /**
     * Adds all the passed in elements to this list.
     *
     * @see java.util.ArrayList.addAll
     * @param elements the elements to be added to this list, can be empty
     * @return this list after adding all the elements
     */
    override fun addAll(vararg elements: E) = addAll(elements.toList())

    /**
     * Adds all the elements of the passed [Collection] to this list.
     *
     * @see java.util.ArrayList.addAll
     * @param collection the [Collection] of elements to be added to this list, can be empty
     * @return this list after adding all the elements of the collection
     */
    override fun addAll(collection: Collection<E>): WaqtiCollection<E> {
        list.addAll(collection)
        return this
    }

    /**
     * Adds all the passed in elements to this list at the passed in index in similar fashion to [addAt] making
     * elements already at and after the index shift right.
     *
     * @see java.util.ArrayList.addAll
     * @param index the index at which to add the elements
     * @param elements the elements to add to this list at the passed in index, can be empty
     * @return this list after adding the elements at the index
     * @throws IndexOutOfBoundsException if the passed in index is out of bounds
     */
    @Throws(IndexOutOfBoundsException::class)
    override fun addAllAt(index: Int, vararg elements: E) = addAllAt(index, elements.toList())

    /**
     * Adds all the passed in collection's elements to this list at the passed in index in similar fashion to [addAt]
     * making elements already at and after the index shift right.
     *
     * @see java.util.ArrayList.addAll
     * @param index the index at which to add the elements
     * @param collection the collection of elements to add to this list at the passed in index, can be empty
     * @return this list after adding the elements at the index
     * @throws IndexOutOfBoundsException if the passed in index is out of bounds
     */
    @Throws(IndexOutOfBoundsException::class)
    override fun addAllAt(index: Int, collection: Collection<E>): WaqtiList<E> {
        if (index < 0 || index > nextIndex) {
            throw  IndexOutOfBoundsException("Cannot add $collection at index $index, limits are 0 to $nextIndex")
        } else {
            list.addAll(index, collection)
            return this
        }
    }

    /**
     * Adds elements from the passed in collection to this list if they satisfy the passed in predicate.
     *
     * @see Iterable.filter
     * @param collection the [Collection] from which to take the elements to be filtered and added to this list, can
     * be empty
     * @param predicate the predicate that the elements in the passed in collection must satisfy in order to be added
     * to this list
     * @return this list after adding the elements filtered from the collection
     */
    override fun addIf(collection: Collection<E>, predicate: (E) -> Boolean): WaqtiCollection<E> {
        this.addAll(collection.filter(predicate))
        return this
    }

    //endregion Add

    //region Update

    /**
     * Updates the first instance of an element equal to the passed in `old` element to be the `new` element.
     *
     * ## No Override Recommended:
     * No override is recommended since this is just done using the [updateAt] method, override that instead
     *
     * @param old the element to find to be updated
     * @param new the value the old element will be updated to
     * @return this list after updating the element
     * @throws ElementNotFoundException if no instance of an element equal to the `old` element can be found see [indexOf]
     */
    @NoOverrideRecommended
    @Throws(ElementNotFoundException::class)
    override fun update(old: E, new: E) = updateAt(indexOf(old), new)

    /**
     * Updates the element at the passed in index to be the `new` element.
     *
     *
     * @param oldIndex the index of the element to be updated
     * @param newElement the value the old element will be updated to
     * @return this list after updating the element
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    @Throws(IndexOutOfBoundsException::class)
    override fun updateAt(oldIndex: Int, newElement: E): WaqtiList<E> {
        if (oldIndex < 0 || oldIndex > nextIndex) {
            throw  IndexOutOfBoundsException("Cannot update to $newElement at index $oldIndex, limits are 0 to $nextIndex")
        } else {
            this.removeAt(oldIndex)
            this.addAt(oldIndex, newElement)
            return this
        }
    }

    /**
     * Updates all instances in this list that are equal to any elements in the passed in collection to all become
     * the passed in `new` element.
     *
     * For example:
     * `updateAllTo(listOf("A", "B"), "X")` done to a list `(A, B, C, A, D, X)` will result in list
     * `(X, X, C, X, D, X)`, any instances of `A` or `B` were updated to become `X`.
     *
     * @param collection the collection of elements to check that this list contains them to be updated to the new
     * element
     * @param new the value that all the elements equal to any of the elements in the passed in collection will be
     * updated to
     * @return this list after updating
     */
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

    /**
     * Updates all instances in this list that satisfy the passed in predicate to all become the passed in `new`
     * element.
     *
     * ## No Override Recommended:
     * No override is recommended since all this function does is filter all elements in this list that satisfy the
     * given predicate and pass them in as the `collection` in [updateAllTo], override that instead if needed
     *
     * @see updateAllTo
     * @param predicate the predicate that will be used to filter the elements that are to be updated to the new value
     * @param new the value that all the elements equal to any of the elements in the passed in collection will be
     * updated to
     * @return this list after updating
     */
    @NoOverrideRecommended
    override fun updateIf(predicate: (E) -> Boolean, new: E) = updateAllTo(this.filter(predicate), new)

    //endregion Update

    //region Remove

    /**
     * Removes the first instance of an element equal to the passed in element, if none are found the list is unchanged.
     *
     * To remove all instances of an element use [removeAll] and pass in a single element.
     *
     * @see java.util.ArrayList.remove
     * @param element the element to find the first occurrence equal to it to remove from this list
     * @return this list after removing the first instance of an element equal to the passed in element
     */
    override fun removeFirst(element: E): WaqtiCollection<E> {
        list.remove(element)
        return this
    }

    /**
     * Removes the element at the passed in index, if the index is out of bounds throws an [IndexOutOfBoundsException].
     *
     * @param index the index of the element to remove from this list
     * @return this list after removing the element at the passed in index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    @Throws(IndexOutOfBoundsException::class)
    override fun removeAt(index: Int): WaqtiList<E> {
        if (index < 0 || index > nextIndex) {
            throw  IndexOutOfBoundsException("Cannot remove at index $index, limits are 0 to $nextIndex")
        } else {
            list.removeAt(index)
            return this
        }
    }

    /**
     * Removes all instances equal to any of the passed in elements, if no elements are passed in the list will be
     * unchanged, if there are no instances equal to a passed in element then no action will be taken for them, see
     * [java.util.ArrayList.removeAll]
     *
     * @see java.util.ArrayList.removeAll
     * @param elements the elements that all instances that are equal to any of them will be removed
     * @return this list after removing all the elements
     */
    override fun removeAll(vararg elements: E) = removeAll(elements.toList())

    /**
     * Removes all instances equal to any of the passed in collection's elements, if the collection is empty are
     * passed the list will be unchanged, if there are no instances equal to a passed in element then no action will
     * be taken for them, see [java.util.ArrayList.removeAll]
     *
     * @see java.util.ArrayList.removeAll
     * @param collection the collection of elements that all instances that are equal to any of them will be removed
     * @return this list after removing all the elements
     */
    override fun removeAll(collection: Collection<E>): WaqtiCollection<E> {
        list.removeAll(collection)
        return this
    }

    /**
     * Removes any elements in this list that satisfy the passed in predicate, if none satisfy, this list will be
     * unchanged.
     *
     * @see java.util.ArrayList.removeIf
     * @param predicate the predicate that must be satisfied in order for elements to be removed from this list
     * @return this list after removing the elements that satisfy the predicate
     */
    override fun removeIf(predicate: (E) -> Boolean): WaqtiCollection<E> {
        list.removeIf(predicate)
        return this
    }

    /**
     * Clears this list, removing all elements in it making it empty.
     *
     * @return this list after being cleared
     */
    override fun clear(): WaqtiCollection<E> {
        list.clear()
        return this
    }

    /**
     * Removes the elements from the passed in `fromIndex` inclusive to the passed in `toIndex` exclusive, if both
     * are equal this list will be unchanged, if `fromIndex` is greater than `toIndex` then the operation still
     * proceeds unlike [java.util.ArrayList.subList], see [subList]
     *
     * @see subList
     * @param fromIndex the index inclusive to start the range to remove
     * @param toIndex the index exclusive to end the range to remove
     * @return this list after removing the range of elements
     * @throws IndexOutOfBoundsException if either indexes are out of bounds
     */
    @Throws(IndexOutOfBoundsException::class)
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
        return when {
            fromIndex < 0 || toIndex < 0 || fromIndex > nextIndex || toIndex > nextIndex -> {
                throw  IndexOutOfBoundsException("Cannot SubList $fromIndex to $toIndex, limits are 0 and $nextIndex")
            }
            fromIndex > toIndex -> list.subList(toIndex + 1, fromIndex + 1)
            fromIndex < toIndex -> list.subList(fromIndex, toIndex)
            else -> emptyList()
        }
    }

    @NoOverrideRecommended
    override fun countOf(element: E) = this.count { it == element }

    @NoOverrideRecommended
    override fun containsAny(predicate: (E) -> Boolean) = this.any(predicate)

    //endregion Query

    //region Manipulate

    override fun move(fromIndex: Int, toIndex: Int): WaqtiList<E> {
        return when {
            toIndex > size - 1 || toIndex < 0 || fromIndex > size - 1 || fromIndex < 0 -> {
                throw  IndexOutOfBoundsException("Cannot move $fromIndex  to $toIndex, limits are 0 and $nextIndex")
            }
            fromIndex < toIndex -> {
                val itemToMove = list[fromIndex]
                for ((i, element) in list.filter { list.indexOf(it) in (fromIndex + 1)..toIndex }.withIndex()) {
                    list[fromIndex + i] = element
                }
                list[toIndex] = itemToMove
                this
            }
            fromIndex > toIndex -> {
                val itemToMove = list[fromIndex]
                for ((i, element) in list.filter { list.indexOf(it) in toIndex..(fromIndex - 1) }.asReversed().withIndex()) {
                    list[fromIndex - i] = element
                }
                list[toIndex] = itemToMove
                this
            }
            else -> this
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

    override fun moveAllTo(toIndex: Int, vararg elements: E) = moveAllTo(elements.toList(), toIndex)

    override fun moveAllTo(collection: Collection<E>, toIndex: Int): WaqtiList<E> {
        val found = this.getAll(collection)
        when {
            toIndex < 0 || toIndex > nextIndex -> {
                throw IndexOutOfBoundsException("Cannot move to $toIndex, limits are 0 to $nextIndex")
            }
            found.isNotEmpty() -> {
                this.removeAll(found)
                this.addAllAt(toIndex, found)
            }
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

    // Can never be called from Kotlin see kotlin.collections.Iterable.forEach
    @NoOverrideRecommended
    override fun forEach(action: Consumer<in E>?) = list.forEach(action)

    @OverrideRecommended
    override fun join(collection: Collection<E>): WaqtiCollection<E> {
        this.addAll(collection)
        return this
    }

    //endregion List Utils

    //region Companion

    companion object {

        fun <E> moveElements(listFrom: AbstractWaqtiList<E>, listTo: AbstractWaqtiList<E>,
                             elements: Collection<E>, toIndex: Int = listTo.nextIndex) {
            val found = listFrom.getAll(elements)
            when {
                toIndex < 0 || toIndex > listTo.nextIndex -> {
                    throw IndexOutOfBoundsException("Cannot move to $toIndex, limits are 0 and ${listTo.nextIndex}")
                }
                found.isNotEmpty() && listFrom !== listTo -> {
                    listFrom.removeAll(found)
                    listTo.addAllAt(toIndex, found)
                }
            }
        }

        fun <E> copyElements(listFrom: AbstractWaqtiList<E>, listTo: AbstractWaqtiList<E>,
                             elements: Collection<E>, toIndex: Int = listTo.nextIndex) {
            val found = listFrom.getAll(elements)
            when {
                toIndex < 0 || toIndex > listTo.nextIndex -> {
                    throw IndexOutOfBoundsException("Cannot move to $toIndex, limits are 0 and ${listTo.nextIndex}")
                }
                found.isNotEmpty() && listFrom !== listTo -> {
                    listTo.addAllAt(toIndex, found)
                }
            }
        }

        fun <E> swapElements(listLeft: AbstractWaqtiList<E>, listRight: AbstractWaqtiList<E>,
                             elementsLeft: Collection<E>, elementsRight: Collection<E>,
                             indexIntoLeft: Int = listLeft.nextIndex, indexIntoRight: Int = listRight.nextIndex) {

            val foundLeft = listLeft.getAll(elementsLeft)
            val foundRight = listRight.getAll(elementsRight)

            if (listLeft !== listRight) {
                when {
                    indexIntoLeft < 0 ||
                            indexIntoRight < 0 ||
                            indexIntoLeft > listLeft.nextIndex ||
                            indexIntoRight > listRight.nextIndex -> {
                        throw IndexOutOfBoundsException("Cannot swap $indexIntoLeft  and $indexIntoRight," +
                                " limits are ${listLeft.nextIndex} and ${listRight.nextIndex} respectively")
                    }

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
        }

        fun <E> join(intoList: AbstractWaqtiList<E>, thisList: AbstractWaqtiList<E>) =
                ArrayList<E>(intoList + thisList).toList()

        fun <E> intersection(thisList: AbstractWaqtiList<E>, thatList: AbstractWaqtiList<E>) =
                ArrayList<E>(thisList + thatList).filter { it in thisList && it in thatList }.toList()

        fun <E> intersectionDistinct(thisList: AbstractWaqtiList<E>, thatList: AbstractWaqtiList<E>) =
                ArrayList<E>(thisList + thatList).filter { it in thisList && it in thatList }.distinct().toList()

        fun <E> difference(inThis: AbstractWaqtiList<E>, notInThis: AbstractWaqtiList<E>) =
                ArrayList<E>(inThis + notInThis).filter { it in inThis && it !in notInThis }.toList()

        fun <E> differenceDistinct(inThis: AbstractWaqtiList<E>, notInThis: AbstractWaqtiList<E>) =
                ArrayList<E>(inThis + notInThis).filter { it in inThis && it !in notInThis }.distinct().toList()

        fun <E> union(vararg lists: AbstractWaqtiList<E>): List<E> {
            val result = ArrayList<E>(lists.size)
            for (list in lists) {
                for (element in list) {
                    result.add(element)
                }
            }
            return result.toList()
        }

        fun <E> unionDistinct(vararg lists: AbstractWaqtiList<E>): List<E> {
            val result = ArrayList<E>(lists.size)
            for (list in lists) {
                for (element in list) {
                    if (element !in result) result.add(element)
                }
            }
            return result.toList()
        }

    }

    //endregion Companion

    //region Overriden from kotlin.Any

    override fun hashCode() = this.toList().hashCode()

    override fun equals(other: Any?) =
            other is WaqtiCollection<*> && this.toList() == other.toList()

    override fun toString() = this.toList().toString()

    //endregion Overriden from kotlin.Any
}