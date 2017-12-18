# Constraint

A Constraint is a special Property of a Task that has special access to the Task's lifecycle.

## Constraints List

* Default Constraint: A Task N will not be killed until it is killed by the user, the default Constraint guarantees that killing of a Task is done by the user, this Constraint cannot be removed.

* Other Task Constraint: A Task N which has the Constraint cannot be killed unless Task M is killed (or its Constraint is met), this basically creates an ordering, sometimes this is desired.

* Deadline: The Task must be killed before the deadline otherwise it is failed, this means a time.

* Target: