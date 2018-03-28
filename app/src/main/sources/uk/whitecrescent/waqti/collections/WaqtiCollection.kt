package uk.whitecrescent.waqti.collections

interface WaqtiCollection<E> : Collection<E> {

    operator fun get(index: Int): E

    operator fun get(element: E): E?

    operator fun set(index: Int, element: E)

    fun addAll(vararg elements: E)

    fun addAll(collection: Collection<E>)

    fun add(element: E)

    fun addAt(index: Int, element: E)

    fun remove(element: E)

    fun removeAt(index: Int)

    fun clear()

    fun sort(): WaqtiCollection<E>

    fun toList(): List<E>
}