package uk.whitecrescent.waqti

import uk.whitecrescent.waqti.collections.Tuple
import uk.whitecrescent.waqti.task.GRACE_PERIOD
import uk.whitecrescent.waqti.task.ID
import uk.whitecrescent.waqti.task.Task

fun sleep(seconds: Int) = Thread.sleep((seconds) * 1000L)

fun <T> logD(t: T) {
    println("DEBUG: ${t.toString()}")
}

fun <T> logI(t: T) {
    println("INFO: ${t.toString()}")
}

fun <T> logE(t: T) {
    error("ERROR: ${t.toString()}")
}

fun setGracePeriod(duration: Duration) {
    GRACE_PERIOD = duration
}

// Extensions

fun Collection<Task>.taskIDs(): List<ID> {
    val ids = ArrayList<ID>(this.size)
    this.forEach { ids.add(it.taskID) }
    return ids
}

val <E> List<E>.toArrayList: ArrayList<E>
    get() {
        return ArrayList(this)
    }

val Collection<Cacheable>.ids: List<ID>
    get() = this.map { it.id() }

val Collection<ID>.tasks: List<Task>
    get() = Cache.getTasks(this)

val Collection<Tuple>.tasks: Array<Task>
    get() {
        val result = ArrayList<Task>(this.size)
        for (tuple in this) {
            result.addAll(tuple.toList())
        }
        return result.toTypedArray()
    }

fun Collection<Task>.putAll() {
    this.forEach { Cache.putTask(it) }
}
