**Readme**

**17102063 Lee you seok**

**Simple Shell**

- a simple shell in Java using processBuilder to run almost all CMD/Terminal Commands. Like "cd", "ls", "pwd", "history" or any kind of command.

**Install**

- Basically, Java is not installed in Ubuntu. So user must install java.
  - sudo apt-get install openjdk-8-jre
  - sudo apt-get install openjdk-8-jdk

**usage**

- **To run**
  - Compile java file by entering “javac SimpleShell.java”
  - User can see that SimpleShell.class file created
  - Enter “java SimpleShell”
- **Each function**
  - **cd**
    - type “cd”
      - you are able to change directory to /home/user/
    - type “cd /home” 
      - you are able to change directory to /home
    - type “cd user”
      - you are able to change directory to /home/user (relative path is working)
    - type “cd any existing folder” or “cd ./any existing folder”
      - you are able to change directory to current directory + input directory
    - type “cd fakeDirectory”
      - you get a error message for invalid path
  - **history**
    - type “history”
      - print out the contents of all history of commands
    - type “history <number>”
      - print out the contents of the number most recently commands
    - type “history !!”
      - run the previous command in the history
    - type “history !<number>
      - run the numberth* command in the history
  - **other OSProcess**
    - type “pwd” of “ls” or “cat test.txt”
- **To terminate the program**
  - type “exit” or “quit”
