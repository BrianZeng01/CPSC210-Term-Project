package model;


// Archer hero
public class ArcherHero extends Hero {

    // EFFECTS: Creates new Archer hero with base agility 5
    //          ,archer class, and given name
    public ArcherHero(String name) {
        super(name);
        this.agility = SPECIAL_BASE_STAT;
        this.heroClass = "archer";
        this.skillMultiplier = 0.25;
    }

    @Override
    public int firstSkill() {
        if (getMana() < getFirstSkillManaCost()) {
            return -1;
        }
        int damage = (int) Math.round(basicAttack() * (1 + ((1 + getSkillMultiplier()) * getAgility())));

        super.usedSkill(1);
        return damage;
    }

    @Override
    public int secondSkill() {
        if (getMana() < getSecondSkillManaCost()) {
            return -1;
        }
        int damage = (int) Math.round(basicAttack() * (1 + ((1 + (2 * getSkillMultiplier())) * getAgility())));
        super.usedSkill(2);
        return damage;
    }

    @Override
    public int thirdSkill() {
        if (getMana() < getThirdSkillManaCost()) {
            return -1;
        }

        int damage;
        if (Math.random() >= 0.5) {
            damage = (int) Math.round(basicAttack() * 3 * (1 + ((1 + (2 * getSkillMultiplier())) * getAgility())));
        } else {
            damage = 0;
        }

        super.usedSkill(3);
        return damage;
    }
}
