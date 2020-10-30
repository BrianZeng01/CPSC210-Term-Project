package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Following similar testing style as JsonSerializationDemo
public class JsonReaderTest {

    @Test
    public void readerNonExistentFileTest() {
        try {
            JsonReader reader = new JsonReader("./data/badFile.json");
            reader.read();
            fail("Should not have been able to read");
        } catch (IOException e) {
            // Expected
        }
    }

    @Test
    public void readerEmptyWorldsTest() {
        try {
            JsonReader reader = new JsonReader("./data/testReaderEmptyWorlds.json");
            Worlds worlds = reader.read();
            assertEquals(0,worlds.getNumberOfWorlds());
        } catch (IOException e) {
            fail("Should not throw an exception");
        }
    }

    @Test
    public void readerWorldsCheckWorldTest() {
        try {
            JsonReader reader = new JsonReader("./data/testReaderWorlds.json");
            Worlds worlds = reader.read();
            assertEquals(1,worlds.getNumberOfWorlds());
            World w = worlds.getWorld(1);
            assertEquals("Sandbox World", w.getWorldName());
            assertEquals("medium", w.getDifficulty());
            assertEquals(3,w.getRound());
        } catch (IOException e) {
            fail("Should not throw an exception");
        }
    }

    @Test
    public void readerWorldsCheckHeroTest() {
        try {
            JsonReader reader = new JsonReader("./data/testReaderWorlds.json");
            Worlds worlds = reader.read();
            World w = worlds.getWorld(1);
            Hero hero = w.getHero();

            assertEquals("MyHero", hero.getName());
            assertEquals("warrior", hero.getHeroClass());
            assertEquals(5,hero.getLevel());
            assertEquals(200,hero.getExperience());
            assertEquals(300,hero.getExperienceRequiredToLevel());
            assertEquals(200,hero.getMaxHealth());
            assertEquals(200,hero.getMaxMana());
            assertEquals(20,hero.getSkillPoints());
            assertEquals(10,hero.getStrength());
            assertEquals(10,hero.getDefence());
            assertEquals(10,hero.getAgility());
            assertEquals(10,hero.getIntelligence());
        } catch (IOException e) {
            fail("Should not throw an exception");
        }
    }

    @Test
    public void readerWorldsCheckInventoryTest() {
        try {
            JsonReader reader = new JsonReader("./data/testReaderWorlds.json");
            Worlds worlds = reader.read();
            World w = worlds.getWorld(1);
            Hero hero = w.getHero();
            Inventory inv = hero.getInventory();

            assertEquals(10, inv.getHealthPotions());
            assertEquals(10, inv.getManaPotions());
            assertEquals(2,inv.getNumberOfAccessoriesInEquipmentSlots());
            assertEquals(1,inv.getNumberOfAccessoriesInInventorySlots());

            Accessory equippedAccessory = inv.getEquipmentSlots().get(1);
            Accessory invAccessory = inv.getInventorySlots().get(0);
            assertEquals(1,invAccessory.getAccessoryId());
            assertEquals("Broken Ring",invAccessory.getAccessoryName());
            assertEquals("Golden Bracelet", equippedAccessory.getAccessoryName());
            assertEquals(3,equippedAccessory.getAccessoryId());
            assertEquals(4,equippedAccessory.getAddedStrength());
            assertEquals(2,equippedAccessory.getAddedDefence());
            assertEquals(1,equippedAccessory.getAddedAgility());
            assertEquals(1,equippedAccessory.getAddedIntelligence());
        } catch (IOException e) {
            fail("Should not throw an exception");
        }
    }

    @Test
    public void reconstructMonsterTest() {
        try {
            JsonReader reader = new JsonReader("./data/testReaderWorlds.json");
            Monster m = reader.reconstructMonster(2, "medium");
            assertEquals("Dire Wolf", m.getName());
            assertEquals(90, m.getHealth());
            assertEquals(3, m.getStrength());
            assertEquals(75, m.getExp());
            assertEquals(1, m.getLootChance());
        } catch (IOException e) {
            fail("Should of worked");
        }
    }
}
