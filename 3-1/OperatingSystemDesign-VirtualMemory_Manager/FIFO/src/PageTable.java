import java.util.LinkedList;

/**
 * Class to emulate a page table
 */
public class PageTable {
	PageTableItem[] table;
	LinkedList<Integer> pagelist;

	/**
	 * Constructor
	 *
	 * initialize PageTable with 256 items
	 */
	public PageTable() {
		table = new PageTableItem[256];
		pagelist = new LinkedList<Integer>(); // LinkedList that checks the order of entering the Page Table
		for (int i = 0; i < 256; i++) {
			this.table[i] = new PageTableItem(-1, false);
		}
	}

	public void FIFO(int f_num) {
		for (int i = 0; i < 256; i++) {
			if (this.table[i].frameNumber == f_num) {
				this.table[i].frameNumber = -1;
				this.table[i].valid = false;
			}
		}
		this.pagelist.removeFirst();

	}

	/**
	 * function to get frame number using page number
	 *
	 * @param int p_num Page Number to be queried
	 * @return int fraame number if it's in table, -1 otherwise
	 */
	public int get(int p_num) {
		int frameNumber = this.table[p_num].getFrameNumber();
		if (frameNumber == -1) {
			// frame number not in page table
			return -1;
		}
		return frameNumber;
	}

	/**
	 * function to add page number, frame number to PageTable
	 *
	 * @param p_num int Page number
	 * @param f_num int Frame number
	 */
	public void add(int p_num, int f_num) {
		this.table[p_num] = new PageTableItem(f_num, true);
		this.pagelist.add(f_num);
	}

}

/**
 * class for storing items of PageTable
 *
 * Because Java doesn't have struct, we use this class to emulate struct in C.
 */
class PageTableItem {
	/**
	 * variable to store frame number information
	 */
	int frameNumber;
	/**
	 * variable to store if the frame is in memory
	 *
	 * not used in this project
	 */
	boolean valid;

	/**
	 * Constructor
	 *
	 * @param i int frame number
	 * @param b boolean whether it's valid frame
	 */
	public PageTableItem(int i, boolean b) {
		this.frameNumber = i;
		this.valid = b;
	}

	/**
	 * Get the frame number of this PageTable item
	 *
	 * @return this.frameNumber
	 */
	public int getFrameNumber() {
		return this.frameNumber;
	}
}
