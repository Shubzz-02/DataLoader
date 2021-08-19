# DataFeeder
## _Load millions of data in database using JAVA fast and efficient way_


Datafeeder is just a simple JAVA program to feed large amount of data in database (MySQL) using less resource possible (Less CPU usage and Less RAM usage on host machine).Please note I developed it for personal use and to learn concept like thread pooling etc.However, while using on my machine it took ~3 min to Insert 1 million record in database.

(Table has 32 Columns)

## Machine

- Bitnami MySQL Stack For Virtual Machines (Installed in VirtualBox with config 512 mb ram single core cpu)
- While running program max 2 GB ram used by Intellij with 9-10% CPU load



## Learnings
> Creating database connection is very costly so connection is created only one time while program starts.
> Creating and destroying threads are costly if we need threads to do n number of task.
> So thread pool concept is used, it will just create n number of thread and after a thread finishes it work it will just wait in pool for new task, so we can use that thread for  n number task.


## Installation

- Clone this repository and open in Intellij or any other IDE.
- navigate to /resource folder and open config.properties file
- Just edit database url, user, pass and your data file
- in datasource. table update your table schema
- in datasource.query update your insert query
- Simply run 


![Alt text](/Screenshot/ss.jpg "MySql Workbench")
