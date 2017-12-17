#Tuple

A Tuple is a Collection of Tasks that automatically enforces strict sequential ordering between the Tasks in it so Task 4 is Constrained by Task 3 and 3 is Constrained by 2 and so on.

This is useful for defining groups of Tasks that have an ordering relationship that are not necessarily parts of a larger parent Task, these may be unrelated Tasks and the only thing they have in common is the need to be executed sequentially one after the other. This differs from a Super/Sub Task relationship in which ordering is not enforced and the SubTasks make up the parts required of the SuperTask. This is a slight difference but a significant one.

A Routine is an example of a type of Tuple.

Maybe there should exist 2 types of Tuple, Constrained Tuple, the one discussed above and Non Constrained Tuple in order to allow non Constrained groups of Tasks to be grouped together, this would be useful for a commonly used group of Tasks, for example to create a Day template/ skeleton, many Tasks reoccur every day. Non Constrained Tuple would have ordering but not enforced, meaning Tasks do not have to be executed in a row, sequentially and be done out of order, unlike a Constrained Tuple.