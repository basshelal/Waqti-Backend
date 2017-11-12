# Task

A Task is the smallest, independent unit in the Time Management System.

A Task may have Properties that add information to it, an example Property would be the duration Property that describes the duration of time of the Task. All Properties are optional.

## The Task Lifecycle

All Tasks have a lifecycle. A Task may be in any of the following lifecycle states.

* Unborn (Not yet relevant, cannot be killed this only applies if it has a scheduled time otherwise it is existing immediately after creation)
* Existing (Currently relevant, now killable)
* Failed (Not killed and no longer able to be killed, no longer needed)
* Sleeping (Is no longer killable until it will be existing again, this happens because of delegation, we say the Task has Aged since it has been delegated and will be reborn into existence)
* Killed (No longer relevant and no longer needed)

* Immortal (Self Replicating, non killable and non existent its only purpose is to create copies of itself)

Notes on the Task lifecycle:

* If a Parent Task has been killed then its Child Tasks are all killed as well, provided all Constraints are met. Constraints of Child Tasks should by definition be Constraints of the Parent Task.
* A Task becomes killable when it is in the Existing state and all its Constraints are met. If there exists a Constraint of the Task that is not met then the task is not killable despite being Existing.
* A Task is failable when it has a Constraint that can be failed and the Task is currently Existing as well.

## Blueprint Tasks (Self Replicating Tasks)

A Blueprint Task is a Task that exists only to create copies of itself that are normal Tasks.
A Blueprint Task therefore is always in the Immortal state.

## Old Notes

So, a Task has a lifecycle. 
* A Task can be existing, meaning it is currently active and can be killed.
* A Task can be killed, meaning it is done and cannot be done again, this occurs when the Constraints have been achieved (Note the default Constraint)
* A Task can be failed, meaning it has not been killed but can never be killed anymore because of a failable Constraint.
* A Task can be self creating, this has to do with having Preset Tasks, the PresetTask can never be killed or failed, it exists indefinitely, however its only purpose is to create instances of itself which are killable and failable.

Tasks can be ordered and can dependent or independent of one another, this is a Constraint, a Task N cannot be killed unless Task M is killed first.

A Task does not require a time or duration, a task can therefore be menial, in fact this could be a feature, creating powerful categories that will allow tasks to have properties of a super Task, for example the Task "Return Books" can have the properties of the category "Short Tasks" which would be the category of Tasks that don't have a duration attached to them but are definitely short, this would require a duration range feature, since predicting task duration is not easy, this would be similar to having some sort of inheritance or sharing of properties

Tasks can be a part of other Tasks (Nested Tasks), so the larger Task (maybe called the parent) would for example be "Tidy up room" which would consist of smaller Tasks that make up this bigger Task, examples would include Tasks like "Mop floor" "Clean out wardrobe" "Vacuum carpet" etc. This would mean the larger Task cannot be killed unless the ALL the smaller Tasks are killed, in doing so will kill the parent Task will be killed.

Tasks can be optional, this would mean if there is time the user may do this task, this would basically be a flag but would also indicate less importance, this would mean that if there is nothing planned for a time then the Optional Task will be shown to be achievable, an Optional Task is thus non-failable

Tasks can have deadlines, this means that a task must be done (killed) before a certain time, otherwise it will be failed

Tasks can be failed, this means it was not achieved and can never be achieved again, an example would include deadline Tasks, another less obvious one would include "Buy new headphones", that could fail if the user gets gifted a new pair or finds the pair they lost etc, the Task was never achieved and can never be attempted again

There will exist categories which can help for filtering and analytics.

There may exist an importance level, this will be how important / urgent a Task is so that when 2 Tasks collide the important one is shown over the less important one (but with a warning of collision and override)

There will exist Routines, a set of Tasks together to form a specific larger Task

There will exist Habits / Goals, these are different from both Macros and Routines

There will also be macros, a set of Tasks that form like a skeleton/ framework for the day

## Examples
