package uk.whitecrescent.waqti.code

enum class TaskState {
    SLEEPING, // Not yet relevant or Waiting to be relevant once again, after being failed
    EXISTING,
    FAILED,
    KILLED,
    IMMORTAL // Template
}