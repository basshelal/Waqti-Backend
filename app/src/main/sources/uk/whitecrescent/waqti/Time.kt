package uk.whitecrescent.waqti


// Type Aliases for which Time API to use

typealias Time = java.time.LocalDateTime
typealias Duration = java.time.Duration
typealias Date = java.time.LocalDate
typealias ZonedTime = java.time.ZonedDateTime
typealias Temporal = java.time.temporal.Temporal
typealias TemporalAmount = java.time.temporal.TemporalAmount
typealias TemporalUnit = java.time.temporal.TemporalUnit
typealias LocalTime = java.time.LocalTime
typealias ChronoUnit = java.time.temporal.ChronoUnit

// Useful Extensions for java.time to make code readable and concise

val now: Time
    get() = Time.now()

val today: Date
    get() = Date.now()

val tomorrow: Date
    get() = Date.now().plusDays(1)

val Number.millis: Duration
    get() = Duration.ofMillis(this.toLong())

val Number.seconds: Duration
    get() = Duration.ofSeconds(this.toLong())

val Number.minutes: Duration
    get() = Duration.ofMinutes(this.toLong())

val Number.hours: Duration
    get() = Duration.ofHours(this.toLong())

val Number.days: Duration
    get() = Duration.ofDays(this.toLong())

val Number.weeks: Duration
    get() = Duration.ofDays(7L * this.toLong())

infix fun Date.at(time: LocalTime): Time = this.atTime(time)

infix fun Date.at(hour: Int): Time = this.atTime(hour, 0)

infix fun Temporal.till(other: Temporal): Duration = Duration.between(this, other)

infix fun Duration.from(temporalAmount: TemporalAmount): Duration = Duration.from(temporalAmount)