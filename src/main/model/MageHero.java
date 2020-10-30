package model;


// Mage hero
public class MageHero extends Hero {

    // EFFECTS: Creates new mage hero with base intelligence 5
    //          ,mage class, and given name
    public MageHero(String name) {
        super(name);
        this.intelligence = SPECIAL_BASE_STAT;
        this.heroClass = "mage";
        this.skillMultiplier = 0.25;
    }

    @Override
    public int firstSkill() {
        if (getMana() < getFirstSkillManaCost() || getLevel() < getFirstSkillLevelRequirement()) {
            return -1;
        }
        int damage = (int) Math.round(basicAttack() * (1 + ((1 + getSkillMultiplier()) * getIntelligence())));

        super.usedSkill(1);
        return damage;
    }

    @Override
    public int secondSkill() {
        if (getMana() < getSecondSkillManaCost() || getLevel() < getSecondSkillLevelRequirement()) {
            return -1;
        }
        int damage = (int) Math.round(basicAttack() * (1 + ((1 + (2 * getSkillMultiplier())) * getIntelligence())));
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
            damage = (int) Math.round(basicAttack() * 3 * (1 + ((1 + (2 * getSkillMultiplier())) * getIntelligence())));
        } else {
            damage = 0;
        }

        super.usedSkill(3);
        return damage;
    }
}
