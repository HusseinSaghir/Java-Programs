import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

/*
 * Used to read, write, or check properties (e.g., existence, permissions) of files
 * A checked exception thrown when a file does not exist or cannot be accessed
 * Parses input from files, strings, or System.in
 * Provides methods like push() (add to top), pop() (remove from top), and peek()
 */

// This exception is thrown when bad indentation is found
class BadIndentationException extends RuntimeException {
    BadIndentationException(String error) {
        super(error); // Calls the parent class constructor with the error message
    }
}

public class IndentChecker {
    // Stack to keep track of indentation levels
    Stack<Integer> indentStack = new Stack<Integer>();
    // Flag to track if we found any errors
    private boolean hasError = false;

    // Finds the first non-space character in a line
    private int findFirstNonBlank(String line) {
        for (int i = 0; i <= line.length(); i++) {
            if (!Character.isWhitespace(line.charAt(i))) {
                return i; // Returns the position of first real character
            }
        }
        return -1; // Returns -1 if line is empty/whitespace only
    }

    // Checks indentation for a single line
    private void processLine(String line, int lineNumber) {
        // Get position of first real character
        int index = findFirstNonBlank(line);
        
        // Skip blank lines
        if (index == -1) {
            return;
        }
        
        // If first line with code, remember its indentation
        if (indentStack.isEmpty()) {
            indentStack.push(index);
            return;
        }
        
        // If more indented than previous line, remember new 
        if (index > indentStack.peek()) {
            indentStack.push(index);
            return;
        }
        
        // If same indentation as previous line, it's okay
        if (index == indentStack.peek()) {
            return;
        }
        
        // If less indented, remove deeper from stack
        while (!indentStack.isEmpty() && indentStack.peek() > index) {
            indentStack.pop();
        }
        
        // After removing , check if indentation matches
        if (!indentStack.isEmpty() && indentStack.peek() != index) {
            throw new BadIndentationException("Bad indentation at line #:" + lineNumber);
        }
    }

  
    public void checkIndentation(String fileName) {
        // Reset for new file
        indentStack.clear();
        hasError = false;
        
        Scanner input = null;
        try {
            // Open the file
            input = new Scanner(new File(fileName));
            int lineNumber = 0;
            
            // Read each line until end of file or first error
            while (input.hasNextLine() && !hasError) {
                lineNumber++;
                String line = input.nextLine();
                // Print line with its number
                System.out.println(lineNumber + ":" + line);
                try {
                    // Check this line's indentation
                    processLine(line, lineNumber);
                } catch (BadIndentationException e) {
                    // If error found, print it and remember we had an error
                    System.out.println(e);
                    hasError = true;
                }
            }
            
            // Print final result for this file
            if (hasError) {
                System.out.println(fileName + " has indentation errors.");
            } else {
                System.out.println(fileName + " is properly indented.");
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Can't open file:" + fileName);
        } 
        finally {
            // Always close the file when done
            if (input != null)
                input.close();
        }
    }

  
    public static void main(String[] args) {
        // Create checker object
        IndentChecker indentChecker = new IndentChecker();
        // Check each file listed in command line arguments
        for (int i = 0; i <= args.length; i++) {
            System.out.println("Processing file: " + args[i]);
            indentChecker.checkIndentation(args[i]);
            System.out.println(); // Blank line between files
        }
    }
}