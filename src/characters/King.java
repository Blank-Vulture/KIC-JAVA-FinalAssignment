package characters;

import utility.Character;
import utility.Talk;

public class King extends Character implements Talk {

    public King(String name, int maxHp) {
        super(name, maxHp);
    }

    @Override
    public void talk(String message) {
        System.out.println(getName() + "：「" + message + "」");
    }
}