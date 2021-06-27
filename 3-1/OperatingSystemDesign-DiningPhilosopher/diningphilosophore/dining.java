
//Use java threads to simulate the Dining Philosophers Problem
//YOUR NAME HERE.  17102063 Lee yoo seok Programming assignment 2 (from ece.gatech.edu) */

class dining {

	public static void main(String args[]) {
		System.out.println("Starting the Dining Philosophers Simulation\n");
		miscsubs.InitializeChecking();
		// Your code here...

		chopstick chop = new chopstick(); // Create chopstick object
		// Create a list for each philosopher thread
		Thread[] philosophers = new Thread[miscsubs.NUMBER_PHILOSOPHERS]; 
		
		// Initialize and start threads as many as NUMBER_PHILOSOPHERS
		for (int i = 0; i < miscsubs.NUMBER_PHILOSOPHERS; i++) {
			Phi phi = new Phi(i, chop);
			philosophers[i] = new Thread(phi);
			philosophers[i].start();
		}

		// Wait until the thread created above is terminated
		for (int i = 0; i < miscsubs.NUMBER_PHILOSOPHERS; i++) {
			try {
				philosophers[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// End of your code
		System.out.println("Simulation Ends..");
		miscsubs.LogResults();
	}
};
