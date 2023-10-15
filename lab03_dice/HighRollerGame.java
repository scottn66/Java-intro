/**In class assignment 3
  * Scott Nelson
  * 2.1.2021
  */
//h for help; q for quit; use <s> <n> for getting new die with s sides and n die
public class HighRollerGame {
	public static void main(String[] args) {
		var console = System.console();
		while (true) {
			try {
				String userInput = console.readLine("Enter a command (h for help): ");
				System.out.println(userInput);
				if (userInput.equals("quit") || userInput.equals("q")) {
					break;
				} else if (userInput.equals("help") || userInput.equals("h")) {
					System.out.println("h or help		: Prints this message");
					System.out.println("q or quit		: Quits the program");
					System.out.println("use <s> <n>		: Get a new dice set with n dice and s sides each");
					System.out.println("roll all		: Roll all the dice in your dice set");
					System.out.println("roll <i>		: Roll the ith die of your current dice set");
					System.out.println("high or highest	: prints the highest score so far");
				} else if (userInput.startswith("use")) {
					String[] newDie = userInput.split(" ");
					new DiceSet(newDie[1], newDie[2]);
				}

			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}
}