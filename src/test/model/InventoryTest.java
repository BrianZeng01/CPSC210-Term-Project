package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {
    Inventory inventory;

    @BeforeEach
    public void inventoryTestSetup() {
        this.inventory = new Inventory();
    }

    @Test
    public void constructorTest() {
        assertEquals(inventory.getStarterPotions(), inventory.getHealthPotions());
        assertEquals(inventory.getStarterPotions(), inventory.getManaPotions());
        assertEquals(0, inventory.getNumberOfAccessoriesInEquipmentSlots());
        assertEquals(0, inventory.getNumberOfAccessoriesInInventorySlots());
    }

    @Test
    public void pickUpAccessoryEnoughRoomTest() {
        Accessory accessory = new Accessory("Pendant", 1,2,3,4);
        assertTrue(inventory.pickUpAccessory(accessory));
        assertEquals(1, inventory.getNumberOfAccessoriesInInventorySlots());
    }

    @Test
    public void pickUpAccessoryNotEnoughRoomTest() {
        for (int i = 0 ; i < inventory.getMaxInventorySlots(); i++) {
            Accessory accessory = new Accessory("Pendant", i,2,3,4);
            assertTrue(inventory.pickUpAccessory(accessory));
        }
        assertEquals(inventory.getMaxInventorySlots(), inventory.getNumberOfAccessoriesInInventorySlots());
        Accessory accessory = new Accessory("Pendant", 20,2,3,4);
        assertFalse(inventory.pickUpAccessory(accessory));
    }

    @Test
    public void dumpAccessoryTest () {
        Accessory accessory = new Accessory("Pendant", 1,2,3,4);
        inventory.pickUpAccessory(accessory);
        assertEquals(1, inventory.getNumberOfAccessoriesInInventorySlots());
        inventory.dumpAccessory(accessory);
        assertEquals(0, inventory.getNumberOfAccessoriesInInventorySlots());
    }

    @Test
    public void dumpAccessoryManyTest () {
        Accessory accessory = new Accessory("Pendant", 1,2,3,4);
        Accessory accessory2 = new Accessory("Necklace", 1,2,3,4);
        inventory.pickUpAccessory(accessory);
        inventory.pickUpAccessory(accessory2);
        assertEquals(2, inventory.getNumberOfAccessoriesInInventorySlots());
        inventory.dumpAccessory(accessory);
        assertEquals(1, inventory.getNumberOfAccessoriesInInventorySlots());
        assertEquals("Necklace", this.inventory.getInventorySlots().get(0).accessoryName);
    }

    @Test
    public void moveToEquipmentSlotsNotFullTest() {
        Accessory accessory = new Accessory("Pendant", 1,2,3,4);
        inventory.pickUpAccessory(accessory);
        inventory.moveToEquipmentSlots(accessory);
        assertEquals(0, inventory.getNumberOfAccessoriesInInventorySlots());
        assertEquals(1, inventory.getNumberOfAccessoriesInEquipmentSlots());
    }

    @Test
    public void moveToEquipmentSlotsManyTest() {
        Accessory accessory = new Accessory("Pendant", 1,2,3,4);
        Accessory accessory2 = new Accessory("Pendant2", 2,2,3,4);
        inventory.pickUpAccessory(accessory);
        inventory.pickUpAccessory(accessory2);
        inventory.moveToEquipmentSlots(accessory);
        assertEquals(1, inventory.getNumberOfAccessoriesInInventorySlots());
        assertEquals(1, inventory.getNumberOfAccessoriesInEquipmentSlots());
        assertEquals(1, inventory.getEquipmentSlots().get(0).getAddedStrength());

        inventory.moveToEquipmentSlots(accessory2);
        assertEquals(0, inventory.getNumberOfAccessoriesInInventorySlots());
        assertEquals(2, inventory.getNumberOfAccessoriesInEquipmentSlots());
        assertEquals(2, inventory.getEquipmentSlots().get(1).getAddedStrength());

    }

    @Test public void moveToInventorySlotsNotFullTest() {
        Accessory accessory = new Accessory("Pendant", 1,2,3,4);
        inventory.pickUpAccessory(accessory);
        inventory.moveToEquipmentSlots(accessory);
        assertEquals(0, inventory.getNumberOfAccessoriesInInventorySlots());
        assertEquals(1, inventory.getNumberOfAccessoriesInEquipmentSlots());

        inventory.moveToInventorySlots(accessory);
        assertEquals(1, inventory.getNumberOfAccessoriesInInventorySlots());
        assertEquals(0, inventory.getNumberOfAccessoriesInEquipmentSlots());
    }

    @Test
    public void moveToInventorySlotsManyTest() {
        Accessory accessory = new Accessory("Pendant", 1,2,3,4);
        Accessory accessory2 = new Accessory("Pendant2", 2,2,3,4);
        inventory.pickUpAccessory(accessory);
        inventory.pickUpAccessory(accessory2);
        inventory.moveToEquipmentSlots(accessory);
        inventory.moveToEquipmentSlots(accessory2);

        inventory.moveToInventorySlots(accessory2);
        assertEquals(1, inventory.getNumberOfAccessoriesInInventorySlots());
        assertEquals(1, inventory.getNumberOfAccessoriesInEquipmentSlots());
        assertEquals(2, inventory.getInventorySlots().get(0).getAddedStrength());

    }
}
