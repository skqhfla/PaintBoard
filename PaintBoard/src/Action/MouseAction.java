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
		// 마우스 현재 위치를 x1,y1에 저장
		x1 = main.getWindow1().getDi().getX1();
		y1 = main.getWindow1().getDi().getY1();
		x = main.getWindow1().getDi().getX();
		y = main.getWindow1().getDi().getY();
		// 도형 형태가 PEN(자유곡선)이면
		if (dist == 0) {
			Color c = color;
			DrawInfo di = new DrawInfo(x, y, x1, y1, dist, c, fill);
			// 마우스 버튼이 눌린 이전 위치와 드래그된 현재 위치로 이루어진.
			// 거의 '점' 수준의 아주 작은 Line을 그래픽 배열에 담는다.
			main.getWindow1().getVc().add(di);
			// 현재의 마우스 위치를 시작 위치로 재설정한다.
			main.getWindow1().getDi().setX(x1);
			main.getWindow1().getDi().setY(y1);
		} // 결과적으로 작은 라인들이 모여서 곡선으로 보이게 한다.
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
		// 도형의 시작, 종료위치와 색상, 채움여부를 그래픽배열에 저장한다.
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
