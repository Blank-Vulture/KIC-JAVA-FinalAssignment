package characters;

import utility.Character;
import utility.Fighter;
import utility.Talk;

public class Wizard extends Character implements Fighter, Talk {
    private double magicAttackMultiplier;

    public Wizard(String name, int maxHp, double magicAttackMultiplier) {
        super(name, maxHp);
        this.magicAttackMultiplier = magicAttackMultiplier;
    }

    @Override
    public void talk(String message) {
        System.out.println(getName() + "：「" + message + "」");
    }

    @Override
    public void attack(Fighter target) {
        if (!isAlive()) {
            System.out.println(getName() + "は攻撃できない！");
            return;
        }

        int damage = (int) ((Math.random() * 20) * magicAttackMultiplier) + 5;
        System.out.println(getName() + "は魔法で" + ((Character) target).getName() + "を攻撃した！" + damage + "のダメージ！");
        target.takeDamage(damage);
    }

    @Override
    public void takeDamage(int amount) {
        setHp(getHp() - amount);
        System.out.println(getName() + "は" + amount + "のダメージを受けた。（HP: " + getHp() + "/" + getMaxHp() + "）");
        if (!isAlive()) {
            System.out.println(getName() + "は倒れた！");
        }
    }

    public double getMagicAttackMultiplier() {
        return magicAttackMultiplier;
    }

    public void setMagicAttackMultiplier(double magicAttackMultiplier) {
        this.magicAttackMultiplier = magicAttackMultiplier;
    }
}