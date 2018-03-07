package uk.whitecrescent.waqti.test

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import uk.whitecrescent.waqti.code.Concurrent
import uk.whitecrescent.waqti.code.Constraint
import uk.whitecrescent.waqti.code.GSON
import uk.whitecrescent.waqti.code.MET
import uk.whitecrescent.waqti.code.SHOWING
import uk.whitecrescent.waqti.code.Task
import uk.whitecrescent.waqti.code.TaskState
import uk.whitecrescent.waqti.code.UNMET
import uk.whitecrescent.waqti.code.logI
import uk.whitecrescent.waqti.code.now
import uk.whitecrescent.waqti.code.sleep
import java.time.LocalDateTime

class OtherTests {

    @DisplayName("Test")
    @Test
    fun test() {
    }

    @DisplayName("Runner")
    @Test
    fun runner() {
        val task = Task("Task").setDeadlineConstraint(Constraint(SHOWING, now().plusSeconds(10), UNMET))
        logI(task.isFailable)
        logI(task.getTaskState())
        logI(task.getAllUnmetAndShowingConstraints())

        sleep(15000)

        logI(task.getTaskState())
        logI(task.getAllUnmetAndShowingConstraints())

    }

    @DisplayName("Concurrency with RxJava")
    @Test
    fun concurrent0() {

        GSON.clearTaskIDFile()
        GSON.clearTasksFile()

        val task = Task("My Task").setTimeProperty(Constraint(SHOWING, LocalDateTime.now().plusSeconds(2), UNMET))
        val task1 = Task("My Other Task").setBeforeProperty(task)
        println(task.time.value)

        val thread = Schedulers.newThread()

        Observable.just({ LocalDateTime.now() })
                .repeatUntil({ task.getTaskState() == TaskState.KILLED })
                .subscribeOn(thread)
                .subscribe(
                        {
                            if (it.invoke() == task.time.value) {
                                (task.time as Constraint).isMet = MET
                            }
                            if (task.canKill()) task.kill()
                        },
                        { throw IllegalArgumentException() },
                        { println("Completed Task0 state: ${task.getTaskState()}") },
                        { println("Subscribed0") })

        Observable.just({ task.getTaskState() })
                .repeatUntil({ task1.getTaskState() == TaskState.KILLED })
                .subscribeOn(thread)
                .subscribe(
                        {
                            if (it.invoke() == TaskState.KILLED) task1.kill()
                        },
                        { throw IllegalArgumentException() },
                        { println("Completed Task1 state: ${task1.getTaskState()}") },
                        { println("Subscribed1") })

        println("ended exec")
        println(Thread.activeCount())
        for (i in Thread.getAllStackTraces()) {
            if (i.key.isAlive)
                println("${i.key.name} ${i.key.state}")
        }
        println("Current Thread: ${Thread.currentThread()}")

        Thread.sleep(3000)
    }

    @DisplayName("Clock with RXJava")
    @Test
    fun clock() {

        val printTime = {
            println("Time: ${LocalDateTime.now().hour}:${LocalDateTime.now().minute}:${LocalDateTime.now().second}")
        }

        Concurrent.timeCheckerObservable.subscribe(
                { printTime.invoke() },
                { throw IllegalStateException() },
                { println("Completed") },
                { println("Subscribed") }
        )

        val task = Task("Task")
        val otherTask = Task("Other Task")

        Observable.just(task)
                .subscribeOn(Concurrent.timeCheckingThread)
                .repeatUntil({ task.getTaskState() == TaskState.KILLED })
                .subscribe(
                        {
                            if (it.getTaskState() == TaskState.KILLED) otherTask.kill()
                        },
                        { throw IllegalStateException() },
                        { println("Completed Task ${task.getTaskState()} OtherTask ${otherTask.getTaskState()}") },
                        { println("Subscribed") }
                )

        Thread.sleep(2000)

        task.kill()

        Thread.sleep(2000)
    }
}
