There could be "Free-Time decider". This means that when a Time slot is not filled with a Task then the app can choose randomly (or based on preference of level of importance) which Task to fill that slot with from a user-defined list of Recurring Tasks which would be a List of Preset Tasks. These Tasks should preferably be part of a Goal/ Habit. Note a Habit is different from a Routine.

A Routine is a set of Tasks that form a larger Task, it is a special type of Nested Task collection

Time Blocks do not have to follow natural time units, there can exist pre-defined units of time like a 45 minute block or some given blocks like a Pomodoro being 25 minutes. This will allow time planning based around any unit of time, including natural units like minutes or hours, or user-defined units. Note that time can be more than days or months.

Time blocks can be a range and not and exact number, for example a Time Block Range called "Short" can be user-defined to be between 10-30 minutes. This can then be set as a time based property of the Task. A Time Block can be defined using a natural time unit or even other Time Units, for example a "Full Pomodoro" could include 4 Units of "Pomodoro", which itself is defined using natural units.

For any time we can use the new Java Time API in `java.time.*`

We should have a timer in the app

Tasks can exist in pairs or groups, this would be a Routine or not necessarily

Habit tracking

Routines and Habits go hand in hand, to build a Habit you need to create a Routine but not the other way round

Task Collections!!

Lists are a Collection of Tasks and can exist with restrictions on the Tasks they can contain

Lists can have types, a List can be a Date or just a general List

Calendar view? Kanban! Maybe a nice mix of the 2? This is mostly a view thing but involves the model quite heavily and so important to know early.

Define the Grace Period (for the Duration Property).

Procedures are like actions to take when a specific event happens, like for example the Task has not been killed nor delegated 5 minutes before its fail time, then there can be a procedure to delegate it to a specified time automatically.

Templates so this is like a Skeleton.

Recurring Tasks, this is different from Template / Blueprint Tasks

Projects / Boards could be like Boards in Kanban, there could be a feature to have the Master Board, the one that contains all Boards information and deadlines etc, or merger boards, an example of this would be a Board for a certain project and another board for another project and the merger Board would show deadlines for both Boards in order to have a big picture of the coming workload on the user. The Master Board would be exactly the same but would have all Boards info, this is like the Inbox of the Boards.

Boards can be left for a little later, not a necessary feature but a good one to take care of early

A Task can be paired or merged to another, this can be considered a delegation, so they both become a Task Group

There will exist Routines, a set of Tasks together to form a specific larger Task

There will exist Habits / Goals, these are different from both Macros and Routines

There will also be macros, a set of Tasks that form like a skeleton/ framework for the day

A Task can be failed, then delegated? But it will say Failed and how many times? This seems like it defeats the purpose of being Failable but we can consider it maybe.

We can say that we "push" a Task, since it has been Failed, this is delegation for Failed Tasks, this (I think) is no different than regular delegation

Moments are significant points in time, things like "Sunset" or other things that just happen, not requiring the user to take any action, so not a Task, but an important point in time

Targets are things that enforce the SMART goals, specifically the M, measurable, the Target is a Constraint used to check progress of the Task, this is user based, so the user can check off this Constraint even if it has not been done, discipline and honesty are very nice things you know

There should before and after Task Constraints, meaning a Task N can only be checked off if an optional Task was done, also, Task M must be done if optional Task N was done, this enforces coupling between Tasks if desired, a strong feature of Tuples

We can bound slots to anonymous Tasks of certain Properties, an example of this is basically at 16:00 a Task with either the label of "Software Development" or "University" must be done, nothing else, so we can bound this time slot to an anonymous Task that fits this criteria or accept a true Task that fits such criteria. We need to explain what bounded and unbounded time or slots means.

A Routine can be a template Tuple?

### Todoist features:
* Projects, like larger scale categories, similar to Lists or Boards in Trello, in a Project you have similar and related Tasks and they can all be viewed by date in the Main / Inbox Project
* Labels are like custom categories or tags
* Priority levels
* Parent Tasks
* Comments
* Reminders
* Tasks can be delegated to a later time, so instead of being failed they can age
* All kinds of sorting, filtering and data analytics
* Search feature
