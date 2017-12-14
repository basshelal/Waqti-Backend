# Constraint

A Constraint is a special Property of a Task that has special access to the Task's lifecycle.

## Constraints List

* Default Constraint: A Task N will not be killed until it is killed by the user, the default Constraint guarantees that killing of a Task is done by the user, this Constraint cannot be removed.

* Other Task Constraint: A Task N which has the Constraint cannot be killed unless Task M is killed (or its Constraint is met), this basically creates an ordering, sometimes this is desired.

* Deadline: The Task must be killed before the deadline otherwise it is failed.

## Old Notes

A constraint is basically a set of conditions that allow a Task to exist or die, so a fixed time Task will "begin existing" at the time that it is scheduled to occur, if it has a duration then it ends existence after that duration (or another fixed time). By default Tasks will die when the user kills it (checks it off).

A Task may or may not have these constraints and can in fact never end existence and never have a fixed time of start existence (other than creation time). In such a case there MUST be the killing constraint that the user checks off this Task, the default constraint.

Constraints should at some point be made by the user or at least added to. Extra constraints may include a checklist or another Task (this has to do with Task ordering, for example, you are not allowed to kill Task "wash dishes" before the Task "have lunch" is killed) this creates an ordering or dependence on Tasks