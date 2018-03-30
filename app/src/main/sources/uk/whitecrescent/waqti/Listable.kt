package uk.whitecrescent.waqti

interface Listable {

    fun getAll(): List<Listable>
}