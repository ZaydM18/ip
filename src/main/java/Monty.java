import java.util.Scanner;

public class Monty {
    private static void longDash() {
        System.out.println("____________________________________________________________");
    }
    public static void main(String[] args) {
        longDash();
        System.out.println(" Hello! I'm Monty");
        System.out.println(" What can I do for you?");
        longDash();

        Scanner sc = new Scanner(System.in);

        while (true) {
            String userInput = sc.nextLine().trim();

            if (userInput.equalsIgnoreCase("bye")) {
                longDash();
                System.out.println(" Bye. Hope to see you again soon!");
                longDash();
                break;
            }

            longDash();
            System.out.println(" " + userInput);
            longDash();
        }

    }
}
