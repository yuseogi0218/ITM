import java.io.*;
import java.util.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SimpleShell {
	public static void main(String[] args) throws java.io.IOException {
		String commandLine;
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		String commandadd;

		// initial directory
		File startDir = new File(System.getProperty("user.dir"));
		File absDir = new File(System.getProperty("user.home")); // /home/user
		// current running directory
		String path = startDir.toString();

		// 13, for history feature, make a arraylist
		ArrayList<String> historycommand = new ArrayList<String>();
		int history_count = 0;

		// we break out with <control><C>
		outloop: while (true) {
			// read what the user entered
			System.out.print("jsh>");
			commandLine = console.readLine();

			inloop: while (true) { // loop for history !<number> and history !!
				// 4, by using split() method, split userinput based on a single space
				String[] commandSplit = commandLine.split(" ");

				// 4,5 by using ArrayList, save the result of split into a List of Strings
				ArrayList<String> commandList = new ArrayList<String>();
				int size = commandSplit.length;
				for (int i = 0; i < size; i++) {
					commandList.add(commandSplit[i]);
				}

				// if the user entered a return, just loop again
				if (commandLine.equals("")) {
					continue outloop;
					// 9. if the command entered is "exit" or "quit" the shell exits the program.
				} else if (commandLine.equals("exit") || commandLine.equals("quit")) {
					System.out.println("Goodbye.");
					System.exit(0);

					// 13, Adding a history feature
				} else if (commandList.get(0).equals("history")) {
					// for total of history
					if (commandList.size() == 1) {
						historycommand.add(commandLine);
						history_count++;
						for (int j = 0; j < history_count; j++) {
							System.out.println(j + " " + historycommand.get(j));
						}
						continue outloop;
						// for history !! that execute previous
					} else if (commandList.get(1).equals("!!")) {
						if (history_count == 0) {
							System.out.println("There is not previous command");
							continue outloop;
						}
						commandList.clear();
						commandList.addAll(Arrays.asList(historycommand.get(history_count - 1).split(" ")));
						commandLine = String.join(" ", commandList);
						continue inloop;
						// for history !<number> that execute history of input number
					} else if (commandList.get(1).contains("!")) {
						int a = Integer.parseInt(commandList.get(1).substring(1));
						commandList.clear();
						commandList.addAll(Arrays.asList(historycommand.get(a).split(" ")));
						commandLine = String.join(" ", commandList);
						continue inloop;
						// for history <number> that print history as many as the input number
					} else if (isInteger(commandList.get(1))) {
						int l = Integer.parseInt(commandList.get(1));
						if (l <= history_count) {
							historycommand.add(commandLine);
							history_count++;
							for (int m = l; m > 0; m--) {
								System.out.println(history_count - m + " " + historycommand.get(history_count - m));
							}
							continue outloop;
						} else {
							System.out.println("You enter more number than num of history that exist");
							continue outloop;
						}
					} else {
						System.out.println("You enter invalid input");
						continue outloop;
					}
				}

				// 10, change directories unsing the cd command.
				// cd follwed by pwd -> /home/user
				if (commandList.get(0).equals("cd")) {
					if (commandList.size() == 1) {
						path = absDir.toString();
					} else {
						String dir = commandList.get(1);
						if(dir.substring(0,1).equals("./")) {
							dir = dir.substring(2);
						}
						// 11, make a program working with relative paths.
						File f = new File(path + "/" + dir);
						File exists_d = new File(dir);
						if (f.exists() && f.isDirectory()) {
							path = path + "/" + dir;
							// also, work "cd .."
						} else if (dir.equals("..")) {
							Path parent_path = Paths.get(path).getParent();
							path = parent_path.toString();
							// Put the newly entered directory in the path.
						} else if (exists_d.exists() && exists_d.isDirectory()) {
							path = dir;
							System.out.println("current directory : " + path);
							// 12, deal with invalid path input
						} else {
							System.out.println("You enter invalid path");
						}
					}

					commandadd = String.join(" ", commandList);
					historycommand.add(commandadd);
					history_count++;
					continue outloop;
				}

				// 6, create ProcessBuilder object
				ProcessBuilder pb = new ProcessBuilder(commandList);

				try {
					// 10, using directory method, change directory into path
					pb.directory(new File(path));

					Process process = pb.start();
					// 7, create a BufferedReader to read the output string of the process builder object.
					//  obtain the input stream
					InputStream is = process.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isr);

					// read the output of the process
					String line;
					while ((line = br.readLine()) != null)
						System.out.println(line);

					// for history
					commandadd = String.join(" ", commandList);
					historycommand.add(commandadd);
					history_count++;
					br.close();
					continue outloop;
				} catch (Exception e) {
					continue outloop;
				}
			}

		}

	}

	static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}