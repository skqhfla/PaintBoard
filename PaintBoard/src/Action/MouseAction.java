package Action;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Dao.DrawInfo;
import Main.Main;

public class MouseAction implements MouseListener, MouseMotionListener{
	Main main = new Main();
	
	@Override
	public void mouseDragged(MouseEvent e) {
		main.getWindow1().getDao().setX1(e.getX());
		main.getWindow1().getDao().setY1(e.getY());

		if (main.getWindow1().getDist() == 0) {
			Color c = main.getWindow1().getNew_color();
			DrawInfo di = new DrawInfo(main.getWindow1().getDao().getX(), main.getWindow1().getDao().getY(), main.getWindow1().getDao().getX1(), main.getWindow1().getDao().getY1(), main.getWindow1().getDao().getThick(), main.getWindow1().getDist(), c, main.getWindow1().getDao().isFill());
			main.getWindow1().getVc().add(di);
			main.getWindow1().getDao().setX(main.getWindow1().getDao().getX1());
			main.getWindow1().getDao().setY(main.getWindow1().getDao().getY1());
		}
		main.getWindow1().repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		main.getWindow1().getDao().setX(e.getX());
		main.getWindow1().getDao().setY(e.getY());
		main.getWindow1().getBefore().add(main.getWindow1().getVc().size());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		main.getWindow1().getDao().setX1(e.getX());
		main.getWindow1().getDao().setY1(e.getY());
		Color c = main.getWindow1().getNew_color();
		DrawInfo di = new DrawInfo(main.getWindow1().getDao().getX(), main.getWindow1().getDao().getY(), main.getWindow1().getDao().getX1(), main.getWindow1().getDao().getY1(), main.getWindow1().getDao().getThick(), main.getWindow1().getDist(), c, main.getWindow1().getDao().isFill());
		main.getWindow1().getVc().add(di);
		main.getWindow1().getAfter().add(main.getWindow1().getVc().size());
		main.getWindow1().repaint();
	}

}
