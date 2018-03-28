package uk.whitecrescent.waqti.collections

interface WaqtiCollection<E> : Collection<E> {

    operator fun set(index: Int, element: E) = addAt(index, element)

    operator fun plus(element: E) = add(element)

    operator fun minus(element: E) = remove(element)

    operator fun get(index: Int): E

    operator fun get(element: E): E?

    fun add(element: E)

    fun addAt(index: Int, element: E)

    fun addAll(vararg elements: E)

    fun addAll(collection: Collection<E>)

    fun remove(element: E)

    fun removeAt(index: Int)

    fun move(fromIndex: Int, toIndex: Int)

    fun clear()

    fun merge(waqtiCollection: WaqtiCollection<E>): WaqtiCollection<E>

    fun getList(): List<E>
}