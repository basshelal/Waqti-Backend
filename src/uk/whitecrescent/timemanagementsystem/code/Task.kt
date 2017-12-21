package uk.whitecrescent.timemanagementsystem.code

import java.time.LocalDateTime

class Task(var title: String) : Killable, Failable {

    /* Optional Properties */
    var time = Pair(HIDDEN, DEFAULT_TIME)
    var duration = Pair(HIDDEN, DEFAULT_DURATION)
    var priority = Pair(HIDDEN, DEFAULT_PRIORITY)
    var label = Pair(HIDDEN, DEFAULT_LABEL)
    var optional = Pair(HIDDEN, DEFAULT_OPTIONAL)
    var description: StringBuilder? = null
    var checkList: CheckList? = null
    var before: Task? = null
    var after: Task? = null
    var deadline: LocalDateTime? = null
    var target: Target? = null
    var state: TaskState? = null

    override var isFailable = false
    override var isKillable = true

    override fun fail() {}

    override fun kill() {}

    fun changeStateToExisting() {
        changeStateTo(TaskState.EXISTING)
    }

    private fun changeStateTo(state: TaskState) {
        this.state = state
    }

}

