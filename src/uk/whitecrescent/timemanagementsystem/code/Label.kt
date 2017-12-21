package uk.whitecrescent.timemanagementsystem.code

class Label private constructor(var name: String) {

    companion object {

        var allLabels = ArrayList<Label>()

        fun createNewLabel(newName: String): Label {

            val newLabel = Label(newName)

            for (current in allLabels) {
                if (current.equals(newLabel)) {
                    return current
                }
            }

            allLabels.add(newLabel)
            return newLabel
        }

        fun getLabelByName(name: String) : Label{
            for (label in allLabels) {
                if (label.name.equals(name)) {
                    return label
                }
            }
            throw IllegalArgumentException("Does not exist!")
        }
    }

    override fun hashCode(): Int {
        return this.name.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other is Label && other.name.equals(this.name)
    }

}