package Main;
import Design.Design;

public class Main {
	static Design window1 = null;

	public static Design getWindow1() {
		return window1;
	}

	public static void setWindow1(Design window1) {
		Main.window1 = window1;
	}

	public static void main(String[] args) {
		window1 = new Design();
		window1.setVisible(true);
	}
}