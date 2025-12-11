
import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ReadingWithExceptions {
    
    public void process(String inputFilename) {
        Scanner inputScanner = null;
        PrintWriter outputWriter = null;
        
        try {
            // Step 1: Try to open the input file
            File inputFile = new File(inputFilename);
            inputScanner = new Scanner(inputFile);

            // Step 2: Read the first line for output filename and number to read
            String firstLine = inputScanner.nextLine();
            String[] firstLineParts = firstLine.split("\\s+", 2);
            
            if (firstLineParts.length < 2) {
                System.out.println("Error in " + inputFilename + ": First line must contain output filename and number to read");
                return;
            }

            String outputFilename = firstLineParts[0];
            String numberToReadStr = firstLineParts[1];
            int numberToRead = 0;
            
            // Parse the number to read
            try {
                numberToRead = Integer.parseInt(numberToReadStr);
                if (numberToRead < 0) {
                    System.out.println("Error in " + inputFilename + ": Number to read is negative, will read all available numbers");
                    numberToRead = Integer.MAX_VALUE;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error in " + inputFilename + ": Invalid number format '" + numberToReadStr + "', will read all available numbers");
                numberToRead = Integer.MAX_VALUE;
            }

            // Step 3: Create output file and write numbers
            outputWriter = new PrintWriter(new FileWriter(outputFilename));
            
            int numbersWritten = 0;
            while (inputScanner.hasNext() && numbersWritten < numberToRead) {
                try {
                    int num = inputScanner.nextInt();
                    outputWriter.print(num);
                    numbersWritten++;
                    
                    if (numbersWritten % 10 == 0) {
                        outputWriter.println();
                    } else if (numbersWritten < numberToRead) {
                        outputWriter.print(" ");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error in " + inputFilename + ": Bad input, skipping non-integer value");
                    inputScanner.next(); // skip the bad token
                }
            }
            if (numbersWritten < numberToRead && numberToRead != Integer.MAX_VALUE) {
                System.out.println("Warning in " + inputFilename + ": Only found " + numbersWritten + " numbers when expecting " + numberToRead);
            }
            
            // Close the output file
            outputWriter.close();
            System.out.println(outputFilename + " created with the following output:");
            
            // Step 5: Open the output file and print to screen
            Scanner outputScanner = new Scanner(new File(outputFilename));
            while (outputScanner.hasNextLine()) {
                System.out.println(outputScanner.nextLine());
            }
            outputScanner.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("Error: Unable to open input file '" + inputFilename + "'");
        } catch (IOException e) {
            System.out.println("Error: IO Exception while processing '" + inputFilename + "'");
        } finally {
            if (inputScanner != null) {
                inputScanner.close();
            }
            if (outputWriter != null) {
                outputWriter.close();
            }
        }
    }
}
    
