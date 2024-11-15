//試し入力、練習016コピペ。

public class Wizard extends Human {
	public Wizard(String name) {
		//this.name = name;
		//this.hp = 100;
	}
	
	public void run() {
		System.out.println("魔法使い" + this.name + "は、魔法を使って逃げた。");
	}
	
	public void talk() {
		System.out.println("魔法使い" + this.name + "は、魔法の呪文で話した。");
	}
	
	public void attack() {
		System.out.println("魔法使い" + this.name + "は、魔法を使って戦った。");
	}
}