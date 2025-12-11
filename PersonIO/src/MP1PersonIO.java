
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Person implements Serializable {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

	@Override
    public String toString() {
        return "Person [name=" + name + ", age=" + age + "]";
    }
}

public class MP1PersonIO {
	String fileName;
	ObjectInputStream ois = null;
	ObjectOutputStream oos = null;
	static Scanner kbInput = new Scanner(System.in);
	private ArrayList<Person> persons = new ArrayList<>();
	

	public MP1PersonIO(String fileName) {
		this.fileName = fileName;
        try {
            
            ois = new ObjectInputStream(new FileInputStream(fileName));
            persons = (ArrayList<Person>) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
           
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error initializing: " + e.getMessage());
        }
	}

	public void add() {
        System.out.println("Please enter the person's name:");
        String name = kbInput.nextLine();
        
        System.out.println("Please enter the person's age:");
        int age = kbInput.nextInt();
        kbInput.nextLine(); // consume newline

        persons.add(new Person(name, age));
        saveToFile();
    }

    public void display() {
        System.out.println("***********************");
        if (persons.isEmpty()) {
            System.out.println("No persons found.");
        } else {
            for (Person person : persons) {
                System.out.println(person);
            }
        }
        System.out.println("***********************");
    }

	 private void saveToFile() {
        try {
            oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(persons);
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

	public static void main(String[] args)  {
		MP1PersonIO mp1 = new MP1PersonIO("person.ser");
		try {
			int option = -1;
			while (option != 0) {
				System.out.println("Please choose an option:");
				System.out.println("0: quit");
				System.out.println("1: add");
				System.out.println("2: display");
				option = kbInput.nextInt();
				kbInput.nextLine();
				switch (option) {
				case 0:
					System.out.println("Bye");
					break;					
				case 1:
					mp1.add();
					break;
				case 2:
					mp1.display();
					break;
				default:
					System.out.println("Invalid option!");
				}

			}
		} finally {
			try {
                if (mp1.oos != null) {
                    mp1.oos.close();
                }
                if (mp1.ois != null) {
                    mp1.ois.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing streams: " + e.getMessage());
		}

	}

  }
}
