
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

interface PIDManager {

	public static final int MIN_PID = 4;
	public static final int MAX_PID = 127;

	public int getPID();

	public int getPIDWait();

	public void releasePID(int pid);
}

class Manager implements PIDManager {
	// Pidlist of PID in use
	private ArrayList<Integer> usingPidList = new ArrayList<Integer>(10);
	// Pidlist of Available PID
	private ArrayList<Integer> PidList = new ArrayList<Integer>();

	public Manager() { // Put it in the PidList
		for (int i = MIN_PID; i < MAX_PID + 1; i++) {
			PidList.add(i);
		}

	}

	private Random randomGenerator;

	// Returns the current PID, -1 if no PID is available
	public synchronized int getPID() {
		int pid = 0;
		randomGenerator = new Random();
		// Check if there is a available PID in PIDList
		if (PidList.isEmpty()) {
			// Returns -1 if none PID
			return -1;
		} else {
			// If present, remove random PID from pidlist and add to usingPidList
			pid = PidList.get(randomGenerator.nextInt(PidList.size()));
			PidList.remove(PidList.indexOf(pid));
			usingPidList.add(pid);
		}

		// return PID
		return pid;
	}

	// Return current PID, if there is no PID available, wait for the thread until there is
	public synchronized int getPIDWait() {
		int pid = 0;
		randomGenerator = new Random();
		// Check if there is a available PID in PIDList
		while (PidList.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		// If present, remove random PID from pidlist and add to usingPidList
		pid = PidList.get(randomGenerator.nextInt(PidList.size()));
		PidList.remove(PidList.indexOf(pid));
		usingPidList.add(pid);
		// return PID
		return pid;
	}

	// Remove the thread that owns the PID
	public synchronized void releasePID(int pid) {
		// Remove the current thread's PID from usingPidList and put it in the PidList.
		PidList.add(pid);
		usingPidList.remove(usingPidList.indexOf(pid));
		notify();
	}
}

class MyThread implements Runnable {
	private Manager manager;
	private int time;
	private int pid;
	private int life_time_thread;
	private int life_time_program;
	private int way;
	private int ith;

	// Thread initialization
	public MyThread(int ith, int way, int time, Manager manager, int life_time_thread, int life_time_program) {
		this.manager = manager;
		this.time = time;
		this.life_time_thread = life_time_thread;
		this.life_time_program = life_time_program;
		this.way = way;
		this.ith = ith;
	}

	// Define the contents to be executed through thread
	public void run() {
		// Select either getPID or getPIDWait method.
		if (way == 1) {
			pid = manager.getPID();
		} else {
			long st = System.currentTimeMillis(); // start time of ready thread
			pid = manager.getPIDWait();
			// waiting_time
			long wating_time = (long) Math.round((System.currentTimeMillis() - st) / 1000.000);
			// Response_time that is real start time to do task
			time = (int) (time + wating_time);
		}
		if (pid == -1) {
			System.out.println("none are available");
			return;
		} else {
			// It adds the time to wait until there is an available PID.
			if (time < life_time_program) {
				System.out.printf("Thread" + ith + " created at Second " + (time) + "(PID: %d)\n",pid);
				// Start of thread's lifetime
				// Check if the current thread exceeds the life_time_program
				int end_time = time + life_time_thread;
				if (end_time > life_time_program) {
					life_time_thread = life_time_thread - (end_time - life_time_program);
					end_time = time + life_time_thread;
				}
				// Simply doing nothing until it will be destroyed.
				try {
					Thread.sleep(1000 * life_time_thread);
				} catch (Exception e) {
				}
				// Terminate the thread owning the PID
				System.out.printf("Thread" + ith + " destroyed at Second " + end_time+"(PID: %d)\n",pid);
				manager.releasePID(pid);
			}
		}
	}

}

class ThreadTask {
	// Manager object creation
	Manager manager = new Manager();
	// Initialize to give the pid a name
	private int ith = 0;

	// if threadtask get signal from timer
	public void signalthread(int way, int time, int life_time_thread, int life_time_program,
			Map<Integer, Integer> randomtime) {
		for (int j = 0; j < randomtime.get(time); j++) {
			// In order not to duplicate each pid name
			ith++;
			// Initialize mythread with pid name that is ith.
			MyThread mythread = new MyThread(ith, way, time, manager, life_time_thread, life_time_program);
			// Create new thread
			Thread thread = new Thread(mythread);
			// Let the thread start executing.
			thread.start();
		}
	}
}

//test class
public class Assignment2 {
	public static int time;
	public static int thread_num;
	public static int life_time_thread;
	public static int life_time_program;
	public static int way;

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		// the number of threads created
		do {
			System.out.println("the number of threads created : ");
			System.out.println("Please input positive number");
			while (!sc.hasNextInt()) {
				System.out.println("That's not a Number!");
				System.out.println("the number of threads created : ");
				sc.next(); 
			}
			thread_num = sc.nextInt();
		} while (thread_num <= 0);
		System.out.println();

		// life time of each threads
		do {
			System.out.println("life time of each threads : ");
			System.out.println("Please input positive number");
			while (!sc.hasNextInt()) {
				System.out.println("That's not a Number!");
				System.out.print("life time of each threads : ");
				sc.next(); 
			}
			life_time_thread = sc.nextInt();
		} while (life_time_thread <= 0);
		System.out.println();

		// life time of the program
		do {
			System.out.println("life time of the program : ");
			System.out.println("Please input positive number");
			while (!sc.hasNextInt()) {
				System.out.println("That's not a Number!");
				System.out.print("life time of the program : ");
				sc.next(); 
			}
			life_time_program = sc.nextInt();
		} while (life_time_program <= 0);
		System.out.println();

		// choose the way of return PID : getPID or getPIDWait
		do {
			System.out.println("choose the way of return PID --ex) 2");
			System.out.println("1)getPID\n");
			System.out.println("2)getPIDWait\n");
			while (!sc.hasNextInt()) {
				System.out.println("That's not a Number!");
				System.out.println("choose the way of return PID --ex) 2");
				System.out.println("1)getPID\n");
				System.out.println("2)getPIDWait\n");
				sc.next(); 
			}
			way = sc.nextInt();
		} while (way!=1 && way!=2);
		
		System.out.println("Test program is initialized with "+thread_num+"thread and "+life_time_program+" seconds,"
				+ " with the life time "+life_time_thread+" seconds of each thread");
		
		// Create a threadtask object to signal the creation of a thread object.
		ThreadTask threadtask = new ThreadTask();

		// Create an array of random start times between 1 and life time of program as
		// many as the number of threads
		Map<Integer, Integer> randomtime = new HashMap<Integer, Integer>();
		for (int k = 1; k < thread_num + 1; k++) {
			Integer a = Integer.valueOf((int) (Math.random() * life_time_program + 1));
			randomtime.put(a, randomtime.getOrDefault(a, 0) + 1);
		}
		System.out.println(randomtime);

		time = 0;
		Timer m_timer = new Timer();
		// Execute the timer task once every second.
		TimerTask m_task = new TimerTask() {
			public void run() {
				if (time < life_time_program) {
					time++;
					// When the current time is at the generated randomtime
					if (randomtime.containsKey(time)) {
						// send signal to threadtask
						threadtask.signalthread(way, time, life_time_thread, life_time_program, randomtime);
					}
				} else {
					// Exceeding the program's lifetime
					System.out.println("------------ The End...! ------------");
					System.out.println(life_time_program + "seconds has passed¡¦ Program ends");
					m_timer.cancel();
					System.exit(0);
				}
			}
		};
		m_timer.schedule(m_task, 0, 1000);
	}
}
