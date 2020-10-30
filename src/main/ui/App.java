package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Utilizing a similar template as Teller Application shown
// as an example.

// Main interface of the application
public class App {
    private Worlds worlds;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String DATA_SOURCE = "./data/application.json";
    private String command;

    // EFFECTS: Starts the application
    public App() {
        jsonWriter = new JsonWriter(DATA_SOURCE);
        jsonReader = new JsonReader(DATA_SOURCE);
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: Acts as a user interface in the console to
    //          process inputs.
    public void runApp() {
        try {
            worlds = jsonReader.read();
        } catch (IOException e) {
            System.out.printf("Data files could not be read");
            e.printStackTrace();
            return;
        }
        input = new Scanner(System.in);
        command = null;

        mainMenu();
        saveApplication();
        System.out.println("Exiting Application");
    }

    // EFFECTS: Processes the input in the main menu
    public void mainMenu() {
        while (true) {
            mainMenuOptions();
            command = input.nextLine();
            if (command.equals("q")) {
                break;
            } else if (validWorldCommand(command)) {
                int worldNumber = Integer.parseInt(command.substring(1, 2));
                worldMenu(worlds.getWorld(worldNumber));
            } else if (validDeleteWorldCommand(command)) {
                int worldNumber = Integer.parseInt(command.substring(1, 2));
                deleteWorld(worldNumber);
            } else if (command.equals("c") && !worlds.worldsIsFull()) {
                createWorldMenu();
            } else {
                invalidInput();
            }
        }
    }

    // EFFECTS: Displays options in the main menu
    public void mainMenuOptions() {
        menuHeader("Main Menu");
        for (World w : worlds.getWorlds()) {
            System.out.println("w" + w.getWorldNumber()
                    + " => display world: " + w.getWorldName());
            System.out.println("d" + w.getWorldNumber()
                    + " => delete world: " + w.getWorldName());
        }
        if (!worlds.worldsIsFull()) {
            System.out.println("c => Create a new world");
        } else {
            System.out.println("Worlds at max capacity, delete a world"
                    + " to make more!");
        }
        System.out.println("q => Quit and Save");
    }

    // EFFECTS: Processes input given in world menu
    public void worldMenu(World w) {
        while (true) {
            worldMenuOptions(w);
            command = input.nextLine();

            if (command.equals("f")) {
                Random random = new Random();
                int str = random.nextInt(20 - 10) + 10;
                Monster monster = new Monster("Slime", 20, str, 1, 50);
                System.out.println(w.getHero().getName()
                        + ", you've encountered" + " a " + monster.getName() + "!");
                startFight(w.getHero(), monster);
                w.getHero().recover();
            } else if (command.equals("c")) {
                characterMenu(w.getHero());
            } else if (command.equals("b")) {
                break;
            } else {
                invalidInput();
            }
        }
    }

    // EFFECTS: Displays the options for world menu
    public void worldMenuOptions(World w) {
        menuHeader("World Menu for " + w.getWorldName());
        System.out.println("f => Fight a monster");
        System.out.println("c => Modifiy/View Character");
        System.out.println("b => Go Back to Main Menu");
        System.out.println("------------------");
        System.out.println("Round:" + w.getRound());
        System.out.println("Difficulty:" + w.getDifficulty());

    }

    // MODIFIES: this
    // EFFECTS: Simulates a fight a against a monster
    public void startFight(Hero hero, Monster monster) {
        int turn = 1;
        while (true) {
            if (isFightOver(hero, monster)) {
                break;
            }
            fightOptions(turn, hero, monster);

            command = input.nextLine();
            if (command.equals("a")) {
                monster.takeDamage(hero.basicAttack());
            } else if (command.equals("h") && hero.getInventory().getHealthPotions() > 0) {
                hero.drinkHealthPotion();
            } else if (command.equals("m") && hero.getInventory().getManaPotions() > 0) {
                hero.drinkManaPotion();
            } else if (command.equals("f")) {
                System.out.println("Running away!");
                break;
            } else {
                invalidInput();
                continue;
            }
            hero.takeDamage(monster.basicAttack());
            turn++;
        }
    }

    // EFFECTS: Displays options during fight
    public void fightOptions(int turn, Hero hero, Monster monster) {
        Inventory inv = hero.getInventory();
        System.out.println("TURN " + turn);
        System.out.println(hero.getName() + " Health: " + hero.getHealth());
        System.out.println(monster.getName() + " Health : " + monster.getHealth());
        System.out.println("------------");
        System.out.println("a => Basic Attack");
        if (inv.getHealthPotions() > 0) {
            System.out.println("h => Drink Health Potion: "
                    + inv.getHealthPotions() + "Remaining");
        }
        if (inv.getManaPotions() > 0) {
            System.out.println("m => Drink Mana Potion: "
                    + inv.getManaPotions() + "Remaining");
        }
        System.out.println("f => Flee");

    }

    // EFFECTS: Returns true if either monster or hero is head
    //          and prints relevant statement, else false
    public Boolean isFightOver(Hero hero, Monster monster) {
        if (monster.isDead()) {
            System.out.println("You've won the fight");
            return true;
        }
        if (hero.isDead()) {
            System.out.println("You've died!");
            return true;
        }
        return false;
    }

    // EFFECTS: Processes given inputs in hero menu
    public void characterMenu(Hero hero) {
        while (true) {
            characterStats(hero);
            System.out.println("--------------");
            characterMenuOptions(hero);
            command = input.nextLine();

            if (command.equals("i")) {
                inventoryMenu(hero);
            } else if (hero.hasSkillPoints() && command.equals("s")) {
                statsMenu(hero);
            } else if (command.equals("b")) {
                break;
            } else {
                invalidInput();
            }
        }
    }

    // EFFECTS: Displays hero menu options
    public void characterMenuOptions(Hero hero) {
        menuHeader("Character Menu");
        System.out.println("i => Open Inventory");
        if (hero.hasSkillPoints()) {
            System.out.println("s => Increase Stats");
        }
        System.out.println("b => Go Back to World Menu");

    }

    // EFFECTS: Displays given heros stats
    public void characterStats(Hero hero) {
        System.out.println("HERO STATS");
        System.out.println("LEVEL: " + hero.getLevel());
        System.out.println("CLASS: " + hero.getHeroClass());
        System.out.println("NAME: " + hero.getName());
        System.out.println("STRENGTH: " + hero.getStrength());
        System.out.println("DEFENCE: " + hero.getDefence());
        System.out.println("AGILITY: " + hero.getAgility());
        System.out.println("INTELLIGENCE: " + hero.getIntelligence());
        System.out.println("MAX HEALTH: " + hero.getMaxHealth());
        System.out.println("MAX MANA: " + hero.getMaxMana());
    }

    // MODIFIES: this
    // EFFECTS: Processes input in Hero Menu and increases chosen stats
    public void statsMenu(Hero hero) {
        characterStats(hero);
        statsMenuOptions(hero);

        while (true) {
            if (!hero.hasSkillPoints()) {
                System.out.println("No more Skill Points");
                break;
            }
            System.out.println("SKILL POINTS REMAINING: " + hero.getSkillPoints());
            command = input.nextLine();

            if (command.equals("s")) {
                hero.increaseStrength();
            } else if (command.equals("d")) {
                hero.increaseDefence();
            } else if (command.equals("a")) {
                hero.increaseAgility();
            } else if (command.equals("i")) {
                hero.increaseIntelligence();
            } else if (command.equals("b")) {
                break;
            } else {
                invalidInput();
            }
        }
    }

    public void statsMenuOptions(Hero hero) {
        menuHeader("Increase Hero's Stats");
        System.out.println("s => Increase Strength +1");
        System.out.println("d => Increase Defence +1");
        System.out.println("a => Increase Agility +1");
        System.out.println("i => Increase Intelligence +1");
        System.out.println("b => Go Back to Character Menu");
    }

    // EFFECTS: Processes given input in inventory menu
    public void inventoryMenu(Hero hero) {

        while (true) {
            menuHeader("Inventory");
            System.out.println("b -> Go Back to Character Menu");
            System.out.println("---------------");
            inventoryDisplay(hero.getInventory());
            command = input.nextLine();
            if (command.equals("b")) {
                break;
            } else {
                invalidInput();
            }
        }
    }

    // EFFECTS: Displays items in inventory
    public void inventoryDisplay(Inventory inv) {
        System.out.println("<<INVENTORYSLOTS>>");
        for (Accessory a: inv.getInventorySlots()) {
            accessoryDisplay(a);
        }

        System.out.println("<<EQUIPMENTSLOTS>>");
        for (Accessory a: inv.getEquipmentSlots()) {
            accessoryDisplay(a);
        }
    }

    // EFFECTS: Displays given accessory
    public void accessoryDisplay(Accessory a) {
        System.out.println(a.getAccessoryName() + ": "
                + "+" + a.getAddedStrength() + "Strength, "
                + "+" + a.getAddedDefence() + "Defence, "
                + "+" + a.getAddedAgility() + "Agility, "
                + "+" + a.getAddedIntelligence() + "Intelligence, "
        );
    }

    // MODIFIES; this
    // EFFECTS: Deletes the world with given worldnumber
    public void deleteWorld(int worldNumber) {
        this.worlds.deleteWorld(worldNumber);
    }

    // MODIFIES; this
    // EFFECTS; Creates a new world
    public void createWorldMenu() {
        List<String> worldData = new ArrayList<>();
        int worldParameters = 3;
        int parameterTracker = 0;

        while (parameterTracker <= worldParameters) {
            createWorldOptions(parameterTracker);
            command = input.nextLine();

            if (parameterTracker == 0) {
                worldData.add(command);
            } else if (parameterTracker == 1) {
                worldData.add(command);
            } else if (parameterTracker == 2) {
                worldData.add(command);
            } else if (parameterTracker == 3) {
                worldData.add(command);
            }

            parameterTracker++;
        }
        if (!createWorld(worldData)) {
            System.out.println("World not created, your class or"
                    + " difficulty input was invalid.");
        } else {
            System.out.println("World Created!");
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates a new world given the data if worldData is valid
    //          and returns true, otherwise false
    public Boolean createWorld(List<String> worldData) {
        String heroClass;
        String difficulty;
        if (worldData.get(2).equals("w")) {
            heroClass = "warrior";
        } else if (worldData.get(2).equals("a")) {
            heroClass = "archer";
        } else if (worldData.get(2).equals("m")) {
            heroClass = "mage";
        } else {
            return false;
        }

        if (worldData.get(3).equals("e")) {
            difficulty = "easy";
        } else if (worldData.get(3).equals("m")) {
            difficulty = "medium";
        } else if (worldData.get(3).equals("h")) {
            difficulty = "hard";
        } else {
            return false;
        }
        this.worlds.createWorld(worldData.get(0), worldData.get(1),
                heroClass, difficulty);
        return true;
    }

    // EFFECTS: Displays options when creating a world
    public void createWorldOptions(int parameterTracker) {
        System.out.println("Creating New World!");
        if (parameterTracker == 0) {
            System.out.println("Enter name of new world:");
        } else if (parameterTracker == 1) {
            System.out.println("Enter name of hero");
        } else if (parameterTracker == 2) {
            System.out.println("Pick a class");
            System.out.println("w => warrior");
            System.out.println("a => archer");
            System.out.println("m => mage");
        } else {
            System.out.println("Select a difficulty");
            System.out.println("e => easy");
            System.out.println("m => medium");
            System.out.println("h => hard");
        }
    }

    // EFFECTS: Displays a menu header with given title
    public void menuHeader(String title) {
        System.out.println(title);
        System.out.println("------------------");
        System.out.println("Enter one of the following:");
    }

    // EFFECTS: prints unknown input
    public void invalidInput() {
        System.out.println("Invalid Input, try again!");
    }

    // EFFECTS: returns true if command in form "w(some integer)"
    //          and is a valid world, else false
    public Boolean validWorldCommand(String command) {
        if (command.length() != 2 || !command.substring(0, 1).equals("w")) {
            return false;
        }
        return validWorld(command);
    }

    // EFFECTS: returns true if command in form "w(some integer)"
    //          and is a valid world, else false
    public Boolean validDeleteWorldCommand(String command) {
        if (command.length() != 2 || !command.substring(0, 1).equals("d")) {
            return false;
        }
        return validWorld(command);
    }

    // EFFECTS: returns true if world exist with given world number
    //          in the command
    public Boolean validWorld(String command) {
        try {
            int num = Integer.parseInt(command.substring(1,2));
            return worlds.getWorld(num) != null;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // EFFECTS: Saves all application data to file
    public void saveApplication() {
        try {
            jsonWriter.open();
            jsonWriter.write(worlds);
            jsonWriter.close();
            System.out.println("Auto-Saving data");
        } catch (FileNotFoundException e) {
            System.out.println("Data could not be saved!");
            e.printStackTrace();
        }
    }
}
