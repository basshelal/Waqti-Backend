# Task

A Task is the smallest, independent unit in the Time Management System.

A Task may have Properties that add information to it, an example Property would be the duration Property that describes the duration of time of the Task. All Properties are optional.

## The Task Lifecycle

All Tasks have a lifecycle. A Task may be in only one of the of the following lifecycle states at a given point in time.

### Unborn

A Task is said to be in the Unborn state if it has been created but is not yet killable, we say then that it is not yet relevant, this often applies to Tasks with a Property bound to a scheduled time.

The Unborn state is attributed to a Task that is not yet killable.

An example of this would be the following Task:

    Title: Prepare tomorrow's breakfast
    Properties:
        Scheduled Time: Friday 10th November 16:00
        Duration: 60 minutes

This task is only relevant and active at 16:00 on Friday 10th November, before that time it is not killable, this is because it has a scheduled time Property, so _before_ that time the Task is in the Unborn state.

However if a Task does not have such a Property then it is never in the Unborn state and immediately jumps to the next state, the Existing state.

### Existing

A Task is said to be in the Existing state if it is currently killable, and so it is relevant at this current time.

A Task with a Property bound to a scheduled time is killable after that time has passed, if it also has a Property bound to duration then it is killable during that time window (from scheduled time until scheduled time plus duration plus possible grace period).

A Task with a Constraint that has not yet been met is still in the Existing state despite not being killable.

The Existing state is attributed to a Task that is currently killable ignoring any met or unmet Constraints. we say the Task is currently relevant.

An example of this would be the following Task:

    Title: Tidy up office space
    Properties:
        Scheduled Time: Friday 10th November 18:00
        Duration: 30 minutes

This task becomes Existing at 18:00 on Friday 10th November, before that time it is in the Unborn state, at 18:00 on Friday 10th November it becomes Existing and so it is killable. It remains in the Existing state until 18:00 + 30 minutes + possible grace period (nothing for this example) and so at 18:30 it ends existence.

If a Task did not have a duration Property then it would remain in the Existing state until it would be killed.

If a Task had no Properties describing scheduled time or duration then it would be in the Existing state indefinitely.

### Failed

A Task is said to be in the Failed state if it is was at some point killable and is now longer killable, we say this Task is a failable Task because it is possible for it to be in the Failed state.

A Task with a Constraint that can be at some point no longer possible is a Task that can be failed since the Constraint can no longer be met and so it is no longer killable, so it is Failed.

The Failed state is attributed to a Task that was killable at some point and now is no longer killable. This state does not exist for Tasks that are not failable.

An example of this would be the following Task:

    Title: Buy mushrooms for tomorrow's lunch
    Properties:
        Scheduled Time: Friday 10th November 13:00
        Duration: 60 minutes
        Failed Time: Friday 10th November 17:00

This task is failable by the Failed Time Property, the Task is failed if it is not in the Killed state by Friday 10th November 17:00, at which point it can no longer be achieved since in this example the stores close at that time and so if the user did not achieve that Task by that time it is failed.

### Sleeping

A Task is said to be in the Sleeping state if it has been delegated, meaning it was at some point or currently is Existing and has been given a new point in time to be Existing. This could also be in conjunction with being failable and is often used for that purpose, specifically a failable Task is realized by the user to be unable to be accomplished so to avoid having a Failed Task the Task will be delegated to another time. In between the current time and the new delegation time, the Task is in the Sleeping State. After a Task has been delegated, ie is currently in the Sleeping state, we say the Task has aged.

The Sleeping state is attributed to a Task that is or was Existing and is no longer Existing but has been delegated to be Existing at another point in time. In between both points in time it is in the Sleeping state. The Sleeping state strongly relates to Task aging.

An example of this would be the following Task:

    Title: See Dr. Tam during his office hours
    Properties:
        Scheduled Time: Friday 10th November 10:00
        Failed Time: Friday 10th November 11:00
        Delegated Time: Friday 17th November 10:00
        Delegated Fail Time: Friday 17th November 11:00
        Age: 1

This task is in the Sleeping state between the time the user delegated it, say 10:45 when they realized it cannot be done, to the time that it will Exist again, also known as being Reborn. Delegating the Task will automatically add to its Age.

### Killed

A Task is said to be in the Killed state if it was at some point in the Existing state and is no longer in that state because of the user killing it. This corresponds to a user checking off the Task that it is done.

A Task can be in the Killed state only if it is killable, a Task cannot enter the Killed state if it has Constraints that are not met.

The Killed state is attributed to a Task that was killable and has been checked off by the user as done. A Task becomes no longer relevant after being killed.

An example of this would be the following Task:

    Title: Have breakfast
    Properties:
        Scheduled Time: Friday 10th November 9:00
        Duration: 30 minutes
        Killed Time: Friday 10th November 9:28

This task is in the Killed state since it was killable and has met all its Constraints and was killed by the user signifying that it has been accomplished.

### Immortal

A Task is said to be in the Immortal state if it is a special kind of Task called the Blueprint Task, a Task that exists solely to create copies of itself which are actual Tasks.

The Immortal state is attributed to a Blueprint Task, which is a Task that has no Existing time and is not failable nor killable.

An example of this would be the following Task:

    Title: Shower
    Properties:
        Duration: 30 minutes
        Blueprint: True

This task is in the Immortal state since it is a Blueprint Task, it cannot exist, and is not failable nor killable. Its sole purpose is to create copies of itself that are regular Tasks.


### Notes on the Task lifecycle:

* If a SuperTask has been killed then its SubTasks are all killed as well, provided all Constraints are met. Constraints of SubTasks should by definition be Constraints of the SuperTask.
* A Task becomes killable when it is in the Existing state and all its Constraints are met. If there exists a Constraint of the Task that is not met then the task is not killable despite being Existing.
* A Task is failable when it has a Constraint that can be failed and the Task is currently Existing as well.

### Killable

### Failable

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
