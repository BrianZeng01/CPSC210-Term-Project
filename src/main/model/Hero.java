package model;

import org.json.JSONObject;
import persistence.Writable;
import persistence.JsonReader;

import java.util.List;
import java.util.Random;

// Abstract for possible Characters
public abstract class Hero extends Entity implements Writable {
    private static final int BASE_STATS = 1;
    private static final int BASE_HEALTH_AND_MANA = 100;
    private static final int HEALTH_AND_MANA_POTION_VALUE = 25;
    private static final int MAX_HEALTH_AND_MANA_INCREMENT = 20;
    private static final int POTION_INCREMENT = 3;
    private static final int REGEN_FACTOR = 2;
    private static final int SKILL_POINTS_GRANTED_PER_LEVEL = 5;
    private static final double DEFENCE_MULTIPLIER = 6;
    private static final int MAX_FIRST_SKILL_COOL_DOWN = 2;
    private static final int MAX_SECOND_SKILL_COOL_DOWN = 3;
    private static final int MAX_THIRD_SKILL_COOL_DOWN = 6;
    private static final int FIRST_SKILL_MANA_COST = 50;
    private static final int SECOND_SKILL_MANA_COST = 75;
    private static final int THIRD_SKILL_MANA_COST = 100;
    private static final int FIRST_SKILL_LEVEL_REQUIREMENT = 1;
    private static final int SECOND_SKILL_LEVEL_REQUIREMENT = 2;
    private static final int THIRD_SKILL_LEVEL_REQUIREMENT = 3;
    private static final int MAX_LEVEL = 6;
    private static final int EXPERIENCE_MULTIPLIER = 3;
    protected double skillMultiplier;
    protected int experienceRequiredToLevel;
    protected int experience;
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
    protected int firstSkillCoolDown;
    protected int secondSkillCoolDown;
    protected int thirdSkillCoolDown;

    // EFFECTS: Construct a hero with given name, an inventory,
    //          and predetermined base stats
    public Hero(String name) {
        this.name = name;
        this.experience = 0;
        this.experienceRequiredToLevel = 100;
        this.inventory = new Inventory();
        this.attackMultiplier = 4;
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
        this.firstSkillCoolDown = 0;
        this.secondSkillCoolDown = 0;
        this.thirdSkillCoolDown = 0;
    }

    // REQUIRES: experienceGained >= 0
    // MODIFIES: this
    // EFFECTS: Increase experience, if sufficient experience then level up hero
    //          and increase experience cap to level. Do nothing if already max level
    public void gainExp(int experienceGained) {
        if (this.getLevel() >= this.MAX_LEVEL) {
            this.experience = this.experienceRequiredToLevel;
            return;
        }
        this.experience += experienceGained;
        if (this.experience >= this.experienceRequiredToLevel) {
            this.levelUp();
            this.incrementExperienceRequiredToLevel();
        }
    }

    // MODIFIES: this
    // EFFECTS: Increases experience required to level by some multiplier
    //          unless hero is maxlevel
    public void incrementExperienceRequiredToLevel() {
        if (getLevel() < getMaxLevel()) {
            this.experienceRequiredToLevel =
                    this.experienceRequiredToLevel * getExperienceMultiplier();
        }
    }

    // MODIFIES: this
    // EFFECTS: Uses skill and returns damage output, or -1 if insufficient mana/level
    public abstract int firstSkill();

    // MODIFIES: this
    // EFFECTS: Uses skill and returns damage output, or -1 if insufficient mana/level
    public abstract int secondSkill();


    // MODIFIES: this
    // EFFECTS: Uses skill and returns damage output, or -1 if insufficient mana/level
    public abstract int thirdSkill();

    // MODIFIES: this
    // EFFECTS: Uses mana and sets cooldown for skill used
    public void usedSkill(int skillNumber) {
        if (skillNumber == 1) {
            this.mana -= this.getFirstSkillManaCost();
            this.firstSkillCoolDown = this.getMaxFirstSkillCoolDown();
        } else if (skillNumber == 2) {
            this.mana -= this.getSecondSkillManaCost();
            this.secondSkillCoolDown = this.getMaxSecondSkillCoolDown();
        } else {
            this.mana -= this.getThirdSkillManaCost();
            this.thirdSkillCoolDown = this.getMaxThirdSkillCoolDown();
        }
    }

    // MODIFIES: this
    // EFFECTS: Applies effects that happens each turn
    public void nextTurn() {
        this.regen();
        this.decreaseCoolDowns();
    }

    // MODIFIES: this
    // EFFECTS: Auto regenerates hero after turn ends
    private void regen() {
        increaseMana(this.getRegenFactor() * this.getLevel());
        increaseHealth(this.getRegenFactor() * this.getLevel());
    }

