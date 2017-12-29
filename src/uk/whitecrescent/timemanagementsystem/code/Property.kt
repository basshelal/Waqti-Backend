package uk.whitecrescent.timemanagementsystem.code

open class Property<V>(open var isVisible: Boolean, open val value: V) {

    companion object {
        fun <T> toConstraint(property: Property<T>) = Constraint(property.isVisible, property.value, false)
    }

    fun toConstraint() = Property.toConstraint(this)

    override fun hashCode() =
            value!!.hashCode() + isVisible.hashCode()

    override fun equals(other: Any?) =
            other is Property<*> && this.value!!.equals(other.value) && this.isVisible == other.isVisible

    override fun toString() =
            """Property:
                |   isVisible = $isVisible
                |   value = ${value}""".trimMargin()
}