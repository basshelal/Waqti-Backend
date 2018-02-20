# Property

A Property is an attribute of a Task. A Task may exist without any Property as they are optional.

The list of Properties available is subject to change.

Note that Constraints are a special type of Property.

## Properties List

Note that many if not most of these Properties can be Constraints.

* Scheduled Time: This Property represents the point in natural time in which the Task will begin existing, which is scheduled or set by the user.

* Duration: This Property represents the estimated amount of Time / Time duration that this Task will take, this can be defined using any Time Measurement Unit. This would be an estimated duration and should also include the user defined grace period.

* Aged: This Property represents if a Task has aged (been reborn) and how many times. This Property cannot be set by the user however Task aging can be disabled by the user. There would also exist an Age for Tasks that would represent the Task is "rotting" and should be acted upon, this can be optional too.

* Failed Time: The time when the Task was failed, applies only to failable Tasks that have been failed.

* Fails: The number of times the Task has been failed.

* Optional: Shows whether the Task is optional or not, an optional Task has a less priority than a non-optional Task even if it is lowest Priority, optionals should not be failable.

* SubTasks: This Property would be the list of SubTasks that this Task has, by default the Task has zero SubTasks. The relation between the SuperTask and the SubTask(s) is one that is not necessarily strong, however Constraints are shared upwards, meaning the SuperTask cannot be killed unless the SubTask(s)'s Constraints are met, unless said SubTask(s) is optional.

* Priority: A Customizable way of representing the Priority of a Task, this would allow for more important Tasks to be highlighted and be shown over less important Tasks, this would be a Collision, when 2 or more Tasks take the same time period. If this is the case then there will be a Collision Warning and the Task with the higher priority will be shown, if the Tasks have equal priority then we call this a Strong Collision, this needs to be solved by the user.

* Description:

### Other Internal Properties

* Blueprint: Shows whether this Task is a Blueprint Task or not, this should disallow other Properties from existing as a result (but maybe not).

* Killable: 

* Failable: 

## Examples