package uk.whitecrescent.timemanagementsystem.code

class Constraint<V>(override var isVisible: Boolean, override val value: V, var isMet: Boolean) : Property<V>(isVisible, value) {

    companion object {
        fun <T> toProperty(constraint: Constraint<T>) = Property(constraint.isVisible, constraint.value)
    }

    fun toProperty() = Constraint.toProperty(this)

    override fun hashCode() =
            value!!.hashCode() + isVisible.hashCode() + isMet.hashCode()

    override fun equals(other: Any?) =
            other is Constraint<*> && this.value!!.equals(other.value) && this.isVisible == other.isVisible && this.isMet == other.isMet

    override fun toString() =
            """Constraint:
                |   isVisible = $isVisible
                |   value = ${value.toString()}
                |   isMet = $isMet""".trimMargin()
}