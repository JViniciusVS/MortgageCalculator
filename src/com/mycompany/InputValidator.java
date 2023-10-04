package com.mycompany;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

/**
 * The InputValidator class provides utility methods for validating user input.
 * It includes methods for validating integers and floating-point numbers within specified ranges.
 */
public class InputValidator {
    static Scanner scanner = new Scanner(System.in);

    /**
     * Prompts the user for an integer input within the specified range and returns the validated input.
     *
     * @param prompt The message displayed to the user as a prompt.
     * @param min    The minimum valid integer value.
     * @param max    The maximum valid integer value.
     * @return The validated integer input from the user.
     */
    public static int getValidInteger (String prompt, int min, int max) {
        int userInput;

        while (true) {
            try {
                System.out.print(prompt); // Use print() instead of println() to keep the prompt and user input on the same line.
                scanner.useLocale(Locale.US);
                userInput = scanner.nextInt();

                if (userInput >= min && userInput <= max) {
                    break; // valid input, get out from loop.
                }
                else {
                    System.out.println("Please enter a value between " + min + " and " + max + ".");
                }

            } catch (InputMismatchException e) { // 'e' is the variable that represents the captured exception.
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume the invalid input to prevent an infinite loop.
                /*
                 The catch block captures the expected exception, changes its behavior to prevent automatic program termination, and allows the program to continue its normal execution, including repeating the while loop until a valid value is entered.
                 The issue is that the invalid value remains in the input buffer because when the exception was thrown during the scanner's call, the scanner did not automatically read the invalid value, thus it was not discarded.
                 Therefore, a second scanner within the catch block is necessary to consume and discard this invalid value. Without it, the program would enter an infinite loop since the invalid value would still be present in the input buffer and would be read repeatedly.
                */
            } catch (Exception e) {
                System.out.println("An unexpected error occurred. Please enter a valid number.");
                scanner.nextLine();
            }
        }
        return userInput;
    }

    /**
     * Prompts the user for a floating-point input within the specified range and returns the validated input.
     *
     * @param prompt The message displayed to the user as a prompt.
     * @param min    The minimum valid float value.
     * @param max    The maximum valid float value.
     * @return The validated floating-point input from the user.
     */
    public static float getValidFloat (String prompt, int min, int max) {
        float userInput;

        while (true) {
            try {
                System.out.print(prompt);
                scanner.useLocale(Locale.US);
                userInput = scanner.nextFloat();

                if (userInput >= min && userInput <= max) {
                    break;
                }
                else {
                    System.out.println("Please enter a value between " + min + " and " + max + ".");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();

            } catch (Exception e) {
                System.out.println("An unexpected error occurred. Please enter a valid number.");
                scanner.nextLine();
            }
        }
        return userInput;
    }
}
