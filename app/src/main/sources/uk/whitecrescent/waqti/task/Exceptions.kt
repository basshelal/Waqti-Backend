package uk.whitecrescent.waqti.task

class TaskStateException(string: String, state: TaskState) : IllegalStateException("$string\n State: $state")

class ConcurrentException(string: String) : IllegalStateException(string)