    // MODIFIES: this
    // EFFECTS: Decrease all skills cooldowns by one due to next turn
    private void decreaseCoolDowns() {
        if (this.firstSkillCoolDown > 0) {
            this.firstSkillCoolDown -= 1;
        }
        if (this.secondSkillCoolDown > 0) {
            this.secondSkillCoolDown -= 1;
        }
        if (this.thirdSkillCoolDown > 0) {
            this.thirdSkillCoolDown -= 1;
        }
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

    // EFFECTS: Returns a semi random value calculated off hero's strength
    public int basicAttack() {
        int baseDamage = (int) Math.round(this.getStrength() * ((100 - (1.5 * this.getStrength())) / 100.0001))
                * this.getAttackMultiplier();
        int minDamage = (int) Math.round(baseDamage * 0.75);
        int maxDamage = (int) Math.round(baseDamage * 1.25);
        Random random = new Random();
        int randomDamage = random.nextInt(maxDamage - minDamage) + minDamage;
        return randomDamage;

    }

    // MODIFIES: this
    // EFFECTS: Decrease health by based on damage
    // and return true if character still alive, otherwise false
    public Boolean takeDamage(int damage) {
        int damageAfterDefence = (int)
                Math.round(damage * (100 / (100 + (this.getDefenceMultiplier() * this.getDefence()))));
        this.health -= damageAfterDefence;
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
        this.firstSkillCoolDown = 0;
        this.secondSkillCoolDown = 0;
        this.thirdSkillCoolDown = 0;
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
            int increaseBy = this.getHealthAndManaPotionValue()
                    + (this.getLevel() - 1) * this.getPotionIncrement();
            this.increaseMana(increaseBy);
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
            int increaseBy = this.getHealthAndManaPotionValue()
                    + (this.getLevel() - 1) * this.getPotionIncrement();
            this.increaseHealth(increaseBy);
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: Heals the hero by increaseBy
    private void increaseHealth(int increaseBy) {
        int possibleNewHealth = this.getHealth() + increaseBy;
        if (possibleNewHealth > maxHealth) {
            this.health = maxHealth;
        } else {
            this.health = possibleNewHealth;
        }

    }

    // MODIFIES: this
    // EFFECTS: increase mana by increaseBy
    private void increaseMana(int increaseBy) {
        int possibleNewMana = this.getMana() + increaseBy;
        if (possibleNewMana > maxMana) {
            this.mana = maxMana;
        } else {
            this.mana = possibleNewMana;
        }
    }

    // EFFECTS: Checks if Character is dead, returns true if dead, otherwise false
    public Boolean isDead() {
        return this.health <= 0;
    }

    // EFFECTS: Returns true if character has skill points, else false.
    public Boolean hasSkillPoints() {
        return this.skillPoints > 0;
    }

    // MODIFIES: this
    // EFFECTS: Increases Strength by 1 and uses one skillPoint
    public void increaseStrength() {
        this.strength++;
        this.skillPoints--;
    }

    // MODIFIES: this
    // EFFECTS: Increases Defence by 1 and uses one skillPoint
    public void increaseDefence() {
        this.defence++;
        this.skillPoints--;
    }

    // MODIFIES: this
    // EFFECTS: Increases Agility by 1 and uses one skillPoint
    public void increaseAgility() {
        this.agility++;
        this.skillPoints--;
    }

    // MODIFIES: this
    // EFFECTS: Increases Intelligence by 1 and uses one skillPoint
    public void increaseIntelligence() {
        this.intelligence++;
        this.skillPoints--;
    }

    // MODIFIES: this
    // EFFECTS: Sets the level of hero for testing purposes
    public void setLevelForTest(int level) {
        this.level = level;
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

    public static int getMaxFirstSkillCoolDown() {
        return MAX_FIRST_SKILL_COOL_DOWN;
    }

    public static int getMaxSecondSkillCoolDown() {
        return MAX_SECOND_SKILL_COOL_DOWN;
    }

    public static int getMaxThirdSkillCoolDown() {
        return MAX_THIRD_SKILL_COOL_DOWN;
    }

    public int getFirstSkillCoolDown() {
        return firstSkillCoolDown;
    }

    public int getSecondSkillCoolDown() {
        return secondSkillCoolDown;
    }

    public int getThirdSkillCoolDown() {
        return thirdSkillCoolDown;
    }

    public static int getFirstSkillManaCost() {
        return FIRST_SKILL_MANA_COST;
    }

    public static int getSecondSkillManaCost() {
        return SECOND_SKILL_MANA_COST;
    }

    public static int getThirdSkillManaCost() {
        return THIRD_SKILL_MANA_COST;
    }

    public static double getDefenceMultiplier() {
        return DEFENCE_MULTIPLIER;
    }

    public static int getFirstSkillLevelRequirement() {
        return FIRST_SKILL_LEVEL_REQUIREMENT;
    }

    public static int getSecondSkillLevelRequirement() {
        return SECOND_SKILL_LEVEL_REQUIREMENT;
    }

    public static int getThirdSkillLevelRequirement() {
        return THIRD_SKILL_LEVEL_REQUIREMENT;
    }

    public static int getMaxLevel() {
        return MAX_LEVEL;
    }

    public int getExperienceRequiredToLevel() {
        return experienceRequiredToLevel;
    }

    public int getExperience() {
        return experience;
    }

    public static int getExperienceMultiplier() {
        return EXPERIENCE_MULTIPLIER;
    }

    public double getSkillMultiplier() {
        return skillMultiplier;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setExperienceRequiredToLevel(int experienceRequiredToLevel) {
        this.experienceRequiredToLevel = experienceRequiredToLevel;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setSkillPoints(int skillPoints) {
        this.skillPoints = skillPoints;
    }

    public static int getPotionIncrement() {
        return POTION_INCREMENT;
    }

    public static int getRegenFactor() {
        return REGEN_FACTOR;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonHero = new JSONObject();
        jsonHero.put("name", this.getName());
        jsonHero.put("heroClass", this.getHeroClass());
        jsonHero.put("level", this.getLevel());
        jsonHero.put("experience", this.getExperience());
        jsonHero.put("experienceRequiredToLevel", this.getExperienceRequiredToLevel());
        jsonHero.put("maxHealth", this.getMaxHealth());
        jsonHero.put("maxMana", this.getMaxMana());
        jsonHero.put("skillPoints", this.getSkillPoints());
        jsonHero.put("strength", this.getStrength());
        jsonHero.put("defence", this.getDefence());
        jsonHero.put("agility", this.getAgility());
        jsonHero.put("intelligence", this.getIntelligence());

        return jsonHero;
    }
}
