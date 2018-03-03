# Task

A Task is the smallest, ***independent*** unit in the Time Management System.

A Task may have Properties that add information to it, an example Property would be the duration Property that describes
 the duration of time of the Task. All Properties are optional.

## The Task Lifecycle

All Tasks have a lifecycle. A Task may be in only one of the of the following lifecycle states at a given point in time.

### Unborn

A Task is said to be in the Unborn state if it has been created but is not yet killable, we say then that it is not yet relevant,
 this often applies to Tasks with a Property bound to a scheduled time.

The Unborn state is attributed to a Task that is not yet killable.

An example of this would be the following Task:

    Title: Prepare tomorrow's breakfast
    Properties:
        Scheduled Time: Friday 10th November 16:00
        Duration: 60 minutes

This task is only relevant and active at 16:00 on Friday 10th November, before that time it is not killable, this is because it has
 a scheduled time Property, so _before_ that time the Task is in the Unborn state.

However if a Task does not have such a Property then it is never in the Unborn state and immediately jumps to the next state, the Existing state.

### Existing

A Task is said to be in the Existing state if it is currently killable, and so it is relevant at this current time.

A Task with a Property bound to a scheduled time is killable after that time has passed, if it also has a Property bound to
 duration then it is killable during that time window (from scheduled time until scheduled time plus duration plus possible grace period).

A Task with a Constraint that has not yet been met is still in the Existing state despite not being killable.

The Existing state is attributed to a Task that is currently killable ignoring any met or unmet Constraints. we say the Task is currently relevant.

An example of this would be the following Task:

    Title: Tidy up office space
    Properties:
        Scheduled Time: Friday 10th November 18:00
        Duration: 30 minutes

This task becomes Existing at 18:00 on Friday 10th November, before that time it is in the Unborn state, at 18:00 on Friday 
10th November it becomes Existing and so it is killable. It remains in the Existing state until 18:00 + 30 minutes + possible grace period 
(nothing for this example) and so at 18:30 it ends existence.

If a Task did not have a duration Property then it would remain in the Existing state until it would be killed.

If a Task had no Properties describing scheduled time or duration then it would be in the Existing state indefinitely.

### Failed

A Task is said to be in the Failed state if it is was at some point killable and is now longer killable, we say this Task is a 
failable Task because it is possible for it to be in the Failed state.

A Task with a Constraint that can be at some point no longer possible is a Task that can be failed since the Constraint can no longer
 be met and so it is no longer killable, so it is Failed.

The Failed state is attributed to a Task that was killable at some point and now is no longer killable. This state does not exist 
for Tasks that are not failable.

An example of this would be the following Task:

    Title: Buy mushrooms for tomorrow's lunch
    Properties:
        Scheduled Time: Friday 10th November 13:00
        Duration: 60 minutes
        Failed Time: Friday 10th November 17:00

This task is failable by the Failed Time Property, the Task is failed if it is not in the Killed state by Friday 10th November 17:00, 
at which point it can no longer be achieved since in this example the stores close at that time and so if the user did not achieve that
 Task by that time it is failed.

### Sleeping

A Task is said to be in the Sleeping state if it has been delegated, meaning it was at some point or currently is Existing and has been 
given a new point in time to be Existing. This could also be in conjunction with being failable and is often used for that purpose, 
specifically a failable Task is realized by the user to be unable to be accomplished so to avoid having a Failed Task the Task will be 
delegated to another time. In between the current time and the new delegation time, the Task is in the Sleeping State. After a Task has
 been delegated, ie is currently in the Sleeping state, we say the Task has aged.

The Sleeping state is attributed to a Task that is or was Existing and is no longer Existing but has been delegated to be Existing 
at another point in time. In between both points in time it is in the Sleeping state. The Sleeping state strongly relates to Task aging.

An example of this would be the following Task:

    Title: See Dr. Tam during his office hours
    Properties:
        Scheduled Time: Friday 10th November 10:00
        Failed Time: Friday 10th November 11:00
        Delegated Time: Friday 17th November 10:00
        Delegated Fail Time: Friday 17th November 11:00
        Age: 1

This task is in the Sleeping state between the time the user delegated it, say 10:45 when they realized it cannot be done, 
to the time that it will Exist again, also known as being Reborn. Delegating the Task will automatically add to its Age.

### Killed

A Task is said to be in the Killed state if it was at some point in the Existing state and is no longer in that state because of the user killing it. 
This corresponds to a user checking off the Task that it is done.

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

A Task is said to be in the Immortal state if it is a special kind of Task called the Blueprint Task, a Task that exists solely 
to create copies of itself which are actual Tasks.

The Immortal state is attributed to a Blueprint Task, which is a Task that has no Existing time and is not failable nor killable.

An example of this would be the following Task:

    Title: Shower
    Properties:
        Duration: 30 minutes
        Blueprint: True

This task is in the Immortal state since it is a Blueprint Task, it cannot exist, and is not failable nor killable. 
Its sole purpose is to create copies of itself that are regular Tasks.

### Notes on the Task lifecycle:

* If a SuperTask has been killed then its SubTasks are all killed as well, provided all Constraints are met. Constraints of SubTasks
 should by definition be Constraints of the SuperTask (this should be a selectable option, to Constrain the SuperTask to its SubTasks,
  if not Constrained and the SuperTask is killed the SubTasks will be either independent Tasks or killed as well)
* A Task becomes killable when it is in the Existing state and all its Constraints are met. If there exists a Constraint 
of the Task that is not met then the task is not killable despite being Existing.

## Other Task Notes

### Killable

A Task is killable when all its Constraints are met and it has not yet been failed if it is failable

### Failable

A Task is failable when it has a Constraint that can be failed and the Task is currently Existing as well.

### Template Tasks (Self Replicating Tasks)

A Blueprint Task is a Task that exists only to create copies of itself that are normal Tasks.

A Blueprint Task therefore is always in the Immortal state.

