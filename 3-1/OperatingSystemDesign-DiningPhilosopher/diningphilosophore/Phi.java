
public class Phi implements Runnable {
	int ith = 0;
	chopstick chop;
	
	// Condition variable for waiting for threads that do not have chopsticks to use
	static Object condition_variable = new Object(); 
	static Object cv_for_max = new Object(); // Condition variable for proper termination

	public Phi(int i, chopstick chop) {
		this.ith = i;
		this.chop = chop;
	}

	public void run() {

		// loop when TotalEats is less than MAX_EATS
		while (miscsubs.TotalEats < miscsubs.MAX_EATS) {
			// delay for Thinking state
			miscsubs.RandomDelay();
			// after random delay, change state of ith thread into Hungry

			// Check until there are available chopsticks on both sides 
			// both sides - (ith and ((i+1) % total number of chopsticks)th)
			while (!chop.pickup(ith)) {
				try {
					synchronized (condition_variable) {
						// Wait if there are no chopsticks available
						condition_variable.wait();
					}
				} catch (InterruptedException e) {
				}
			}

			// If you have both chopsticks available
			// start eating
			synchronized (cv_for_max) {
				if (miscsubs.TotalEats < miscsubs.MAX_EATS) {
					// state of ith thread will be Eating
					miscsubs.StartEating(ith);
					miscsubs.RandomDelay();
				}
			}

			// When finished, put your chopsticks down
			// state of ith thread will be Thinking
			miscsubs.DoneEating(ith);
			chop.putdown(ith);
			
			synchronized (condition_variable) {
				// Wake up another waiting thread to once again check for possible chopsticks
				condition_variable.notifyAll();
			}

		}
	}
}
