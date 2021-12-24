package Action;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Dao.DrawInfo;
import Main.Main;

public class MouseAction implements MouseListener, MouseMotionListener {
	Main main = new Main();
	private int x,y,x1,y1;
	private int dist;
	private int type;
	private Color color;
	private boolean fill;
	
	@Override
	public void mouseDragged(MouseEvent e) {
		dist = main.getWindow1().getDist();
		color = main.getWindow1().getDi().getColor();
		fill = main.getWindow1().getDi().isFill();
		main.getWindow1().getDi().setX1(e.getX());
		main.getWindow1().getDi().setY1(e.getY());
		// ���콺 ���� ��ġ�� x1,y1�� ����
		x1 = main.getWindow1().getDi().getX1();
		y1 = main.getWindow1().getDi().getY1();
		x = main.getWindow1().getDi().getX();
		y = main.getWindow1().getDi().getY();
		// ���� ���°� PEN(�����)�̸�
		if (dist == 0) {
			Color c = color;
			DrawInfo di = new DrawInfo(x, y, x1, y1, dist, c, fill);
			// ���콺 ��ư�� ���� ���� ��ġ�� �巡�׵� ���� ��ġ�� �̷����.
			// ���� '��' ������ ���� ���� Line�� �׷��� �迭�� ��´�.
			main.getWindow1().getVc().add(di);
			// ������ ���콺 ��ġ�� ���� ��ġ�� �缳���Ѵ�.
			main.getWindow1().getDi().setX(x1);
			main.getWindow1().getDi().setY(y1);
		} // ��������� ���� ���ε��� �𿩼� ����� ���̰� �Ѵ�.
		main.getWindow1().repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//System.out.println("X = " + e.getX() + " Y = " + e.getY());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		main.getWindow1().getDi().setX(e.getX());
		main.getWindow1().getDi().setY(e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		dist = main.getWindow1().getDist();
		color = Color.black;
		fill = main.getWindow1().getDi().isFill();
		x1 = main.getWindow1().getDi().getX1();
		y1 = main.getWindow1().getDi().getY1();
		x = main.getWindow1().getDi().getX();
		y = main.getWindow1().getDi().getY();
		Color c = color;
		DrawInfo di = new DrawInfo(x, y, x1, y1, dist, c, fill);
		// ������ ����, ������ġ�� ����, ä�򿩺θ� �׷��ȹ迭�� �����Ѵ�.
		main.getWindow1().getVc().add(di);
		main.getWindow1().repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
