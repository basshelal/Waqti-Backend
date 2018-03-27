package uk.whitecrescent.waqti.task

class Label private constructor(var name: String) {

    var children = arrayListOf<Label>()

    companion object {

        val allLabels = ArrayList<Label>()


        fun getOrCreateLabel(name: String): Label {
            val newLabel = Label(name)
            val found = Label.allLabels.find { it == newLabel }

            if (found == null) {
                Label.allLabels.add(newLabel)
                return newLabel
            } else return found
        }

        fun getLabel(name: String): Label {
            val newLabel = Label(name)
            val found = Label.allLabels.find { it == newLabel }

            if (found == null) {
                throw IllegalArgumentException("Label not found")
            } else return found
        }

        fun deleteLabel(name: String) {
            for (child in getLabel(name).children){
                allLabels.remove(child)
            }
            allLabels.remove(getLabel(name))
        }

    }

    override fun hashCode() = name.hashCode()

    override fun equals(other: Any?) =
            other is Label && other.name == this.name

    override fun toString(): String {
        val s = StringBuilder(name)
        if (children.isNotEmpty()) {
            s.append("\n\t$children\n")
        }
        return s.toString()
    }
}