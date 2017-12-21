package uk.whitecrescent.timemanagementsystem.code

class CheckList : ArrayList<ListItem>(){

    fun checkItemAt(index: Int) {
        this.get(index).isChecked = true
    }

}