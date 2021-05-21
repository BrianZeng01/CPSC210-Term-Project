package model;


// Archer hero
public class ArcherHero extends Hero {

    // EFFECTS: Creates new Archer hero with base agility 5
    //          ,archer class, and given name
    public ArcherHero(String name) {
        super(name);
        this.agility = SPECIAL_BASE_STAT;
        this.heroClass = "archer";
        this.skillMultiplier = 0.15;
    }

    @Override
    public int firstSkill() {
        if (getMana() < getFirstSkillManaCost() || getLevel() < getFirstSkillLevelRequirement()) {
            return -1;
        }
        int damage = (int) Math.round(basicAttack() * (1 + ((1 + getSkillMultiplier()) * getAgility())));

        super.usedSkill(1);
        return damage;
    }

    @Override
    public int secondSkill() {
        if (getMana() < getSecondSkillManaCost() || getLevel() < getSecondSkillLevelRequirement()) {
            return -1;
        }
        int damage = (int) Math.round(basicAttack() * (1 + ((1 + (2 * getSkillMultiplier())) * getAgility())));
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
            damage = (int) Math.round(basicAttack() * 1.5 * (1 + ((1 + (2 * getSkillMultiplier())) * getAgility())));
        } else {
            damage = 0;
        }

        super.usedSkill(3);
        return damage;
    }
}
