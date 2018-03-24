# TODO
 
* Relative Time like tomorrow, next week etc
 
* Ensure the Task lifecycle works with tests and documentation

* Figure out a better way to implement Template Tasks, maybe have a function in Task to get this Task's information 
and save it as a Template Task, using a state just for Template Tasks is a bad idea

* Write stories tests to test as many possible scenarios, here we try to write tests to break the thing

* Priority collisions are not done on the Task level, they are done on the List level, specifically TimedLists which 
will check all their elements and see for collisions

* Collections like Tuples, Lists, TimedLists, Boards and maybe others, TodayList, PriorityList Lists that allow only 
specific Tasks etc

-------------------------------------------------------------------------------------------------------------------------------------------------

# Done

* ~~Finish all Properties with tests and documentation to ensure (as much as possible) they work perfectly as intended~~

* ~~Consider the Optional issue, should we make it constrainable and give it so much power over Task or keep it simple 
and keep it the way it is?~~

* ~~Fix the re-setting problem with the Un-constraining problem on all the Observers and test them and re-document~~

* ~~Consider the un-constraining problem (I suspect the solution may be simple but better to be safe), the problem: 
Think about when we un-constrain while it's checking the condition, for example if we have duration as a Constraint
 for a while then before it is met we un-constrain, the checking should end, but I don't think it will, we need to 
 test this extensively~~

* ~~Test Activity within Tasks, inside a Task, sub-tasks can interact with one another and with the parent, this is 
tricky but quite useful~~

* ~~Concurrent Constraint checking to transition between states in the lifecycle,
 meaning for example if deadline is a Constraint then there must be something that constantly checks the time to compare the current time 
 with the deadline time and when they are equal to act accordingly, in this case to fail the task.~~

* ~~Deadline has the problem that if you kill before the deadline then deadline is an unmet Constraint and you can't 
kill at all! Maybe we disregard deadline from being one of the Constraints to be met before killing. Deadline must be
constrainable so that it can be a descriptor (show deadline but don't do anything special about it) and an enforcer 
(show deadline and make sure that the Task behaves accordingly)~~

* ~~Make Optional only a Property, having it as a Constraint is a little weird since why would one single Constraint 
cancel the effects of all the others? Let Optional be a descriptor only and not an Enforcer~~

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
