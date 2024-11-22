package system;

import utility.Character;
import utility.Fighter;
import utility.Runner;
import characters.Princess;
import characters.Hero;
import characters.SuperHero;
import characters.Wizard;

import java.util.Scanner;

/**
 * バトルクラス
 */
public class Battle {
    private Character participant1; // 勇者またはスーパー勇者
    private Character participant2; // 敵キャラクター
    private Wizard ally; // 魔法使い（味方）
    private int turnCount;
    private boolean battleOver;
    private Princess princess;
    private int originalAttackPower; // スーパー勇者の元の攻撃力を保存
    private Scanner scanner;

    // コンストラクタ
    public Battle(Character participant1, Character participant2, Wizard ally, Princess princess, Scanner scanner) {
        this.participant1 = participant1;
        this.participant2 = participant2;
        this.ally = ally;
        this.princess = princess;
        this.scanner = scanner;
        this.turnCount = 0;
        this.battleOver = false;
        if (participant1 instanceof SuperHero) {
            this.originalAttackPower = ((SuperHero) participant1).getBaseAttackPower();
        }
    }

    public void startBattle() {
        // バトル開始前に逃走状態とHPをリセット
        resetParticipants();

        System.out.println(participant1.getName() + "と" +
                participant2.getName() + "の戦いが始まった！");
        determineTurnOrder();
        while (!isBattleOver()) {
            executeTurn();
        }
        endBattle();
    }

    public void executeTurn() {
        turnCount++;
        System.out.println("\nターン " + turnCount + ":");

        // お姫様が40%の確率で応援する
        if (princess != null && participant1 instanceof SuperHero) {
            if (Math.random() < 0.4) {
                princess.cheer((SuperHero) participant1);
            } else {
                System.out.println(princess.getName() + "は応援していない。");
            }
        }

        // participant1の行動（ユーザー入力による）
        if (participant1.isAlive() && !battleOver) {
            if (participant1 instanceof Hero) {
                ((Hero) participant1).takeTurn((Fighter) participant2, scanner);
            } else if (participant1 instanceof SuperHero) {
                ((SuperHero) participant1).takeTurn((Fighter) participant2, scanner);
            } else if (participant1 instanceof Fighter) {
                ((Fighter) participant1).attack((Fighter) participant2);
            }

            // 魔法使いの攻撃
            if (ally != null && ally.isAlive()) {
                ally.attack((Fighter) participant2);
            }

            if (!participant2.isAlive() || (participant2 instanceof Runner && ((Runner) participant2).isRunAway())) {
                battleOver = true;
                return;
            }
            if (participant1 instanceof Runner && ((Runner) participant1).isRunAway()) {
                battleOver = true;
                return;
            }
        }

        // participant2の行動
        if (participant2.isAlive() && !battleOver) {
            if (participant2 instanceof Fighter) {
                // 攻撃対象を決定（勇者または魔法使い）
                if (ally != null && ally.isAlive()) {
                    // ランダムに攻撃対象を決める
                    if (Math.random() < 0.5) {
                        ((Fighter) participant2).attack((Fighter) participant1);
                    } else {
                        ((Fighter) participant2).attack(ally);
                    }
                } else {
                    // 魔法使いがいない場合は勇者を攻撃
                    ((Fighter) participant2).attack((Fighter) participant1);
                }
            }
            if (!participant1.isAlive() || (participant1 instanceof Runner && ((Runner) participant1).isRunAway())) {
                battleOver = true;
                return;
            }
            if (participant2 instanceof Runner && ((Runner) participant2).isRunAway()) {
                battleOver = true;
                return;
            }
        }

        // 応援の効果を一時的なものにするため、攻撃後に攻撃力を元に戻す
        if (princess != null && participant1 instanceof SuperHero) {
            ((SuperHero) participant1).setBaseAttackPower(originalAttackPower);
        }
    }

    public void endBattle() {
        if (!participant1.isAlive()) {
            System.out.println("\n" + participant1.getName() + "は倒れた！");
            System.out.println(participant2.getName() + "の勝利！");
        } else if (!participant2.isAlive()) {
            System.out.println("\n" + participant2.getName() + "は倒れた！");
            System.out.println(participant1.getName() + "の勝利！");
        } else if (participant1 instanceof Runner && ((Runner) participant1).isRunAway()) {
            System.out.println("\n" + participant1.getName() + "は逃げ出した！");
            System.out.println(participant2.getName() + "の勝利！");
        } else if (participant2 instanceof Runner && ((Runner) participant2).isRunAway()) {
            System.out.println("\n" + participant2.getName() + "は逃げ出した！");
            System.out.println(participant1.getName() + "の勝利！");
        }

        // 戦闘終了後に攻撃力を元に戻す
        if (princess != null && participant1 instanceof SuperHero) {
            ((SuperHero) participant1).setBaseAttackPower(originalAttackPower);
        }
    }

    public boolean isBattleOver() {
        return battleOver;
    }

    public void determineTurnOrder() {
        // participant1が先攻とする
        System.out.println(participant1.getName() + "が先に攻撃する！");
    }

    // 参加者の逃走状態とHPをリセットするメソッド
    private void resetParticipants() {
        // participant1 のリセット
        if (participant1 instanceof Runner) {
            ((Runner) participant1).resetRunAway();
        }
        participant1.setHp(participant1.getMaxHp());

        // participant2 のリセット
        if (participant2 instanceof Runner) {
            ((Runner) participant2).resetRunAway();
        }
        participant2.setHp(participant2.getMaxHp());

        // 魔法使いのリセット
        if (ally != null) {
            ally.setHp(ally.getMaxHp());
        }
    }
}