package Dao;

import java.awt.Color;
import java.awt.Stroke;
import java.io.Serializable;

public class DrawInfo implements Serializable {
	private int x, y, x1, y1;
	private int thick;
	private int type;
	private Color color;
	private boolean fill;

	public DrawInfo() {
	}

	public DrawInfo(int x, int y, int x1, int y1, int thick, int type, Color color, boolean fill) {
		this.x = x;
		this.y = y;
		this.x1 = x1;
		this.y1 = y1;
		this.thick = thick;
		this.type = type;
		this.color = color;
		this.fill = fill;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getThick() {
		return thick;
	}

	public void setThick(int thick) {
		this.thick = thick;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isFill() {
		return fill;
	}

	public void setFill(boolean fill) {
		this.fill = fill;
	}
}
