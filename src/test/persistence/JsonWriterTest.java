package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Following similar testing style as JsonSerializationDemo
public class JsonWriterTest {

    @Test
    public void writerToInvalidFileTest() {
        try {
            JsonWriter writer = new JsonWriter("./data/bad:\0File.json");
            writer.open();
            fail("Should not have opened, file does not exist!") ;
        } catch (FileNotFoundException e) {
            // Expected
        }
    }

    @Test
    public void writerEmptyWorldsTest() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorlds.json");
            Worlds worlds = new Worlds();
            writer.open();
            writer.write(worlds);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorlds.json");
            worlds = reader.read();
            assertEquals(0,worlds.getNumberOfWorlds());

        } catch (IOException e) {
            fail("Should not have thrown an exception");
        }
    }

    @Test
    public void writerWorldsTest() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterWorlds.json");
            Worlds worlds = new Worlds();
            worlds.addWorld(createTestWorld());

            writer.open();
            writer.write(worlds);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterWorlds.json");
            Worlds worldsRead = reader.read();
            World w = worldsRead.getWorld(1);
            Hero h = w.getHero();
            assertEquals(1,worldsRead.getNumberOfWorlds());
            assertEquals(1, w.getWorldNumber());
            assertEquals("medium", w.getDifficulty());
            assertEquals("Sandbox World", w.getWorldName());
            assertEquals(3, w.getRound());
            assertEquals("MyHero", h.getName());
            assertEquals(5,h.getLevel());
            assertEquals(10,h.getAgility());


        } catch (IOException e) {
            fail("Should not have thrown an exception");
        }
    }

    @Test
    public void writerWorldsCheckHeroTest() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterWorlds.json");
            Worlds worlds = new Worlds();
            worlds.addWorld(createTestWorld());

            writer.open();
            writer.write(worlds);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterWorlds.json");
            Worlds worldsRead = reader.read();
            World w = worldsRead.getWorld(1);
            Hero h = w.getHero();
            assertEquals("MyHero", h.getName());
            assertEquals("warrior",h.getHeroClass());
            assertEquals(5,h.getLevel());
            assertEquals(200,h.getExperience());
            assertEquals(300,h.getExperienceRequiredToLevel());
            assertEquals(200,h.getMaxHealth());
            assertEquals(200,h.getMaxMana());
            assertEquals(20,h.getSkillPoints());
            assertEquals(10,h.getStrength());
            assertEquals(10,h.getDefence());
            assertEquals(10,h.getAgility());
            assertEquals(10,h.getIntelligence());
        } catch (IOException e) {
            fail("Should not have thrown an exception");
        }
    }

    @Test
    public void writerWorldsCheckInventoryTest() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterWorlds.json");
            Worlds worlds = new Worlds();
            worlds.addWorld(createTestWorld());

            writer.open();
            writer.write(worlds);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterWorlds.json");
            Worlds worldsRead = reader.read();
            Inventory inv = worldsRead.getWorld(1).getHero().getInventory();
            Accessory equippedAccessory = inv.getEquipmentSlots().get(1);
            Accessory invAccessory = inv.getInventorySlots().get(0);
            assertEquals(1,invAccessory.getAccessoryId());
            assertEquals("Ring",invAccessory.getAccessoryName());
            assertEquals("Glove", equippedAccessory.getAccessoryName());
            assertEquals(3,equippedAccessory.getAccessoryId());
            assertEquals(4,equippedAccessory.getAddedStrength());
            assertEquals(2,equippedAccessory.getAddedDefence());
            assertEquals(1,equippedAccessory.getAddedAgility());
            assertEquals(1,equippedAccessory.getAddedIntelligence());

        } catch (IOException e) {
            fail("Should not have thrown an exception");
        }
    }

        public World createTestWorld() {
        World w = new World("Sandbox World", "MyHero", "warrior", "medium", 1) ;
        Hero hero = new WarriorHero("MyHero");
        Inventory inv = new Inventory();
        Accessory a = new Accessory("Broken Ring", 1,1,1,1,1);
        Accessory a3 = new Accessory("Golden Bracelet",4,2,1,1,3);
        List<Accessory> equipmentSlots = new ArrayList<>();
        List<Accessory> inventorySlots = new ArrayList<>();
        equipmentSlots.add(a);
        equipmentSlots.add(a3);
        inventorySlots.add(a);
        inv.setHealthPotions(10);
        inv.setManaPotions(10);
        inv.setEquipmentSlots(equipmentSlots);
        inv.setInventorySlots(inventorySlots);
        hero.setLevel(5);
        hero.setExperience(200);
        hero.setExperienceRequiredToLevel(300);
        hero.setMaxHealth(200);
        hero.setMaxMana(200);
        hero.setSkillPoints(20);
        hero.setStrength(10);
        hero.setAgility(10);
        hero.setDefence(10);
        hero.setIntelligence(10);
        hero.setInventory(inv);
        w.setHero(hero);
        w.setRound(3);

        return w;
    }
}
