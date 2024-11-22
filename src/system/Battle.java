package system;

import utility.Character;
import utility.Fighter;
import utility.Runner;
import characters.Princess;
import characters.SuperHero;

/**
 * バトルクラス
 */
public class Battle {
    private Character participant1; // プレイヤー（勇者）
    private Character participant2; // 敵
    private int turnCount;
    private boolean battleOver;
    private Princess princess;
    private int originalAttackPower; // スーパー勇者の元の攻撃力を保存

    public Battle(Character participant1, Character participant2) {
        this.participant1 = participant1;
        this.participant2 = participant2;
        this.turnCount = 0;
        this.battleOver = false;
        this.princess = null;
    }

    // お姫様が応援する場合のコンストラクタ
    public Battle(Character participant1, Character participant2, Princess princess) {
        this(participant1, participant2);
        this.princess = princess;
        if (participant1 instanceof SuperHero) {
            // スーパー勇者の元の攻撃力を保存
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

        // お姫様が応援する場合
        if (princess != null && participant1 instanceof SuperHero) {
            princess.cheer((SuperHero) participant1);
        }

        // participant1の行動（ユーザー入力による）
        if (participant1.isAlive()) {
            if (participant1 instanceof Fighter) {
                ((Fighter) participant1).attack((Fighter) participant2);
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
        if (participant2.isAlive()) {
            if (participant2 instanceof Fighter) {
                ((Fighter) participant2).attack((Fighter) participant1);
            }
            if (!participant1.isAlive() || (participant1 instanceof Runner && ((Runner) participant1).isRunAway())) {
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
    }
}