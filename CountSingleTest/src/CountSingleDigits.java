import java.util.Random;
import java.util.stream.IntStream;

public class CountSingleDigits {
    public static void main(String[] args) {
        Random random = new Random();
        int[] counts = new int[101]; //Remember arrays start at 0

        IntStream.generate(() -> random.nextInt(100))
                .limit(100)
                .forEach(digit -> counts[digit]++);

        System.out.println("Digit counts:");
        IntStream.range(0, 101)
                .forEach(i -> System.out.printf("%d: %d%n", i, counts[i]));
                
    }
}


//I didn't know if you wanted it to display all 100 numbers but that's what I did :P
// I also could not get the numbers to exceed 3. I think I misunderstood the assignment maybe