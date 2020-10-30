package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Reads the JSON from file given file
// Copied format from JsonSerializationDemo
public class JsonReader {
    private String source;
    protected String accessoryFile = "./data/accessories.json";

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Worlds from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Worlds read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorlds(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Worlds from JSON object and returns it
    private Worlds parseWorlds(JSONObject jsonObject) throws IOException {
        Worlds worlds = new Worlds();
        for (String key: jsonObject.keySet()) {
            World w = reconstructWorld(jsonObject.optJSONObject(key));
            worlds.addWorld(w);
        }
        return worlds;
    }

    // MODIFIES: Worlds
    // EFFECTS: Parses all world objects from JSON object and adds them to worlds
    private World reconstructWorld(JSONObject jsonObject) throws IOException {
        World baseWorld = baseWorld(jsonObject.getJSONObject("worldData"));
        Inventory inventory = reconstructInventory(jsonObject.optJSONObject("inventoryData"));
        Hero hero = reconstructHero(jsonObject.optJSONObject("heroData"), inventory);
        baseWorld.setHero(hero);

        return baseWorld;
    }

    // EFFECTS: Returns a new world with some properties reloaded
    private World baseWorld(JSONObject worldData) {
        int worldNumber = worldData.getInt("worldNumber");
        String worldName = worldData.getString("worldName");
        String difficulty = worldData.getString("difficulty");
        int round = worldData.getInt("round");

        World w = new World(worldName,"placeholder","placeholder",
                difficulty,worldNumber);
        w.setRound(round);
        return w;
    }

    // EFFECTS: Returns a reconstructed hero object
    public Hero reconstructHero(JSONObject hero, Inventory inv) {
        Hero baseHero = baseHero(hero.getString("name"),
                hero.getString("heroClass"));

        baseHero.setExperience(hero.getInt("experience"));
        baseHero.setExperienceRequiredToLevel(hero.getInt("experienceRequiredToLevel"));
        baseHero.setMaxHealth(hero.getInt("maxHealth"));
        baseHero.setMaxMana(hero.getInt("maxMana"));
        baseHero.setLevel(hero.getInt("level"));
        baseHero.setSkillPoints(hero.getInt("skillPoints"));
        baseHero.setStrength(hero.getInt("strength"));
        baseHero.setDefence(hero.getInt("defence"));
        baseHero.setAgility(hero.getInt("agility"));
        baseHero.setIntelligence(hero.getInt("intelligence"));
        baseHero.setInventory(inv);
        return baseHero;
    }

    // EFFECTS: Returns a new hero of correct class
    public Hero baseHero(String name, String heroClass) {
        Hero newHero;
        if (heroClass.equals("warrior")) {
            newHero = new WarriorHero(name);
        } else if (heroClass.equals("archer")) {
            newHero = new MageHero(name);
        } else {
            newHero = new MageHero(name);
        }
        return newHero;
    }

    // EFFECTS: Returns a reconstructed inventory object
    public Inventory reconstructInventory(JSONObject inventoryData) throws IOException {
        Inventory baseInventory = new Inventory();
        List<Accessory> inventorySlots = convertIds(inventoryData.getJSONArray("inventorySlots"));
        List<Accessory> equipmentSlots = convertIds(inventoryData.getJSONArray("equipmentSlots"));
        baseInventory.setHealthPotions(inventoryData.getInt("healthPotions"));
        baseInventory.setManaPotions(inventoryData.getInt("manaPotions"));

        return null;
    }

    // EFFECTS: Converts all the ids into accessories and returns it
    public List<Accessory> convertIds(JSONArray ids) throws IOException {
        List<Accessory> slots = new ArrayList<>();
        String jsonData = readFile(accessoryFile);
        JSONObject accessoryJson = new JSONObject(jsonData);
        for (int i = 0; i < ids.length(); i++) {
            String accId = ids.getString(0);
            slots.add(reconstructAccessory(accessoryJson.getJSONObject("accId")));
        }

        return slots;
    }

    // EFFECTS: Reconstructs an accessory object with given json
    public Accessory reconstructAccessory(JSONObject data) {
        int accessoryId = data.getInt("accessoryId");
        String name = data.getString("name");
        int addedStrength = data.getInt("addedStrength");
        int addedDefence = data.getInt("addedDefence");
        int addedAgility = data.getInt("addedAgility");
        int addedIntelligence = data.getInt("addedIntelligence");

        return new Accessory(name,addedStrength,addedDefence,addedAgility,
                addedIntelligence,accessoryId);
    }

}
