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
typealias Instant = java.time.Instant
typealias DayOfWeek = java.time.DayOfWeek
typealias Year = java.time.Year
typealias YearMonth = java.time.YearMonth
typealias Month = java.time.Month
typealias MonthDay = java.time.MonthDay

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

val Number.am: LocalTime
    get() = LocalTime.of(this.toInt(), 0)

val Number.pm: LocalTime
    get() = LocalTime.of(this.toInt() + 12, 0)

val Pair<Number, Number>.am: LocalTime
    get() = LocalTime.of(this.first.toInt(), this.second.toInt())

val Pair<Number, Number>.pm: LocalTime
    get() = LocalTime.of(this.first.toInt() + 12, this.second.toInt())

val Triple<Number, Number, Number>.am: LocalTime
    get() = LocalTime.of(this.first.toInt(), this.second.toInt(), this.third.toInt())

val Triple<Number, Number, Number>.pm: LocalTime
    get() = LocalTime.of(this.first.toInt() + 12, this.second.toInt(), this.third.toInt())

fun time(hour: Number, minute: Number): Pair<Number, Number> = Pair(hour, minute)

fun time(hour: Number, minute: Number, second: Number): Triple<Number, Number, Number> =
        Triple(hour, minute, second)

infix fun Date.at(time: LocalTime): Time = this.atTime(time)

infix fun Date.at(hour: Int): Time = this.atTime(hour, 0)

infix fun Date.at(pair: Pair<Number, Number>): Time =
        this.atTime(pair.first.toInt(), pair.second.toInt())

infix fun Date.at(triple: Triple<Number, Number, Number>): Time =
        this.atTime(triple.first.toInt(), triple.second.toInt(), triple.third.toInt())

infix fun Number.colon(other: Number): Pair<Number, Number> = this to other

infix fun Temporal.till(other: Temporal): Duration = Duration.between(this, other)

infix fun Duration.from(temporalAmount: TemporalAmount): Duration = Duration.from(temporalAmount)

fun coming(dayOfWeek: DayOfWeek): Date {
    return Date.from(today.dayOfWeek + dayOfWeek.value.toLong())
}

fun last(dayOfWeek: DayOfWeek): Date {
    return Date.from(today.dayOfWeek - dayOfWeek.value.toLong())
}