package uk.whitecrescent.timemanagementsystem.code

class CheckList(vararg itemValues: String) {

    private val list = ArrayList<ListItem>()

    init {
        addAll(*itemValues)
    }

    fun size(): Int {
        return list.size
    }

    fun addItem(listItem: ListItem) {
        this.list.add(listItem)
    }

    fun addItem(listItemValue: String) {
        this.list.add(ListItem(listItemValue))
    }

    fun addAll(vararg listItems: ListItem) {
        this.list.addAll(listItems)
    }

    fun addAll(vararg listItemValues: String) {
        for (itemValue in listItemValues) {
            addItem(itemValue)
        }
    }

    fun asList(): List<ListItem> {
        return this.list
    }

    fun get(index: Int): ListItem {
        return this.list[index]
    }

    fun moveItem(fromIndex: Int, toIndex: Int) {
        if (toIndex > size() || toIndex < 0 || fromIndex > size() || fromIndex < 0) {
            throw IllegalArgumentException("Index doesn't exist!")
        }
        if (fromIndex == toIndex) {
            return
        }

        val itemToMove = list[fromIndex]

        if (fromIndex < toIndex) {
            for ((i, element) in list.filter { listItem -> list.indexOf(listItem) in (fromIndex + 1)..toIndex }.withIndex()) {
                list[fromIndex + i] = element
            }
            list[toIndex] = itemToMove
        } else if (fromIndex > toIndex) {
            for ((i, element) in list.filter { listItem -> list.indexOf(listItem) in toIndex..(fromIndex - 1) }.asReversed().withIndex()) {
                list[fromIndex - i] = element
            }
            list[toIndex] = itemToMove
        }


        // everything after fromIndex push left by 1 and everything after toIndex don't move, just like everything before fromIndex
        // basically only manipulate what's between fromIndex and toIndex

    }

    fun checkItem(index: Int) {
        this.list[index].isChecked = true
    }

    fun checkItem(item: ListItem) {
        getItemByReference(item)?.isChecked = true
    }

    fun uncheckItem(index: Int) {
        this.list.get(index).isChecked = false
    }

    fun uncheckItem(item: ListItem) {
        getItemByReference(item)?.isChecked = false
    }

    private fun getItemByReference(item: ListItem) = this.list.find { element -> item.equals(element) }

    private fun getItemByContents(value: String) = this.list.find { element -> element.value.equals(value) }

    private fun getAllCheckedItems() = this.list.filter { it.isChecked }

    private fun getAllUncheckedItems() = this.list.filterNot { it.isChecked }

    override fun hashCode() = list.hashCode()

    override fun equals(other: Any?) =
            other is CheckList && this.toString().equals(other.toString())

    override fun toString() = list.toString()

}

data class ListItem(val value: String, var isChecked: Boolean = false)