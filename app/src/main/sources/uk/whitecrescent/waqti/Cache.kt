package uk.whitecrescent.waqti

import uk.whitecrescent.waqti.task.Bundle
import uk.whitecrescent.waqti.task.ID
import uk.whitecrescent.waqti.task.Label
import uk.whitecrescent.waqti.task.Priority
import uk.whitecrescent.waqti.task.Property
import uk.whitecrescent.waqti.task.Task
import uk.whitecrescent.waqti.task.TimeUnit
import java.util.Random
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.set

/*
 * The intent of this class is to contain all the objects we need in memory, literally a cache, what it has to do is
 * the following.
 * Be able to read the main persistence (Database, will differ by platform) then load the necessary (and only the
 * necessary) objects from persistence to ConcurrentHashMaps inside this class which we can then use to quickly
 * access the objects we need, not needing to request to read the database all the time, the issue will come with
 * automatic updating which actually we can do here and is one of the main purposes of this class, we can have a few
 * observers check to see that the objects correspond to their equivalent in the persistent database, if not then
 * we update the persistent database with what's in the cache because it implies that there was some update called
 * or done, this saves us from having to mess with the persistent database directly very often or even better, stops
 * us from having an inconsistency between memory and database making us not have to call 2 updates (1 for memory
 * and 1 for persistence).
 *
 * The point is that this class will abstract away all of that, making it seem like all we have to do is just update
 * the memory.
 *
 * There probably will be potential issues with this, concurrency being one, consistency being the other, who's the
 * source of truth etc etc, but the benefits are quite great so this is worth it but it needs to be tested and done
 * with care
 *
 * This also stops us from having to update a million things when the database implementation changes, we would just
 * need to change this class
 *
 * In summary the Cache is then just a middle man between the code (CRUDs) and the persistence database mainly for
 * the reason to have database independence/ modularity and minimize database operations directly, instead delegating
 * them to be done here
 *
 * Long note but well worth it, it's a good idea.
 *
 * Bassam Helal Mon-14-May-18
 *
 * */
object Cache {

    private val taskDB = ConcurrentHashMap<ID, Task>()
    private val templateDB = ConcurrentHashMap<String, Bundle<String, Property<*>>>()
    private val labelsDB = ConcurrentHashMap<ID, Label>()
    private val priorityDB = ConcurrentHashMap<ID, Priority>()
    private val timeUnitDB = ConcurrentHashMap<ID, TimeUnit>()
    // TODO: 14-May-18 Collections stuff maybe but later, probably using IDs a lot

    //region Tasks

    fun newTaskID() = taskDB.newID()

    fun putTask(task: Task) {
        taskDB[task.id()] = task
    }

    fun putTasks(tasks: Collection<Task>) {
        tasks.forEach { putTask(it) }
    }

    inline fun putTasksIf(tasks: Collection<Task>, predicate: () -> Boolean) {
        tasks.forEach { if (predicate.invoke()) putTask(it) }
    }

    fun getTask(id: ID): Task {
        return taskDB.safeGet(id)
    }

    fun getTasks(ids: Collection<ID>): List<Task> {
        return ids.map { getTask(it) }
    }

    fun removeTask(task: Task) {
        if (task in taskDB) taskDB[task].endObservers()
        taskDB.remove(task.id())
    }

    fun removeTasks(tasks: Collection<Task>) {
        tasks.forEach { removeTask(it) }
    }

    inline fun removeTasksIf(tasks: Collection<Task>, predicate: () -> Boolean) {
        tasks.forEach { if (predicate.invoke()) removeTask(it) }
    }

    fun clearTasks() {
        taskDB.forEach { _: ID, task: Task -> task.endObservers() }
        taskDB.clear()
    }

    fun containsTask(task: Task): Boolean {
        return task in taskDB
    }

    fun containsTasks(tasks: Collection<Task>): Boolean {
        return tasks.all { containsTask(it) }
    }

    // To Build Queries using kotlin stdlib
    fun allTasks(): List<Task> {
        return taskDB.values.toList()
    }

    //endregion Tasks

    //region Templates

    // TODO: 14-May-18 This is an issue

    //endregion Templates

    //region Extensions

    fun <V : Cacheable> ConcurrentHashMap<ID, V>.safeGet(id: ID): V {
        val found = this[id]
        if (found == null) throw CacheElementNotFoundException(id)
        else return found
    }

    fun <V : Cacheable> ConcurrentHashMap<ID, V>.newID(): ID {
        var id = Math.abs(Random().nextLong())
        while (this.containsKey(id)) {
            id = Math.abs(Random().nextLong())
        }
        return id
    }

    operator fun <V : Cacheable> ConcurrentHashMap<ID, V>.get(value: V): V {
        val found = this[value.id()]
        if (found == null) throw CacheElementNotFoundException(value)
        else return found
    }

    operator fun <V : Cacheable> ConcurrentHashMap<ID, V>.set(old: V, new: V) {
        this[old.id()] = new
    }

    operator fun <V : Cacheable> ConcurrentHashMap<ID, V>.plus(value: V) {
        this[value.id()] = value
    }

    operator fun <V : Cacheable> ConcurrentHashMap<ID, V>.plus(collection: Collection<V>) {
        collection.forEach { this.putIfAbsent(it.id(), it) }
    }

    operator fun <V : Cacheable> ConcurrentHashMap<ID, V>.minus(value: V) {
        this.remove(value.id())
    }

    operator fun <V : Cacheable> ConcurrentHashMap<ID, V>.minus(collection: Collection<V>) {
        collection.forEach { this.remove(it.id()) }
    }

    //endregion Extensions

}
