
public class chopstick {

	// list of chopsticks to indicate whether each chopstick is used or not
	boolean[] chopsticks; 
	private final int num_chops = miscsubs.NUMBER_CHOPSTICKS;

	chopstick() {
		chopsticks = new boolean[num_chops]; // Specify the size of the chopstick array
	}

	// Chopstick pair inspection of the i-th and ((i+1)% total number of chopsticks)th
	public synchronized boolean pickup(int ith) {

		if (chopsticks[ith] || chopsticks[(ith + 1) % num_chops]) {
			// Returns false if either of the chopsticks is in use
			return false;
		} else {
			// If both chopsticks are not in use
			// Changes the use of chopsticks to being used and returns true
			chopsticks[ith] = true;
			chopsticks[(ith + 1) % num_chops] = true;
			return true;
		}

	}

	public synchronized void putdown(int ith) {
		// Change the use of  chopsticks to being not used
		chopsticks[ith] = false;
		chopsticks[(ith + 1) % num_chops] = false;

	}

}
