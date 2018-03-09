package uk.whitecrescent.waqti.code

class Priority private constructor(var name: String, var importanceLevel: Int) {

    companion object {

        val allPriorities = ArrayList<Priority>()

        fun getOrCreatePriority(name: String, importanceLevel: Int): Priority {
            val newPriority = Priority(name, importanceLevel)
            val found = allPriorities.find { it == newPriority }

            if (found == null) {
                allPriorities.add(newPriority)
                return newPriority
            } else return found
        }

        fun getPriority(name: String, importanceLevel: Int): Priority {
            val newPriority = Priority(name, importanceLevel)
            val found = allPriorities.find { it == newPriority }

            if (found == null) {
                throw IllegalArgumentException("Priority not found")
            } else return found
        }

        fun deletePriority(name: String, importanceLevel: Int) {
            allPriorities.remove(getPriority(name, importanceLevel))
        }
    }

    override fun hashCode() = name.hashCode() + importanceLevel

    override fun equals(other: Any?) =
            other is Priority &&
                    other.name == this.name &&
                    other.importanceLevel == this.importanceLevel

    override fun toString() = "$name $importanceLevel"

}