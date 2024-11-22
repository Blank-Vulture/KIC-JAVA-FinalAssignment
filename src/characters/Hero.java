package characters;

import utility.Character;
import utility.Fighter;
import utility.Runner;
import utility.Talk;

public class Hero extends Character implements Fighter, Runner, Talk {
    private int baseAttackPower;
    private boolean runAway;

    public Hero(String name, int maxHp, int baseAttackPower) {
        super(name, maxHp);
        this.baseAttackPower = baseAttackPower;
        this.runAway = false;
    }

    @Override
    public void talk(String message) {
        System.out.println(getName() + "：「" + message + "」");
    }

    @Override
    public void attack(Fighter target) {
        if (runAway || !isAlive()) {
            System.out.println(getName() + "は攻撃できない！");
            return;
        }
        System.out.println(getName() + "は" + ((utility.Character) target).getName() + "を攻撃した！" + baseAttackPower + "のダメージ！");
        target.takeDamage(baseAttackPower);
    }

    @Override
    public void takeDamage(int amount) {
        setHp(getHp() - amount);
        System.out.println(getName() + "は" + amount + "のダメージを受けた。（HP: " + getHp() + "/" + getMaxHp() + "）");
        if (!isAlive()) {
            System.out.println(getName() + "は倒れてしまった！");
        }
    }

    @Override
    public void runAway() {
        runAway = true;
        System.out.println(getName() + "は逃げ出した！");
    }

    @Override
    public boolean isRunAway() {
        return runAway;
    }

    public void sleep() {
        if (runAway || !isAlive()) {
            System.out.println(getName() + "は眠れない！");
            return;
        }
        System.out.println(getName() + "は眠ってHPを回復した！");
        setHp(getHp() + 20); // HPを20回復
        System.out.println("（HP: " + getHp() + "/" + getMaxHp() + "）");
    }

    public int getBaseAttackPower() {
        return baseAttackPower;
    }

    public void setBaseAttackPower(int baseAttackPower) {
        this.baseAttackPower = baseAttackPower;
    }
}