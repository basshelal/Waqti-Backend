package uk.whitecrescent.waqti.code

import java.time.Duration

class TimeUnit(val name: String, val duration: Duration) {

    /*
     *
     * This would ideally be an Object with functions that convert persisted Custom Time Units into Java API usable
     * Durations, so the user would create a new TimeUnit called "Pomodoro" which is 25 minutes of Natural Time, we
     * store that, then when Task X is given a duration Property with the value of 2 Pomodoros we have a function
     * that converts that into Natural Time, this does assume persistence is dealt with however.
     *
     * This whole thing can maybe be done with Extension Functions to the Duration class, but I'm unsure about that
     * being the best way of doing it.
     * Another way could be to make duration Property be of type TimeUnit and not of Duration and have TimeUnit
     * consist of a Duration object in it, this is safe and familiar but may require a bit of code.
     */

}