package uk.whitecrescent.waqti.task

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import uk.whitecrescent.waqti.now
import java.time.Duration
import java.util.concurrent.TimeUnit

class Timer {

    private val thread = Schedulers.newThread()
    private val timePeriod = 100L
    private val timeUnit = TimeUnit.MILLISECONDS

    var stopped = true
        private set
    var paused = false
        private set
    var running = false
        private set

    private var lastTime = now()

    var duration: Duration = Duration.ZERO
        private set

    private val timer = Observable
            .interval(timePeriod, timeUnit)
            .subscribeOn(thread)
            .takeWhile { running }

    fun start() {
        if (paused || stopped) lastTime = now()
        running = true
        paused = false
        stopped = false
        timer.subscribe(
                {
                    duration = duration.plus(Duration.between(lastTime, now()))
                    lastTime = now()
                },
                {
                    throw  ConcurrentException("Timer Failed!")
                }
        )
    }

    fun pause() {
        paused = true
        running = false
        stopped = false
    }

    fun stop() {
        running = false
        stopped = true
        paused = false
        duration = Duration.ZERO
    }
}