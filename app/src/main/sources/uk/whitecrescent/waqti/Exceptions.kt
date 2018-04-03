package uk.whitecrescent.waqti

// TODO: 03-Apr-18 Put this guy in the right places, where the reason of the exception is unknown
class UnknownException(string: String = "Unknown exception!") : IllegalStateException(string)