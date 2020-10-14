package model;

import java.util.ArrayList;
import java.util.List;

// Basic Inventory system for character
public class Inventory {

    private static final int MAX_INVENTORY_SLOTS = 3;
    private static final int MAX_EQUIPMENT_SLOTS = 3;
    private static final int STARTER_POTIONS = 5;
    public int healthPotions;
    public int manaPotions;
    public List<Accessory> inventorySlots;
    public List<Accessory> equipmentSlots;

    // EFFECTS: Creates a new inventory with all empty slots except for
    //          starter health and mana potions
    public Inventory() {
        this.healthPotions = STARTER_POTIONS;
        this.manaPotions = STARTER_POTIONS;
        this.inventorySlots = new ArrayList<Accessory>(MAX_INVENTORY_SLOTS);
        this.equipmentSlots = new ArrayList<Accessory>(MAX_EQUIPMENT_SLOTS);
    }

    // MODIFIES: this
    // EFFECTS: Returns true if accessory added to slots with room,
    //          otherwise false due to full inventory.
    public Boolean pickUpAccessory(Accessory accessory) {
        if (this.inventorySlotsIsFull()) {
            return false;
        }

        this.getInventorySlots().add(accessory);
        return true;
    }

    // REQUIRES: Accessory must exist in inventorySlots
    // MODIFIES: this
    // EFFECTS: Removes Accessory from inventorySlots
    //          permenantly.
    public void dumpAccessory(Accessory accessory) {
        this.getInventorySlots().remove(accessory);
    }

    // REQUIRES: Sufficient room in equipmentSlots
    // MODIFIES: this
    // EFFECTS: Moves given accessory from inventorySlots to equipmentSlots
    public void moveToEquipmentSlots(Accessory accessory) {
        this.getEquipmentSlots().add(accessory);
        this.getInventorySlots().remove(accessory);
    }

    // REQUIRES: Sufficient room in inventorySlots
    // MODIFIES: this
    // EFFECTS: Moves given accessory from equipmentSlots to inventorySlots
    public void moveToInventorySlots(Accessory accessory) {
        this.getEquipmentSlots().remove(accessory);
        this.getInventorySlots().add(accessory);
    }

    public int getNumberOfAccessoriesInInventorySlots() {
        return this.inventorySlots.size();
    }

    public int getNumberOfAccessoriesInEquipmentSlots() {
        return this.equipmentSlots.size();
    }



    public void useManaPotion() {
        this.manaPotions--;
    }

    public void useHealthPotion() {
        this.healthPotions--;
    }

    // EFFECTS: Returns true if inventory slots are full
    public Boolean inventorySlotsIsFull() {
        return this.getNumberOfAccessoriesInInventorySlots() >= this.getMaxInventorySlots();
    }

    // EFFECTS: Returns true if equipment slots are full
    public Boolean equipmentSlotsIsFull() {
        return this.getNumberOfAccessoriesInEquipmentSlots() >= this.getMaxEquipmentSlots();
    }

    public int getStarterPotions() {
        return this.STARTER_POTIONS;
    }

    public int getHealthPotions() {
        return this.healthPotions;
    }

    public int getManaPotions() {
        return this.manaPotions;
    }

    public List<Accessory> getInventorySlots() {
        return this.inventorySlots;
    }

    public List<Accessory> getEquipmentSlots() {
        return this.equipmentSlots;
    }

    public int getMaxInventorySlots() {
        return this.MAX_INVENTORY_SLOTS;
    }

    public int getMaxEquipmentSlots() {
        return this.MAX_EQUIPMENT_SLOTS;
    }
}
