17102063 Lee you seok

**Implementation assignment #2**

Student : 17102063 Lee you seok

Operating System Design 146043 and 31001

Instructor : Lee youn ho

**Due : 4/27/2021**

**Problem:**

1. Designing a pid Manager and its test program.
1. In Pid Manager, implement getPID() ,getPIDWait(), and releasePID(int pid);
   1. getPID() - Returns the current PID, -1 if no PID is available
   1. getPIDWait() - Return current PID, if there is no PID available, wait for the thread until there is.
   1. releasePID(int pid) – Release the PID.

**Implementation:**

- **Manager**
  - Arraylist usingPidList – PidList of Pid in use
  - Arraylist PidList – PidList of available PID
  - Manager constructor
    - Put all available PID in the PidList 
  - getPID()
    - if no PID is available
      - pid = -1
    - if present PID is available 
      - pid = one random value of the PidList
      - remove that pid from PidList
      - add that pid to usingPidList
    - return pid
  - getPIDWait
    - if no PID is available
      - wait() – convert to current thread waiting state
    - if present PID is available 
      - pid = one random value of the PidList
      - remove that pid from PidList
      - add that pid to usingPidList
    - return pid
  - releasePID(int pid)
    - terminate the thread holding input pid
    - Remove current thread’s PID from usingPidList
    - Put the PID in the PidList.
  - **MyThread**
    - Implementation runnable interface.
    - MyThread constructor definition
    - Run() Method
      - If Way == 1 
        - pid = return value of Manager.getPID() 
      - else if way == 2
        - st = Time of ready\_made thread
        - pid = return value of getPIDWait()
        - wating\_time = Time to return from wait to ready
        - time = Time of thread running
      - if pid == -1
        - print none are available
      - else
        - if time < ife\_time\_program
          - start thread
          - simply doing nothing until it will be terminated
          - thread terminated
  - **ThreadTask class**
    - Int ith – variable for name of each thread.
    - Signalthread(int way, int time, int life\_time\_thread, int life\_time\_program, Map<Integer, Integer> randomtime)
      - Random time, as many threads as the corresponding time are created
      - Create thread object
      - Start Thread.start() that is thread task 
        - Start MyThread’s run() method
  - **Main**
    - Thread count, each thread life time, total process life time, getpid method input received
    - Generate random start times as many as the number of threads and put them in the Map<Integer, Integer> randomtime.
      - Timer task execution once per second by designating a timer
      - Timer task
        - If – Current process life time < total process life time
          - Current process life time
          - If – Randomtime has current process life time
            - Signalthread();
              - Pass the getpid method, current time, each thread life time, and the entire process life time
              - Send signal to threadtask



Test description and result

1. I tested the example of instructions of implementation assignment#2. First, we have to input number of threads, life time of each threads, life tome of the program, and the way of return PID. After that you can see the created sample time, and also, can see result of program that is consist of thread created and destroyed.

![](Aspose.Words.c8338ed8-1187-4538-be6c-140a10f62dfd.002.png)


2. In this case, the function of getPIDWait() was tested by reducing the number of available pids and increasing the number of threads to be created. Here, there are only 7 pids available. In other words, the thread after thread7 using last available pid value ​​for the first time waits until the previous thread is terminated.


![](Aspose.Words.c8338ed8-1187-4538-be6c-140a10f62dfd.001.png)
