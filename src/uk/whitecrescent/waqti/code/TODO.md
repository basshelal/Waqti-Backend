# TODO

* When Task is given a Constraint we must change it to being failable automatically

* Concurrent Constraint checking to transition between states in the lifecycle

* Based on Concurrent stuff, check which state we make the default, WAITING should be the default

* Documentation (A good way of better understanding the codebase and allowing for easy returning)

* Make sure all Task Properties are working perfectly

* Make sure before and after Constraints work well with Tuples perfectly

* Make sure Properties can become Constraints and still work

* Make sure killing and failing and the Task Lifecycle works perfectly

* ~~Task Failing Sub-cycle (Failing, Sleeping and back to Existing (Waiting)) needs concurrent time checking, maybe we use Rx for this~~

* ~~Organize tests properly~~

* ~~Task Before and After Properties using TaskID and not Tasks~~

* ~~Store persistent Task IDs that are randomly generated somewhere, these identify Tasks, the idea being that every time a new Task is created (in the sense like created in the Database not really as in Objects) it is assigned a new Task ID randomly which is unique to it. This obviously requires persistence in mind.~~

* ~~Simple Task Persistence (Use JSON but consider SQLite since we can have the DB to use Task ID as Primary Key)~~
