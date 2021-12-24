package Main;
import Design.Board;

public class Main {
	static Board window1 = null;

	public static Board getWindow1() {
		return window1;
	}

	public static void setWindow1(Board window1) {
		Main.window1 = window1;
	}

	public static void main(String[] args) {
		window1 = new Board();
		window1.setVisible(true);
	}
}
