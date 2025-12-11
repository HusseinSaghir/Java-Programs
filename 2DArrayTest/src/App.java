import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    public static void main(String[] args) {
        Integer[][] numberArray = {{34, 89}, {56, 3}, {27, 61}, {45, 8}, {45, 89}};
        
        String result = Stream.of(numberArray)
                           .flatMap(Stream::of) //Turns it into a single stream of numbers
                           .distinct() // Keeps only first occurrence of each number and removes any duplicates like the "45"
                           .sorted() // Literally just sorts out the remaining numbers
                           .map(String::valueOf)
                           .collect(Collectors.joining(" "));
        
        System.out.println(result);
    }
}





















// From Fudz: <3