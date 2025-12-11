
import java.util.*;
import java.util.stream.*;


public class ScrabbleChecker { //create a map that stores the point value for each letter
    private static final Map<Character, Integer> letterValues = new HashMap<>();

    static {
        letterValues.put('a', 1);
        letterValues.put('b', 3);
        letterValues.put('c', 3);
        letterValues.put('d', 2);
        letterValues.put('e', 1);
        letterValues.put('f', 4);
        letterValues.put('g', 2);
        letterValues.put('h', 4);
        letterValues.put('i', 1);
        letterValues.put('j', 8);
        letterValues.put('k', 5);                      //You're doing great work!
        letterValues.put('l', 1);
        letterValues.put('m', 3);
        letterValues.put('n', 1);
        letterValues.put('o', 1);
        letterValues.put('p', 3);
        letterValues.put('q', 10);
        letterValues.put('r', 1);
        letterValues.put('s', 1);
        letterValues.put('t', 1);
        letterValues.put('u', 1);
        letterValues.put('v', 8);
        letterValues.put('w', 4);
        letterValues.put('x', 8);
        letterValues.put('y', 4);
        letterValues.put('z', 10);
    }



public static int calculationsForScore (String word) {
    return word.toLowerCase().chars() //We have to convert to lowercase since our map uses those keys
        .map(c -> letterValues.getOrDefault((char)c, 0)) //Maps each character to a value
        .sum(); 

}

    public static void main(String[] args) throws Exception {
        List <String> words = Arrays.asList("Java", "program", "list", 
        "string", "unix", "hours", "syntax", "error"); //Just creating an array for the words
    



List <Map.Entry<String, Integer>> wordScore = words.stream()  // 
    .map(word -> Map.entry(word, calculationsForScore(word)))
    .collect(Collectors.toList()); // Creates list for words scored in pairs


    System.out.println("The Top Three Words Are: ");
    wordScore.stream() // Finds top 3 words
      .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())) //e1 stands for "first entry" 
      .limit(3) // Keeps only the first 3 entries 
      .forEach(e -> System.out.println(e.getKey() + ":" + e.getValue()));
      

      
        double average = wordScore.stream()
        .mapToInt(Map.Entry::getValue)
        .average()
        .orElse(0.0); //Handles empty case if no average exists
        System.out.println("Average score is: " + average);



        List<String> belowAverage = wordScore.stream() //Converts wordScore into a stream
            .filter (e -> e.getValue() < average) // "e" represents each entry and "getValue" gets the score
            .map(Map.Entry::getKey) //Just gets the word
            .collect(Collectors.toList());

            List<String> aboveAverage = wordScore.stream()
            .filter (e -> e.getValue() > average)
            .map(Map.Entry::getKey) // is a shortcut for e -> e.getKey()
            .collect(Collectors.toList()); 
            
            System.out.println("words below average:" + belowAverage);
            System.out.println("words above average:" + aboveAverage);
  }

}



/* 
                       
 _________        .---"""-------"""---.              
:______.-':      :  .--------------.  :             
| ______  |      | :                : |             
|:______B:|      | |     Error:     | |             
|:______B:|      | |                | |             
|:______B:|      | |  404 Java JDK  | |             
|         |      | |  not found.    | |             
|:_____:  |      | |                | |             
|    ==   |      | :                : |             
|       O |      :  '--------------'  :             
|       o |      :'---...______...---'              
|       o |-._.-i___/'             \._              
|'-.____o_|   '-.   '-...______...-'  `-._          
:_________:      `.____________________   `-.___.-. 
                .'.eeeeeeeeeeeeeeeeee.'.      :___:
              .'.eeeeeeeeeeeeeeeeeeeeee.'.         
             :____________________________:

*/