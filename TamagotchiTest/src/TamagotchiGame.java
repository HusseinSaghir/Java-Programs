import java.util.Scanner;

public class TamagotchiGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create a Tamagotchi
        System.out.println("\nWelcome to Tamagotchi!");
        System.out.print("Enter your Tamagotchi's name: ");
        String name = scanner.nextLine();
        Tamagotchi tamagotchi = new Tamagotchi(name);
    

    //GamePlay 
    //We make a do while loop so the game plays as long as our pet is alive
    //If our pet dies the it will do a game over screen along with stats
    while (tamagotchi.isAlive()) {
        System.out.println(tamagotchi); //<---- come back to this incase it does not print status

        //Displaying the menu and what you can interact with
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. Feed "+ tamagotchi.getName());
        System.out.println("2. Play with "+ tamagotchi.getName());
        System.out.println("3. Put " + tamagotchi.getName() + " to sleep");
        System.out.println("4. Do nothing I guess -.-");

        //creating a choice variable
        int choice = scanner.nextInt();

        //We create a switch statement that reacts to our decisions
        switch (choice) {
            case 1 -> { tamagotchi.feed();
                }
            case 2 -> { tamagotchi.play();
                }
            case 3 -> { tamagotchi.sleep();
                }
            case 4 -> { System.out.println("\nYou did nothing -.-");
                }
        
            default -> { System.out.println("\nInvalid choice, please choose again");
                }
        }

        //pass time feature which happens after each action
        tamagotchi.passTime();
    }
    
    //create life checker to finish loop and give game over
    System.out.println("\nOh no! " + tamagotchi.getName() + " has passed away....");
    System.out.println("Final Stats: " + tamagotchi);
    scanner.close();
    }
  }