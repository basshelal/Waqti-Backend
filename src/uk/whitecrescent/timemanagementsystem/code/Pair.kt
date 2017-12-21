package uk.whitecrescent.timemanagementsystem.code

class Pair<V>(var visibility: Boolean, var value: V) {

    fun value(): V {
        return value
    }

    fun isVisible(): Boolean {
        return visibility
    }

}