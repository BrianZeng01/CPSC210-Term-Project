package model;


// Warrior hero
public class WarriorHero extends Hero {

    // EFFECTS: Creates new warrior hero with base defence 5
    //          ,warrior class, and given name
    public WarriorHero(String name) {
        super(name);
        this.defence = SPECIAL_BASE_STAT;
        this.heroClass = "warrior";
        this.skillMultiplier = 0.05;
    }

    @Override
    public int firstSkill() {
        if (getMana() < getFirstSkillManaCost() || getLevel() < getFirstSkillLevelRequirement()) {
            return -1;
        }
        int damage = (int) Math.round(basicAttack() * (1 + (getSkillMultiplier() * getStrength())));

        super.usedSkill(1);
        return damage;
    }

    @Override
    public int secondSkill() {
        if (getMana() < getSecondSkillManaCost() || getLevel() < getSecondSkillLevelRequirement()) {
            return -1;
        }
        int damage = (int) Math.round(basicAttack() * (1 + ((2 * getSkillMultiplier()) * getStrength())));
        super.usedSkill(2);
        return damage;
    }

    @Override
    public int thirdSkill() {
        if (getMana() < getThirdSkillManaCost() || getLevel() < getThirdSkillLevelRequirement()) {
            return -1;
        }

        int damage;
        if (Math.random() >= 0.5) {
            damage = (int) Math.round(basicAttack() * 1.5 * (1 + ((4 * getSkillMultiplier()) * getStrength())));
        } else {
            damage = 0;
        }

        super.usedSkill(3);
        return damage;
    }
}
