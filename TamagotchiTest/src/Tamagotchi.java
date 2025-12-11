
class Tamagotchi {

    //These can be private or private but for sake of encapsulation and
    //control of access we will make some of them private

    //Instance variables below

    public String name; //ChiChi
    public int hunger; // 0 = Starving, 50 = Hungry, 100 = Full
    public int happiness; // 0 = Sad, 50 = Good, 100 = Verry Happy
    public int energy; // From 0 to 100
    public int age; // In days
    public String status; // Condition of pet
    

    //Constructors
    public Tamagotchi(String name) {
        this.name = name;
        this.hunger = 50;
        this.happiness = 50;
        this.energy = 50;
        this.age = 0;
    }

    //Getters 

    public String getName() {
        return name;
    }

    public int getHunger() {
        return hunger;
    }

    public int getHappiness() {
        return happiness;
    }

    public int getEnergy() {
        return energy;
    }

    public int getAge() {
        return age;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setAge(int age) {
        this.age = age;
    }



    //interactions 
    
    public void feed() {
        //Effects of feed
        happiness += 10;
        energy += 20;
        if (happiness > 100) happiness = 100;
        if (energy > 100) energy = 100;
        //Output message
        System.out.println(name + " has been fed. hunger is now "+ hunger);
    }

    public void play() {
        //Effects of play
        happiness += 10;
        energy -= 20;
        if (happiness > 100) happiness = 100;
        if (energy < 0) energy = 0;
        //Output message
        System.out.println(name +" played with you! Happiness is now "+happiness);
    }

    public void sleep() {
        //Effects of sleep
        energy += 30;
        if (energy > 100) energy = 100;
        //output message
        System.out.println(name + " slept. Energy is now " + energy);
    }

    // Check Status
    @Override
    public String toString() {

        if (hunger <= 35 && energy <=35) {
            status = name + " is hungry.";
        } if (happiness >= 35) {
            status = status + name + " is doing good";
        } if (happiness <= 35) {
            status = status + name + " is bored";
        }  if (hunger >= 35 && energy >=35) {
            status = name + " is happy.";
        } 

        return "[ Tamagotchi name = " +name + " , hunger = "+hunger+" age = "+age+ "-Days happiness = " +happiness+ " energy = "+energy +"]";
    }

    //Grows older while losing stats
    public void passTime() {
        hunger -= 10;
        happiness -= 5;
        energy -= 10;
        age += 1;

        if (hunger < 0) hunger = 0;
        if (happiness < 0) happiness = 0;
        if (energy < 0) energy = 0;

        System.out.println("\nTime has passed...");
    }

    //Check if pet is alive 
    public boolean isAlive() {
        return hunger > 0 && happiness > 0 && energy > 0; 
    }
    
}

