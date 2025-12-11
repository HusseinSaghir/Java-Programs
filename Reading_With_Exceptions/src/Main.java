
public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java reading_with_exceptions.Main file1 file2 ...");
            return;
        }
        
        ReadingWithExceptions processor = new ReadingWithExceptions();
        for (String filename : args) {
            processor.process(filename);
            System.out.println(); // Add space between file outputs
        }
    }
}