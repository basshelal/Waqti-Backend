package uk.whitecrescent.waqti

interface Listable {

    fun getAll(): List<Listable>

    fun mergeToList(listable: Listable): List<Listable>
}