package uk.whitecrescent.waqti.code


// TODO more Exceptions to specify problems more clearly
class TaskStateException(string: String, state: TaskState) : IllegalStateException("$string\n State: $state")

class ConcurrentException(string: String) : IllegalStateException(string)