package characters;

import utility.Character;
import utility.Talk;

/**
 * お姫様クラス
 */
public class Princess extends Character implements Talk {

    public Princess(String name, int maxHp) {
        super(name, maxHp);
    }

    @Override
    public void talk(String message) {
        System.out.println(getName() + "：「" + message + "」");
    }

    // 応援する
    public void cheer(SuperHero hero) {
        System.out.println(getName() + "は" + hero.getName() + "を応援している！");
        int boostedAttackPower = hero.getBaseAttackPower() * 2; // 攻撃力を2倍に
        hero.setBaseAttackPower(boostedAttackPower);
    }
}