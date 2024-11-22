package characters;

import utility.Character;
import utility.Fighter;
import utility.Runner;

/**
 * スライムクラス
 */
public class Slime extends Character implements Fighter, Runner {
    private int baseAttackPower;
    private boolean runAway;

    public Slime(String name, int maxHp, int baseAttackPower) {
        super(name, maxHp);
        this.baseAttackPower = baseAttackPower;
        this.runAway = false;
    }

    @Override
    public void attack(Fighter target) {
        if (runAway || !isAlive()) {
            System.out.println(getName() + "は攻撃できない！");
            return;
        }

        // 20%の確率で逃げる
        if (Math.random() < 0.2) {
            runAway();
            return;
        }

        int maxDamage = baseAttackPower / 2; // 勇者の攻撃力の半分
        if (maxDamage < 1) {
            maxDamage = 1; // 最低でも1
        }
        int damage = (int) (Math.random() * maxDamage) + 1; // 1からmaxDamageまでの乱数
        System.out.println(getName() + "は毒を吐いて" + ((Character) target).getName() + "を攻撃した！" + damage + "のダメージ！");
        target.takeDamage(damage);
    }

    @Override
    public void takeDamage(int amount) {
        setHp(getHp() - amount);
        System.out.println(getName() + "は" + amount + "のダメージを受けた。（HP: " + getHp() + "/" + getMaxHp() + "）");
        if (!isAlive()) {
            System.out.println(getName() + "は倒された！");
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
    
    @Override
    public void resetRunAway() {
        runAway = false;
    }

    public int getBaseAttackPower() {
        return baseAttackPower;
    }

    public void setBaseAttackPower(int baseAttackPower) {
        this.baseAttackPower = baseAttackPower;
    }
}