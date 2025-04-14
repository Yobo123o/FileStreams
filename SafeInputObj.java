import java.util.Scanner;

/**
 * SafeInputObj class: Provides safe input methods for console input.
 * This version uses instance methods instead of static methods.
 */
public class SafeInputObj {
    private Scanner pipe;

    /**
     * Default constructor using System.in as the scanner input.
     */
    public SafeInputObj() {
        this.pipe = new Scanner(System.in);
    }

    /**
     * Constructor allowing a custom Scanner input.
     * @param scanner A Scanner object to use for input.
     */
    public SafeInputObj(Scanner scanner) {
        this.pipe = scanner;
    }

    /**
     * Get a String which contains at least one character.
     * @param prompt Prompt for the user.
     * @return A String response that is not zero length.
     */
    public String getNonZeroLenString(String prompt) {
        String retString = "";
        do {
            System.out.print("\n" + prompt + ": ");
            retString = pipe.nextLine();
        } while (retString.length() == 0);
        return retString;
    }

    /**
     * Get an int value within a specified numeric range.
     * @param prompt Input prompt message.
     * @param low Low end of the inclusive range.
     * @param high High end of the inclusive range.
     * @return An int value within the inclusive range.
     */
    public int getRangedInt(String prompt, int low, int high) {
        int retVal = 0;
        boolean done = false;
        do {
            System.out.print("\n" + prompt + " [" + low + "-" + high + "]: ");
            if (pipe.hasNextInt()) {
                retVal = pipe.nextInt();
                pipe.nextLine();
                if (retVal >= low && retVal <= high) {
                    done = true;
                } else {
                    System.out.println("Number is out of range [" + low + "-" + high + "]: " + retVal);
                }
            } else {
                System.out.println("You must enter an int: " + pipe.nextLine());
            }
        } while (!done);
        return retVal;
    }

    /**
     * Get a double value within an inclusive range.
     * @param prompt Input prompt message.
     * @param low Low value inclusive.
     * @param high High value inclusive.
     * @return A double value within the specified inclusive range.
     */
    public double getRangedDouble(String prompt, double low, double high) {
        double retVal = 0;
        boolean done = false;
        do {
            System.out.print("\n" + prompt + " [" + low + "-" + high + "]: ");
            if (pipe.hasNextDouble()) {
                retVal = pipe.nextDouble();
                pipe.nextLine();
                if (retVal >= low && retVal <= high) {
                    done = true;
                } else {
                    System.out.println("Number is out of range [" + low + "-" + high + "]: " + retVal);
                }
            } else {
                System.out.println("You must enter a double: " + pipe.nextLine());
            }
        } while (!done);
        return retVal;
    }

    /**
     * Get a [Y/N] confirmation from the user.
     * @param prompt Input prompt message for user.
     * @return True for yes, false for no.
     */
    public boolean getYNConfirm(String prompt) {
        boolean retVal = false;
        String response = "";
        boolean valid = false;
        do {
            System.out.print("\n" + prompt + " [Y/N]: ");
            response = pipe.nextLine();
            if (response.equalsIgnoreCase("Y")) {
                valid = true;
                retVal = true;
            } else if (response.equalsIgnoreCase("N")) {
                valid = true;
                retVal = false;
            } else {
                System.out.println("You must answer [Y/N]: " + response);
            }
        } while (!valid);
        return retVal;
    }

    /**
     * Get a string that matches a regex pattern.
     * @param prompt Prompt for the user.
     * @param regExPattern Java style regex pattern.
     * @return A String that matches the regex pattern supplied.
     */
    public String getRegExString(String prompt, String regExPattern) {
        String response = "";
        boolean valid = false;
        do {
            System.out.print("\n" + prompt + ": ");
            response = pipe.nextLine();
            if (response.matches(regExPattern)) {
                valid = true;
            } else {
                System.out.println(response + " must match the pattern " + regExPattern);
            }
        } while (!valid);
        return response;
    }
}
