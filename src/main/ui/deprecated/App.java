package ui.deprecated;

import model.*;
import org.json.JSONArray;
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
            System.out.print("Data files could not be read");
            e.printStackTrace();
            return;
        }
        input = new Scanner(System.in);
        command = null;

        mainMenu();
        System.out.println("Exiting Application");
    }

    // EFFECTS: Processes the input in the main menu
    public void mainMenu() {
        while (true) {
            mainMenuOptions();
            System.out.println("==================================");
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
        saveApplication();
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

            if (command.equals("m")) {
                mapMenu(w);
            } else if (command.equals("c")) {
                characterMenu(w.getHero());
            } else if (command.equals("b")) {
                break;
            } else {
                invalidInput();
            }
        }
        saveApplication();
    }

    // EFFECTS: Displays the options for world menu
    public void worldMenuOptions(World w) {
        menuHeader("World Menu for " + w.getWorldName());
        System.out.println("m => View the Map");
        System.out.println("c => Modify/View Character");
        System.out.println("b => Exit & Save World");
        System.out.println("----------------------------------");
        System.out.println("Round:" + w.getRound());
        System.out.println("Difficulty:" + w.getDifficulty());
        System.out.println("==================================");
    }

    // EFFECTS: Processes the input in the map menu
    public void mapMenu(World w) {
        while (true) {
            mapMenuOptions(w);
            command = input.nextLine();

            try {
                if (!processMapInput(w, command)) {
                    break;
                }
            } catch (IOException e) {
                System.out.println("Could not generate monster");
            }
        }
    }

    // EFFECTS: Processes the user input on map and returns true if success, otherwise false
    public Boolean processMapInput(World w, String command) throws IOException {
        for (int i = 1; i <= 5; i++) {
            if (command.equals(Integer.toString(i))) {
                if (w.getRound() >= i) {
                    startFight(w.getHero(), jsonReader.reconstructMonster(i, w.getDifficulty()), w, i);
                    return true;
                } else {
                    System.out.println("You haven't reached this area yet!");
                    return true;
                }
            }
        }
        if (command.equals("b")) {
            return false;
        } else {
            invalidInput();
        }
        return true;
    }

    // EFFECTS: Displays the map
    public void mapMenuOptions(World w) {
        System.out.println("==================================");
        System.out.println("The Fractured Lands");
        System.out.println("Select a destination\n");
        System.out.println("Map Progress: " + (w.getRound() - 1) + "/5");

        System.out.println("1 -> Dark Swamp");
        System.out.println("2 -> Frosty Forest");
        System.out.println("3 -> Graveyard");
        System.out.println("4 -> The Hills");
        System.out.println("5 -> The Serpent's Pass");
        System.out.println("b -> Go Back");
    }

    // MODIFIES: this
    // EFFECTS: Simulates a fight a against a monster
    public void startFight(Hero hero, Monster monster, World world, int round) {
        int turn = 1;
        hero.recover();
        while (true) {
            fightOptions(turn, hero, monster);

            command = input.nextLine();
            if (command.equals("f")) {
                System.out.println("Running away!");
                break;
            }
            if (!processFightInputs(hero, monster, command)) {
                continue;
            } else {
                hero.decreaseCoolDowns();
            }
            if (isFightOver(hero, monster, world, round)) {
                System.out.println("======================================");
                break;
            }

            turn++;
        }
        saveApplication();
    }

    // MODIFIES: this
    // EFFECTS: Processes a potential skill cast returning true if processed
    public Boolean processSkillInputs(Hero hero, Monster monster, String command) {
        if (command.equals("1")) {
            return tryFirstSkill(hero, monster);
        } else if (command.equals("2")) {
            return trySecondSkill(hero, monster);
        } else if (command.equals("3")) {
            return tryThirdSkill(hero, monster);
        } else {
            return false;
        }
    }



    // MODIFIES: this
    // EFFECTS: Processes User input during fight returns true if processed
    public Boolean processFightInputs(Hero hero, Monster monster, String command) {
        if (processSkillInputs(hero, monster, command)) {
            return true;
        } else if (command.equals("a")) {
            int damageDealt = hero.basicAttack();
            monster.takeDamage(damageDealt);
            System.out.println("You did " + damageDealt + " damage!");
            return true;
        } else if (command.equals("h") && hero.getInventory().getHealthPotions() > 0) {
            hero.drinkHealthPotion();
            System.out.println("You gained " + hero.getHealthAndManaPotionValue() + " health!");
            return true;
        } else if (command.equals("m") && hero.getInventory().getManaPotions() > 0) {
            hero.drinkManaPotion();
            System.out.println("You gained " + hero.getHealthAndManaPotionValue() + " mana!");
            return true;
        } else {
            invalidInput();
            return false;
        }
    }

    // EFFECTS: Displays options during fight
    public void fightOptions(int turn, Hero hero, Monster monster) {
        Inventory inv = hero.getInventory();
        System.out.println("==================================");
        System.out.println("TURN " + turn);
        System.out.println(hero.getName() + " Health: " + hero.getHealth());
        System.out.println(hero.getName() + " Mana: " + hero.getMana());
        System.out.println(monster.getName() + " Health : " + monster.getHealth());
        System.out.println("----------------------------------");
        System.out.println("1 => First Skill || Mana Cost: " + hero.getFirstSkillManaCost()
                 + " || Cool Down: " + hero.getFirstSkillCoolDown());
        System.out.println("2 => Second Skill || Mana Cost: " + hero.getSecondSkillManaCost()
                + " || Cool Down: " + hero.getSecondSkillCoolDown());
        System.out.println("3 => Third Skill || Mana Cost: " + hero.getThirdSkillManaCost()
                + " || Cool Down: " + hero.getThirdSkillCoolDown());
        System.out.println("a => Basic Attack");
        if (inv.getHealthPotions() > 0) {
            System.out.println("h => Drink Health Potion: " + inv.getHealthPotions() + "Remaining");
        }
        if (inv.getManaPotions() > 0) {
            System.out.println("m => Drink Mana Potion: " + inv.getManaPotions() + "Remaining");
        }
        System.out.println("f => Flee");
        System.out.println("==================================");
    }

    // MODIFIES: this
    // EFFECTS: Returns true if either monster or hero is head
    //          and prints relevant statement, else false
    public Boolean isFightOver(Hero hero, Monster monster, World world, int round) {
        if (monster.isDead()) {
            System.out.println("======================================");
            victoryLoot(hero.getInventory(), monster.dropLoot());
            hero.gainExp(monster.getExp());
            System.out.println("+" + monster.getExp() + " Experience");
            nextRound(world,round);
            return true;
        } else {
            int monsterDamage = monster.basicAttack();
            int initialHealth = hero.getHealth();
            hero.takeDamage(monsterDamage);
            System.out.println("The " + monster.getName() + " did "
                    + (initialHealth - hero.getHealth()) + " damage to you!");
        }
        if (hero.isDead()) {
            System.out.println("======================================");
            System.out.println("Defeat...");
            System.out.println("The " + monster.getName() + " glares down at your broken body.");
            System.out.println("Out of pity they allow you live another day.");
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: Increments to next round in world if on correct round.
    public void nextRound(World w, int round) {
        if (round == w.getRound()) {
            w.nextRound();
        }
    }

    // MODIFIES: this
    // EFFECTS: Picks up loot from a dead monster
    public void victoryLoot(Inventory inv, List<Integer> loot) {
        System.out.println("Victory!");
        System.out.println("Rewards:");
        inv.setHealthPotions(inv.getHealthPotions() + loot.get(0));
        inv.setManaPotions(inv.getManaPotions() + loot.get(1));
        System.out.println(loot.get(0) + " Health Potions");
        System.out.println(loot.get(1) + " Mana Potions");
        if (loot.get(2) != -1) {
            if (inv.inventorySlotsIsFull()) {
                System.out.println("No more space in inventory, accessory discarded :(");
                return;
            }
            JSONArray arr = new JSONArray();
            arr.put(Integer.toString(loot.get(2)));
            try {
                Accessory a = jsonReader.convertIds(arr).get(0);
                inv.pickUpAccessory(a);
                System.out.println("You've found a " + a.getAccessoryName() + "!");
            } catch (IOException e) {
                System.out.println("Failed to generate an accessory upon victory.");
                e.printStackTrace();
            }
        }
    }

    // EFFECTS: Processes given inputs in hero menu
    public void characterMenu(Hero hero) {
        while (true) {
            System.out.println("==================================");
            characterStats(hero);
            System.out.println("----------------------------------");
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
        System.out.println("==================================");

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
        System.out.println("EXPERIENCE: " + hero.getExperience() + "/" + hero.getExperienceRequiredToLevel());
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
        System.out.println("==================================");
    }

    // EFFECTS: Processes given input in inventory menu
    public void inventoryMenu(Hero hero) {

        while (true) {
            menuHeader("Inventory");
            System.out.println("b -> Go Back to Character Menu");
            System.out.println("----------------------------------");
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
        System.out.println(inv.getHealthPotions() + " Health Potions");
        System.out.println(inv.getManaPotions() + " Mana Potions");
        System.out.println("<<INVENTORYSLOTS>>");
        for (Accessory a: inv.getInventorySlots()) {
            accessoryDisplay(a);
        }
        System.out.println("----------------------------------");
        System.out.println("<<EQUIPMENTSLOTS>>");
        for (Accessory a: inv.getEquipmentSlots()) {
            accessoryDisplay(a);
        }
        System.out.println("==================================");
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
        System.out.println("==================================");
        System.out.println(title);
        System.out.println("----------------------------------");
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

    // MODIFIES: this
    // EFFECTS: Uses firstSkill if not on cool down and sufficient mana,
    //          Deals damage to monster and returns true upon success, otherwise false
    public Boolean tryFirstSkill(Hero hero, Monster monster) {

        if (hero.getLevel() < hero.getFirstSkillLevelRequirement()) {
            System.out.println("Need to be level " + hero.getFirstSkillLevelRequirement()
                    + " to use this skill.");
            return false;
        } else if (hero.getFirstSkillCoolDown() > 0) {
            System.out.println("First Skill is still on CoolDown");
            return false;
        } else if (hero.getMana() < hero.getFirstSkillManaCost()) {
            System.out.println("Not Enough Mana to use First Skill!");
            return false;
        } else {
            int damageDealt = hero.firstSkill();
            monster.takeDamage(damageDealt);
            System.out.println("You've used your first skill and did " + damageDealt + " damage!");
            return true;
        }
    }

    // MODIFIES: this
    // EFFECTS: Uses secondSkill if not on cool down and sufficient mana,
    //          Deals damage to monster and returns true upon success, otherwise false
    public Boolean trySecondSkill(Hero hero, Monster monster) {

        if (hero.getLevel() < hero.getSecondSkillLevelRequirement()) {
            System.out.println("Need to be level " + hero.getSecondSkillLevelRequirement()
                    + " to use this skill.");
            return false;
        } else if (hero.getSecondSkillCoolDown() > 0) {
            System.out.println("Second Skill is still on CoolDown");
            return false;
        } else if (hero.getMana() < hero.getSecondSkillManaCost()) {
            System.out.println("Not Enough Mana to use Second Skill!");
            return false;
        } else {
            int damageDealt = hero.secondSkill();
            monster.takeDamage(damageDealt);
            System.out.println("You've used your second skill and did " + damageDealt + " damage!");
            return true;
        }
    }

    // MODIFIES: this
    // EFFECTS: Uses thirdSkill if not on cool down and sufficient mana,
    //          Deals damage to monster and returns true upon success, otherwise false
    public Boolean tryThirdSkill(Hero hero, Monster monster) {
        if (hero.getLevel() < hero.getThirdSkillLevelRequirement()) {
            System.out.println("Need to be level " + hero.getThirdSkillLevelRequirement()
                    + " to use this skill.");
            return false;
        } else if (hero.getThirdSkillCoolDown() > 0) {
            System.out.println("Third Skill is still on CoolDown");
            return false;
        } else if (hero.getMana() < hero.getThirdSkillManaCost()) {
            System.out.println("Not Enough Mana to use Third Skill!");
            return false;
        } else {
            int damageDealt = hero.thirdSkill();
            monster.takeDamage(damageDealt);
            System.out.println("You've used your third skill and did " + damageDealt + " damage!");
            return true;
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
