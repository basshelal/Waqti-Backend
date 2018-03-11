# TODO

* Think about when we un-constrain while it's checking the condition, for example if we have duration as a Constraint
 for a while then before it is met we un-constrain, the checking should end, but I don't think it will, we need to 
 test this extensively
 
* Optional is tricky, so if optional is a Constraint then it cannot be failed no matter what, but what if we 
un-constrain optional, then it should be failable if there was some other constraint happening, this means that we 
have a background check, or the means by which we un-constrain we can do the operation needed, instead of background 
checks

* Deadline has the problem that if you kill before the deadline then deadline is an unmet Constraint and you can't 
kill at all! Maybe we disregard deadline from being one of the Constraints to be met before killing. Deadline must be
constrainable so that it can be a descriptor (show deadline but don't do anything special about it) and an enforcer 
(show deadline and make sure that the Task behaves accordingly)

* Sun-25-Feb Currently the focus is `Task` we need to make sure this thing works perfectly and does everything we 
need it to,
 rewriting this thing will be a mess so we must try and make sure it works well, it's the core of this whole project,
  everything else in this project relies on the assumption that Task is perfect.
  
* Sat-3-Mar Note on Persistence: We may need to change the way we persist by using either SQLite (the Android Database 
System) or MongoDB (A NoSQL JSON like System). The problem with using SQLite is Object Relational Mapping, and in our case 
the problem arises from having Collections that have a variable number of Tasks in them as well as Tasks containing 
IDs of other Tasks and all sorts of heavy Object Oriented Design style that may be difficult or inconvenient to map 
to Relational SQL, not to mention the difficulty of having to have SQLite set up and working well, however in the 
long term SQLite is most definitely the better choice. I would like to use SQLite but if the Object Relational 
Mapping problem is too great and could cause too many problems I may just try to stick with JSON. On Android I 
believe this should be less of a problem if we use Room Persistence Library but again, Object Relational Mapping. In 
the meantime I'll work on translating the design into some Relational form using ER Diagrams.

* Figure out a better way to implement Template Tasks, maybe have a function in Task to get this Task's information 
and save it as a Template Task, using a state just for Template Tasks is a bad idea

* Concurrent Constraint checking to transition between states in the lifecycle,
 meaning for example if deadline is a Constraint then there must be something that constantly checks the time to compare the current time 
 with the deadline time and when they are equal to act accordingly, in this case to fail the task.

* Documentation (A good way of better understanding the codebase and allowing for easy returning)

* Make sure to have Unit tests that cover 100% of Task class when it is near completion

* Try to make Integration tests for Task as well since basically it's the basic unit and the core of everything in this project

* Update the Documentation Files as necessary

* Make sure all Task Properties are working perfectly

* Priority collisions are not done on the Task level, they are done on the List level, specifically TimedLists which 
will check all their elements and see for collisions

* Make sure before and after Constraints work well with Tuples perfectly

* Make sure Properties can become Constraints and still work

* Make sure killing and failing and the Task Lifecycle works perfectly

-------------------------------------------------------------------------------------------------------------------------------------------------

# Done

* ~~Stop using JSON!~~

* ~~Based on Concurrent stuff, check which state we make the default,
 WAITING should be the default since a Task is waiting to be relevant, however this can be an option?
  Maybe we make Task be EXISTING and then if Time is set then it can change to WAITING, then when that time passes it will be EXISTING,
  so the default state does not matter as much as having effective concurrent checking of Task's state (as in 
  condition) in time.~~

* ~~When Task is given a Constraint we must change it to being failable automatically (this may also require Rx)~~

* ~~Remember Custom Time Units~~

* ~~Sun-4-Mar Rewrite the Documentation MarkDown files to make sure we can clearly define what we need especially for 
Task since there are ambiguities with Task Properties and Constraints and how a Task should behave~~

* ~~We need to define which Properties can be Constraints and which can't and the behaviours when Properties are 
allowed to be Constraints, why should Description be a Constraint??~~

* ~~Task Failing Sub-cycle (Failing, Sleeping and back to Existing (Waiting)) needs concurrent time checking, maybe we use Rx for this~~

* ~~Organize tests properly~~

* ~~Task Before and After Properties using TaskID and not Tasks~~

* ~~Store persistent Task IDs that are randomly generated somewhere, these identify Tasks,
 the idea being that every time a new Task is created (in the sense like created in the Database not really as in Objects)
  it is assigned a new Task ID randomly which is unique to it. This obviously requires persistence in mind.~~

* ~~Simple Task Persistence (Use JSON but consider SQLite since we can have the DB to use Task ID as Primary Key)~~
