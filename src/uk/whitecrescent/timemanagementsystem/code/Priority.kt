package uk.whitecrescent.timemanagementsystem.code

class Priority private constructor(var name: String) {

    companion object {

        var allPriorities = ArrayList<Priority>()

        fun createNewPriority(newName: String): Priority {

            val newPriority = Priority(newName)

            for (current in allPriorities) {
                if (current.equals(newPriority)) {
                    return current
                }
            }

            allPriorities.add(newPriority)
            return newPriority
        }

        fun getPriorityByName(name: String) : Priority{
            for (priority in allPriorities) {
                if (priority.name.equals(name)) {
                    return priority
                }
            }
            throw IllegalArgumentException("Does not exist!")
        }

    }

    override fun hashCode(): Int {
        return this.name.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other is Priority && other.name.equals(this.name)
    }

}