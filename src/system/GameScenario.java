package system;

import utility.Character;
import utility.Fighter;

import java.util.ArrayList;
import java.util.List;

public class GameScenario {

    private List<Character> characters = new ArrayList<>();

    // シナリオを開始する
    public void startScenario() {
        System.out.println("ゲームシナリオが始まりました。");

        // トークイベントを挿入
        System.out.println("\n=== トークイベント ===");
        characters.Hero hero = null;
        for (Character character : characters) {
            if (character instanceof characters.Hero) {
                hero = (characters.Hero) character;
                break;
            }
        }
        if (hero != null) {
            hero.talk("今日も冒険に出かけるぞ！");
        }
        System.out.println("==================\n");
    }

    // キャラクターをシナリオに追加する
    public void addCharacter(Character character) {
        characters.add(character);
    }

    // バトルを開始する
    public void startBattle(Fighter fighter1, Fighter fighter2) {
        Battle battle = new Battle(fighter1, fighter2);
        battle.startBattle();
    }

    // シナリオの次の部分に進む
    public void proceedToNextScenarioPart() {
        System.out.println("シナリオの次の部分に進みます。");
        // 次のシナリオ部分への処理をここに追加
    }
}