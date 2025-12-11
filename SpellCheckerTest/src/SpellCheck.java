import java.io.*;
import java.util.*;
/*
 * which contains utility classes like collections, data structures, and helper tools
 * used for input/output (I/O) operations like 
 * reading/writing files, streams, and handling file system interactions
 */

public class SpellCheck {
    // HashSet for dictionary words - fast lookup
    private HashSet<String> dictionary = new HashSet<String>();
    // TreeSet for misspelled words - keeps them sorted
    private TreeSet<String> miss_spelled_words = new TreeSet<String>();
    // Scanner for user input
    private Scanner userInput = new Scanner(System.in);

    public SpellCheck() throws FileNotFoundException {
        // Load dictionary words from file
        Scanner dictScanner = new Scanner(new File("dictionary.txt"));
        while (dictScanner.hasNext()) {
            String word = dictScanner.next().toLowerCase();
            dictionary.add(word);
        }
        dictScanner.close();
    }

    public void checkSpelling(String fileName) throws FileNotFoundException {
        System.out.println("======== Spell checking " + fileName + " =========");
        // Clear previous misspelled words for this file
        miss_spelled_words.clear();
        
        // Read the input file line by line
        Scanner fileScanner = new Scanner(new File(fileName));
        int lineNumber = 0;
        
        while (fileScanner.hasNextLine()) {
            lineNumber++;
            String line = fileScanner.nextLine();
            // Split line into words using spaces and punctuation as delimiters (a character or set of characters that separates or marks the boundaries between different pieces of data within a string, file, or other data structure)
            String[] words = line.split(" +|\\p{Punct}");
            boolean lineHasMisspelled = false;
            
            for (String word : words) {
                if (word.isEmpty()) continue;
                
                String lowerWord = word.toLowerCase();
                
                // Skip words that don't start with a letter
                if (!Character.isLetter(lowerWord.charAt(0))) continue;
                
                // Check if word is in dictionary or already marked as misspelled
                if (dictionary.contains(lowerWord)) continue;
                if (miss_spelled_words.contains(lowerWord)) continue;
                
               
                    
                
                
                // If we get here, the word is unrecognized
                if (!lineHasMisspelled) {
                    System.out.println("line number:" + lineNumber);
                    lineHasMisspelled = true;
                }
                
                /*
                 * Asks the user whether
                 * to add the misspelled word to the dictionary or mark it as incorrect.
                 */
                System.out.print(word + " add to dictionary? y or n\n");
                String response = userInput.nextLine().toLowerCase();
                
                if (response.equals("y")) {
                    dictionary.add(lowerWord);
                } else {
                    miss_spelled_words.add(lowerWord);
                }
            } // I need to consider adding case sensitivity checkers 
        }
        fileScanner.close();
    }

    // Prints all misspelled words (stored in the TreeSet) in alphabetical order
    public void dump_miss_spelled_words() {
        System.out.println("======miss spelled words======");
        for (String word : miss_spelled_words) {
            System.out.println(word);
        }
    }

    public static void main(String[] args) {
        try {
            SpellCheck spellCheck = new SpellCheck();
            
            // Process each file passed as command line argument
            for (String filename : args) {
                spellCheck.checkSpelling(filename);
                spellCheck.dump_miss_spelled_words();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}