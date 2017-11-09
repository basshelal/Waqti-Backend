# Task

A Task is the smallest unit, a Task can contain properties that add conditions or Constraints to its existence, these properties can also help structure and categorize the Task in terms of a whole system.

A Task can consist of a fixed/ scheduled time or a duration or both, these are Properties/ Constraints.
We call these Constraints and Constraints are not just limited to being time-based.

A constraint is basically a set of conditions that allow a Task to exist or die, so a fixed time Task will "begin existing" at the time that it is schedules to occur, if it has a duration then it ends existence after that duration (or another fixed time). By default Tasks will die when the user kills it (checks it off).

So, a Task has a lifecycle. 
* A Task can be existing, meaning it is currently active and can be killed.
* A Task can be killed, meaning it is done and cannot be done again, this occurs when the Constraints have been achieved (Note the default Constraint)
* A Task can be failed, meaning it has not been killed but can never be killed anymore because of a failable Constraint.
* A Task can be self creating, this has to do with having Preset Tasks, the PresetTask can never be killed or failed, it exists indefinitely, however its only purpose is to create instances of itself which are killable and failable.

A Task may or may not have these constraints and can in fact never end existence and never have a fixed time of start existence (other than creation time). In such a case there MUST be the killing constraint that the user checks off this Task, the default constraint.

Constraints should at some point be made by the user or at least added to. Extra constraints may include a checklist or another Task (this has to do with Task ordering, for example, you are not allowed to kill Task "wash dishes" before the Task "have lunch" is killed) this creates an ordering or dependence on Tasks

Tasks can be ordered and can dependent or independent of one another, this is a Constraint, a Task N cannot be killed unless Task M is killed first.

A Task does not require a time or duration, a task can therefore be menial, in fact this could be a feature, creating powerful categories that will allow tasks to have properties of a super Task, for example the Task "Return Books" can have the properties of the category "Short Tasks" which would be the category of Tasks that don't have a duration attached to them but are definitely short, this would require a duration range feature, since predicting task duration is not easy, this would be similar to having some sort of inheritance or sharing of properties

Tasks can be a part of other Tasks (Nested Tasks), so the larger Task (maybe called the parent) would for example be "Tidy up room" which would consist of smaller Tasks that make up this bigger Task, examples would include Tasks like "Mop floor" "Clean out wardrobe" "Vacuum carpet" etc. This would mean the larger Task cannot be killed unless the ALL the smaller Tasks are killed, in doing so will kill the parent Task will be killed.

Tasks can be optional, this would mean if there is time the user may do this task, this would basically be a flag but would also indicate less importance

Tasks can have deadlines, this means that a task must be done (killed) before a certain time, otherwise it will be failed

Tasks can be failed, this means it was not achieved and can never be achieved again, an example would include deadline Tasks, another less obvious one would include "Buy new headphones", that could fail if the user gets gifted a new pair or finds the pair they lost etc, the Task was never achieved and can never be attempted again

There will exist categories which can help for filtering and analytics.

There may exist an importance level, this will be how important / urgent a Task is so that when 2 Tasks collide the important one is shown over the less important one (but with a warning of collision and override)

There will exist routines, a set of Tasks together to form a specific larger Task

There will also be macros, a set of Tasks that form like a skeleton/ framework for the day