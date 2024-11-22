package system;

import characters.Hero;
import characters.Slime;
import utility.Talk; // utility.Talkをインポート

public class Main {
    public static void main(String[] args) {
        // ゲームシナリオを作成
        GameScenario scenario = new GameScenario();

        // ヒーローを作成
        Hero hero = new Hero("勇者アーサー", 100, 20);

        // スライムを作成
        Slime slime = new Slime("スライム", 30, 5);

        // キャラクターをシナリオに追加
        scenario.addCharacter(hero);
        scenario.addCharacter(slime);

        // トークイベントをテスト的に実装
        System.out.println("\n=== メインプログラムでのトークイベント ===");
        Talk talkingCharacter = hero; // HeroはTalkを実装しているため、Talk型に代入可能
        talkingCharacter.talk("これから冒険が始まる！");
        System.out.println("================================\n");

        // シナリオを開始
        scenario.startScenario();

        // ヒーローとスライムのバトルを開始
        scenario.startBattle(hero, slime);

        // シナリオの次の部分に進む
        scenario.proceedToNextScenarioPart();
    }
}