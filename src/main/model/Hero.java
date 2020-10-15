package model;

// Abstract for possible Characters
public abstract class Hero extends Entity {

    private static final int BASE_STATS = 1;
    private static final int BASE_HEALTH_AND_MANA = 100;
    private static final int HEALTH_AND_MANA_POTION_VALUE = 25;
    private static final int MAX_HEALTH_AND_MANA_INCREMENT = 10;
    private static final int SKILL_POINTS_GRANTED_PER_LEVEL = 5;
    protected String heroClass;
    protected static final int SPECIAL_BASE_STAT = 5;
    protected String name;
    protected Inventory inventory;
    protected int maxHealth;
    protected int health;
    protected int maxMana;
    protected int mana;
    protected int level;
    protected int skillPoints;
    protected int strength;
    protected int defence;
    protected int agility;
    protected int intelligence;

    // EFFECTS: Construct a hero with given name, an inventory,
    //          and predetermined base stats
    public Hero(String name) {
        this.name = name;
        this.inventory = new Inventory();
        this.attackMultiplier = 5;
        this.maxHealth = BASE_HEALTH_AND_MANA;
        this.maxMana = BASE_HEALTH_AND_MANA;
        this.health = BASE_HEALTH_AND_MANA;
        this.mana = BASE_HEALTH_AND_MANA;
        this.level = 1;
        this.skillPoints = 0;
        this.strength = BASE_STATS;
        this.defence = BASE_STATS;
        this.agility = BASE_STATS;
        this.intelligence = BASE_STATS;
    }

    // MODIFIES: this
    // EFFECTS: Increase level by one and grants 5 skill points
    public void levelUp() {
        this.level++;
        this.skillPoints += this.getSkillPointsGrantedPerLevel();
        this.maxHealth += this.getMaxHealthAndManaIncrement();
        this.maxMana += this.getMaxHealthAndManaIncrement();
        this.health = this.getMaxHealth();
        this.mana = this.getMaxMana();
    }

    // EFFECTS: Returns a value calculated off hero's strength
    public int basicAttack() {
        return this.getStrength() * this.getAttackMultiplier();
    }

    // MODIFIES: this
    // EFFECTS: Decrease health by based on damage
    // and return true if character still alive, otherwise false
    public Boolean takeDamage(int damage) {
        this.health -= damage;
        if (this.isDead()) {
            return false;
        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS: Equips the given accessory if sufficient room in
    //          equipmentSlots and increases stats, then returns true
    //          false, otherwise
    public Boolean equipAccessory(Accessory accessory) {
        if (this.getInventory().equipmentSlotsIsFull()) {
            return false;
        } else {
            this.getInventory().moveToEquipmentSlots(accessory);
            this.strength += accessory.getAddedStrength();
            this.defence += accessory.getAddedDefence();
            this.agility += accessory.getAddedAgility();
            this.intelligence += accessory.getAddedIntelligence();
            return true;
        }
    }

    // MODIFIES: this
    // EFFECTS: Sets hero health and mana back to max
    public void recover() {
        this.health = this.getMaxHealth();
        this.mana = this.getMaxMana();
    }

    // MODIFIES: this
    // EFFECTS: Unequips the given accessory if sufficient room in
    //          inventorySlots and decreases stats, then returns true.
    //          Otherwise false.
    public Boolean unequipAccessory(Accessory accessory) {
        if (this.getInventory().inventorySlotsIsFull()) {
            return false;
        } else {
            this.getInventory().moveToInventorySlots(accessory);
            this.strength -= accessory.getAddedStrength();
            this.defence -= accessory.getAddedDefence();
            this.agility -= accessory.getAddedAgility();
            this.intelligence -= accessory.getAddedIntelligence();
            return true;
        }
    }

    // MODIFIES: this
    // EFFECTS: Decrease mana by manaNeeded if sufficient mana and return true,
    //          otherwise false.
    public Boolean spendMana(int manaNeeded) {
        if (this.getMana() >= manaNeeded) {
            this.mana -= manaNeeded;
            return true;
        }
        return false;
    }

    // REQUIRES: Must have mana potion in inventory
    // MODIFIES: this
    // EFFECTS: Increments mana by healthAndManaPotionValue up to maxMana(Still consumes potion)
    //          , removes one mana potion from inventory, and returns true. If no mana potions
    //          in inventory returns false.
    public Boolean drinkManaPotion() {
        if (this.getInventory().getManaPotions() > 0) {
            this.getInventory().useManaPotion();
            int possibleNewMana = this.mana + this.getHealthAndManaPotionValue();
            if (possibleNewMana > maxMana) {
                this.mana = maxMana;
            } else {
                this.mana = possibleNewMana;
            }
            return true;
        }
        return false;
    }

    // REQUIRES: Must have health potion in inventory
    // MODIFIES: this
    // EFFECTS: Increments health by healthAndManaPotionValue up to maxHealth, removes one health
    //          potion from inventory, and returns true. If no health potions
    //          in inventory returns false.
    public Boolean drinkHealthPotion() {
        if (this.getInventory().getHealthPotions() > 0) {
            this.getInventory().useHealthPotion();
            int possibleNewHealth = this.getHealth() + this.getHealthAndManaPotionValue();
            if (possibleNewHealth > maxHealth) {
                this.health = maxHealth;
            } else {
                this.health = possibleNewHealth;
            }
            return true;
        }
        return false;
    }

    // EFFECTS: Checks if Character is dead, returns true if dead, otherwise false
    public Boolean isDead() {
        return this.health <= 0;
    }

    // EFFECTS: Returns true if character has skill points, else false.
    public Boolean hasSkillPoints() {
        return this.skillPoints > 0;
    }

    public void increaseStrength() {
        this.strength++;
        this.skillPoints--;
    }

    public void increaseDefence() {
        this.defence++;
        this.skillPoints--;
    }

    public void increaseAgility() {
        this.agility++;
        this.skillPoints--;
    }

    public void increaseIntelligence() {
        this.intelligence++;
        this.skillPoints--;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public int getSkillPoints() {
        return this.skillPoints;
    }

    public int getSkillPointsGrantedPerLevel() {
        return this.SKILL_POINTS_GRANTED_PER_LEVEL;
    }

    public int getSpecialBaseStat() {
        return this.SPECIAL_BASE_STAT;
    }

    public int getMana() {
        return this.mana;
    }

    public int getHealth() {
        return this.health;
    }

    public int getLevel() {
        return this.level;
    }

    public String getName() {
        return this.name;
    }

    public int getStrength() {
        return this.strength;
    }

    public int getDefence() {
        return this.defence;
    }

    public int getAgility() {
        return this.agility;
    }

    public int getIntelligence() {
        return this.intelligence;
    }

    public int getBaseStats() {
        return this.BASE_STATS;
    }

    public int getBaseHealthAndMana() {
        return this.BASE_HEALTH_AND_MANA;
    }

    public int getHealthAndManaPotionValue() {
        return this.HEALTH_AND_MANA_POTION_VALUE;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public int getMaxMana() {
        return this.maxMana;
    }

    public int getMaxHealthAndManaIncrement() {
        return this.MAX_HEALTH_AND_MANA_INCREMENT;
    }

    public String getHeroClass() {
        return this.heroClass;
    }
}
