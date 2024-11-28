package characters;

import utility.Character;
import utility.Fighter;
import utility.Talk;

/**
 * 魔法使いクラス
 */
public class Wizard extends Character implements Fighter, Talk {
    private int maxAttackPower;

    public Wizard(String name, int maxHp, int heroBaseAttackPower) {
        super(name, maxHp);
        // 魔法使いの最大攻撃力は勇者の1.5倍
        this.maxAttackPower = (int) (heroBaseAttackPower * 1.5);
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

        // ダメージを0から最大攻撃力までの乱数で決定
        int damage = (int) (Math.random() * maxAttackPower) + 1;
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

    public int getMaxAttackPower() {
        return maxAttackPower;
    }

    public void setMaxAttackPower(int maxAttackPower) {
        this.maxAttackPower = maxAttackPower;
    }
}