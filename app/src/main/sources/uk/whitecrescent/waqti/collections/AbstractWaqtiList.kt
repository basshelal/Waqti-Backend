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
    @OverrideRecommended
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

    /**
     * Gets all the elements in this list that are equal to any of the elements passed in, if none are passed in
     * nothing is returned, if elements are passed in that this list does not contain instances that are equal to
     * them then no action is taken for them and they will not appear in the returned list. The returned list can
     * contain duplicates.
     *
     * This operation does not modify this list, it only queries this list.
     *
     * @param elements the elements to check that this list contains elements that are equal to any of them
     * @return the list of elements that this list contains that are from the passed in elements
     */
    override fun getAll(vararg elements: E) = getAll(elements.toList())

    /**
     * Gets all the elements in this list that are equal to any of the elements of the collection passed in, if the
     * collection is empty nothing is returned, if the collection contains elements that this list does not contain
     * instances that are equal to them then no action is taken for them and they will not appear in the returned
     * list. The returned list can contain duplicates.
     *
     * This operation does not modify this list, it only queries this list.
     *
     * @param collection the collection of elements to check that this list contains elements that are equal to any of
     * them
     * @return the list of elements that this list contains that are from the passed in collection of elements
     */
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

    /**
     * Returns this list as a read only kotlin [List]
     *
     * This operation does not modify this list, it only queries this list.
     *
     * @see List
     * @return this list as a read only kotlin [List]
     */
    @NoOverrideRecommended
    override fun toList() = list.toList()

    /**
     * Checks whether this list contains *all* of the passed in elements, returns true if every element passed in
     * contains at least an instance equal to it and false otherwise, if no elements are passed in will always return
     * true.
     *
     * @see java.util.ArrayList.containsAll
     * @param elements the elements to check that they are contained in this list
     * @return true if the elements passed in have at least a single instance that is equal to them and false otherwise
     */
    override fun containsAll(vararg elements: E) = this.containsAll(elements.toList())

    /**
     * Checks whether this list contains *all* of the passed in collection's elements, returns true if every element
     * in the passed collection contains at least an instance equal to it and false otherwise, if the passed in
     * collection is empty then will always return true.
     *
     * @see java.util.ArrayList.containsAll
     * @param elements the collection of elements to check that they are contained in this list
     * @return true if the collection of elements passed in have at least a single instance that is equal to them and
     * false otherwise

     */
    override fun containsAll(elements: Collection<E>) = list.containsAll(elements)

    /**
     * Checks whether this list is empty, returns true if this list contains no elements and false otherwise.
     *
     * ## No Override Recommended:
     * No Override is recommended since this implementation just uses the ArrayList's [java.util.ArrayList.isEmpty]
     * method.
     *
     * @see java.util.ArrayList.isEmpty
     * @return true if this list is empty and false if it is not
     */
    @NoOverrideRecommended
    override fun isEmpty() = list.isEmpty()

    /**
     * Returns the index of the first instance of an element equal to the passed in element, if no such instance is
     * found will throw an [ElementNotFoundException]. This differs from [java.util.ArrayList.indexOf] which returns
     * -1 if the element is not found.
     *
     * @see java.util.ArrayList.indexOf
     * @param element the element to search for instances equal to it
     * @return the index of the first instance of an element equal to the passed in element
     * @throws ElementNotFoundException if no instance is found that is equal to the passed in element
     */
    @NoOverrideRecommended
    @Throws(ElementNotFoundException::class)
    override fun indexOf(element: E): Int {
        if (list.indexOf(element) == -1) {
            throw ElementNotFoundException("$element")
        } else return list.indexOf(element)
    }

    /**
     * Returns the index of the last instance of an element equal to the passed in element, if no such instance is
     * found will throw an [ElementNotFoundException]. This differs from [java.util.ArrayList.lastIndexOf] which returns
     * -1 if the element is not found.
     *
     * @see java.util.ArrayList.lastIndexOf
     * @param element the element to search for instances equal to it
     * @return the index of the last instance of an element equal to the passed in element
     * @throws ElementNotFoundException if no instance is found that is equal to the passed in element
     */
    @NoOverrideRecommended
    override fun lastIndexOf(element: E): Int {
        if (list.lastIndexOf(element) == -1) {
            throw ElementNotFoundException("$element")
        } else return list.lastIndexOf(element)
    }

    /**
     * Gets a list of all the indexes of instances of elements that are equal to the passed in element, if this
     * list does not contain any instances that are equal to the passed in element the returned read only kotlin
     * [List] will be empty.
     *
     * @param element the element to search for all indexes of instances that are equal to it
     * @return the list of integers representing the list of indexes of instances of elements in this list that are
     * equal to the passed in element
     */
    @NoOverrideRecommended
    override fun allIndexesOf(element: E): List<Int> {
        val result = ArrayList<Int>()
        this.forEachIndexed { index, it -> if (it == element) result.add(index) }
        return result.toList()
    }

    /**
     * Gets the elements from the passed in `fromIndex` inclusive to the passed in `toIndex` exclusive, if both
     * are equal the returned list will be empty, if `fromIndex` is greater than `toIndex` then the operation still
     * proceeds unlike [java.util.ArrayList.subList], the returned list will still be `fromIndex` inclusive and
     * `toIndex` exclusive and show that portion of this list.
     *
     * This operation does not modify this list, it only queries this list.
     *
     * @see java.util.ArrayList.subList
     * @param fromIndex the index inclusive to start the sublist
     * @param toIndex the index exclusive to end the sublist
     * @return the sublist from the `fromIndex` inclusive to the `toIndex` exclusive
     * @throws IndexOutOfBoundsException if either indexes are out of bounds
     */
    @NoOverrideRecommended
    @Throws(IndexOutOfBoundsException::class)
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

    /**
     * Gets the number of times the passed in element occurs in this list, if it does not occur any times will return
     * zero, this should be the same as the size of the list returned by [allIndexesOf].
     *
     * @param element the element to check the number of times it occurs in this list
     * @return the number of times the passed in element occurs in this list
     */
    @NoOverrideRecommended
    override fun countOf(element: E) = this.count { it == element }

    /**
     * Checks whether this list contains any elements that satisfy the passed in predicate, returns true if
     * there exists at least one element that satisfies the predicate and false if none do.
     *
     * @see any
     * @param predicate the predicate to check whether any elements in this list satisfy it or not
     * @return true if at least one satisfies the passed in predicate and false if none do
     */
    @NoOverrideRecommended
    override fun containsAny(predicate: (E) -> Boolean) = this.any(predicate)

    //endregion Query

    //region Manipulate

    /**
     * Moves the element at the passed in `fromIndex` to the passed in `toIndex`, if they are out of bounds an
     * [IndexOutOfBoundsException] will be thrown. The list will accommodate to the movements as follows.
     *
     * `move(3,5)` in the list
     * `(A, B, C, D, E, F, G)` will result in the list
     * `(A, B, C, E, F, D, G)`.
     *
     * @param fromIndex the index of the element to move
     * @param toIndex the index that the element to move will be moved to
     * @return this list after moving the element
     * @throws IndexOutOfBoundsException if either indexes are out of bounds
     */
    @Throws(IndexOutOfBoundsException::class)
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

    /**
     * Moves the first instance of an element in this list equal to the passed in element `from` to the
     * the first instance of an element in this list list equal to the passed in element `to` , if
     * either of them cannot be found then an [ElementNotFoundException] will be thrown. The list will accommodate to
     * the movements as follows.
     *
     * `move(D,F)` in the list
     * `(A, B, C, D, E, F, G)` will result in the list
     * `(A, B, C, E, F, D, G, F)`.
     *
     * @see indexOf
     * @param from the element to move its first instance in this list
     * @param to the element to use its index to move the element to be moved to
     * @return this list after moving the element
     * @throws ElementNotFoundException if either element does not have instances equal to it in this list
     */
    @NoOverrideRecommended
    @Throws(ElementNotFoundException::class)
    override fun move(from: E, to: E) = move(indexOf(from), indexOf(to))

    /**
     * Swaps the element at the passed in `thisIndex` to the element at the passed in `thatIndex`, effectively
     * switching their places.
     *
     * @param thisIndex the index of the element to switch place with the other element
     * @param thatIndex the index of the element to switch place with the other element
     * @return this list after swapping the elements at the passed in indexes
     * @throws IndexOutOfBoundsException if either index is out of bounds
     */
    @Throws(IndexOutOfBoundsException::class)
    override fun swap(thisIndex: Int, thatIndex: Int): WaqtiList<E> {
        when {
            thisIndex < 0 || thisIndex > nextIndex || thatIndex < 0 || thatIndex > nextIndex -> {
                throw IndexOutOfBoundsException("Cannot swap $thisIndex with $thatIndex, limits are 0 to $nextIndex")
            }
            else -> {
                val `this` = this[thisIndex]
                val that = this[thatIndex]
                this.updateAt(thatIndex, `this`)
                this.updateAt(thisIndex, that)
                return this
            }
        }
    }

    /**
     * Swaps the element at the index of the first instance of the passed in ``this`` to the index of the first
     * instance of the passed in `that`, effectively switching their places.
     *
     * @param `this` the element to get the index of the element to switch place with the other element
     * @param that the element to get the index of the element to switch place with the other element
     * @return this list after swapping the elements at the passed in indexes
     * @throws ElementNotFoundException if either element does not have an instance equal to it in this list
     */
    @NoOverrideRecommended
    @Throws(ElementNotFoundException::class)
    override fun swap(`this`: E, that: E) = swap(indexOf(`this`), indexOf(that))

    /**
     * Moves all instances in this list that are equal to any of the passed in elements to the passed in index, if
     * none are found this list is unchanged.
     *
     * @param toIndex the index to move all the elements to
     * @param elements the elements to find instances in this list that are equal to any to be moved to the index
     * @return this list after moving all elements
     * @throws IndexOutOfBoundsException if the index passed in is out of bounds
     */
    override fun moveAllTo(toIndex: Int, vararg elements: E) = moveAllTo(elements.toList(), toIndex)

    /**
     * Moves all instances in this list that are equal to any of the passed in collection's elements to the passed in
     * index, if none are found this list is unchanged.
     *
     * @param collection the collection od elements to find instances in this list that are equal to any to be moved to
     * the index
     * @param toIndex the index to move all the elements to
     * @return this list after moving all elements
     * @throws IndexOutOfBoundsException if the index passed in is out of bounds
     */
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

    /**
     * Sorts the elements of this list with the passed in [java.util.Comparator].
     *
     * @see java.util.Comparator
     * @see MutableList.sortWith
     * @param comparator the [java.util.Comparator] to use to sort the elements in this list
     * @return this list after sorting the elements in it
     */
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