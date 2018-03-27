package uk.whitecrescent.waqti.code

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.time.Duration
import java.util.concurrent.TimeUnit

class Timer {

    private val thread = Schedulers.newThread()
    private val timePeriod = 1L
    private val timeUnit = TimeUnit.MILLISECONDS

    private var stopped = true
    private var paused = false
    private var running = false

    private var lastTime = now()

    var duration = Duration.ZERO
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