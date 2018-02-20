package uk.whitecrescent.waqti.code

class Priority private constructor(var name: String) {

    companion object {

        var allPriorities = ArrayList<Priority>()

        fun createNewPriority(name: String): Priority {
            val newPriority = Priority(name)
            if (allPriorities.find { it.equals(newPriority) } == null) {
                allPriorities.add(newPriority)
                return newPriority
            } else return allPriorities.find { it.equals(newPriority) }!!
        }

        fun getPriorityByName(name: String): Priority {
            if (allPriorities.find { it.name.equals(name) } == null) {
                throw IllegalArgumentException("Does not exist!")
            } else return allPriorities.find { it.name.equals(name) }!!
        }

        fun deletePriority(name: String) {
            allPriorities.remove(getPriorityByName(name))
        }
    }

    override fun hashCode() = name.hashCode()

    override fun equals(other: Any?) =
            other is Priority && other.name.equals(this.name)

    override fun toString() = name

}