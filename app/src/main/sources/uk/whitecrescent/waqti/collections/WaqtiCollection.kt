package uk.whitecrescent.waqti.collections

interface WaqtiCollection<E> : Collection<E> {

    operator fun plus(element: E) = add(element)

    operator fun minus(element: E) = removeElement(element)

    operator fun get(element: E): E?

    fun add(element: E)

    fun addAll(vararg elements: E)

    fun addAll(collection: Collection<E>)

    fun removeElement(element: E)

    fun removeAll(vararg elements: E)

    fun removeAll(collection: Collection<E>)

    fun getAll(vararg elements: E): List<E>

    fun getAll(collection: Collection<E>): List<E>

    fun clear()

    fun sort(comparator: Comparator<E>): WaqtiCollection<E>

    fun toList(): List<E>
}