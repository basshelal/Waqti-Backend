package uk.whitecrescent.timemanagementsystem.code

import java.time.LocalDateTime

class Task : Killable, Failable {

    override var isFailable = false
    override var isKillable = true

    var title = TaskTitle("", this)
    var time = TaskTime(LocalDateTime.of(1996, 6, 15, 14, 30, 10), this)

    var state: TaskState = TaskState.UNBORN

    fun changeStateToExisting() {
        this.state = TaskState.EXISTING
    }

    private fun changeStateTo(state: TaskState) {
        this.state = state
    }

}

