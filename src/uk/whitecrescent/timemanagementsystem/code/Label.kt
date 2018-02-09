package uk.whitecrescent.timemanagementsystem.code

class Label private constructor(var name: String) {

    companion object {

        var allLabels = ArrayList<Label>()

        fun createNewLabel(name: String): Label {
            val newLabel = Label(name)
            if (allLabels.find { it.equals(newLabel) } == null) {
                allLabels.add(newLabel)
                return newLabel
            } else return allLabels.find { it.equals(newLabel) }!!
        }

        fun getLabelByName(name: String): Label {
            if (allLabels.find { it.name.equals(name) } == null) {
                throw IllegalArgumentException("Does not exist!")
            } else return allLabels.find { it.name.equals(name) }!!
        }

        fun deleteLabel(name: String) {
            allLabels.remove(getLabelByName(name))
        }
    }

    override fun hashCode() = name.hashCode()

    override fun equals(other: Any?) =
            other is Label && other.name.equals(this.name)

    override fun toString() = name

}