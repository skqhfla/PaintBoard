package Action;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Dao.DrawInfo;
import Main.Main;

public class MenuAction implements ActionListener {
	Main main = new Main();

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			tryingCorrectNumberFormats(e);
		} catch (NumberFormatException e2) {
		}
	}

	@SuppressWarnings("unchecked")
	private void tryingCorrectNumberFormats(ActionEvent e) {
		if (e.getActionCommand().equals("SAVE")) {
			FileDialog fdl = new FileDialog(Main.getWindow1(), "SAVE", FileDialog.SAVE);
			fdl.setVisible(true);
			String dir = fdl.getDirectory();
			String file = fdl.getFile();
			if (dir == null || file == null) {
				return;
			}
			try {
				ObjectOutputStream oos = new ObjectOutputStream(
						new BufferedOutputStream(new FileOutputStream(new File(dir, file))));
				oos.writeObject(main.getWindow1().getVc());
				oos.close();
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		} else if (e.getActionCommand().equals("OPEN")) {
			FileDialog fdlg = new FileDialog(main.getWindow1(), "OPEN", FileDialog.LOAD);
			fdlg.setVisible(true);
			String dir = fdlg.getDirectory();
			String file = fdlg.getFile();
			if (dir == null || file == null) {
				return;
			}
			try {
				ObjectInputStream oos = new ObjectInputStream(
						new BufferedInputStream(new FileInputStream(new File(dir, file))));
				main.getWindow1().setVc((ArrayList<DrawInfo>) oos.readObject());
				oos.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (e.getActionCommand().equals("NEW")) {
			main.getWindow1().getVc().clear();
			main.getWindow1().getUndo().clear();
			main.getWindow1().getBefore().clear();
			main.getWindow1().getAfter().clear();
			main.getWindow1().setDist(0);
			main.getWindow1().repaint();
			System.out.println("reset");
		} else if (e.getActionCommand().equals("UP")) {
			if (main.getWindow1().getDao().getThick() >= 100)
				main.getWindow1().getDao().setThick(1);
			else
				main.getWindow1().getDao().setThick(main.getWindow1().getDao().getThick() + 5);
		} else if (e.getActionCommand().equals("DOWN")) {
			if (main.getWindow1().getDao().getThick() <= 5)
				main.getWindow1().getDao().setThick(1);
			else
				main.getWindow1().getDao().setThick(main.getWindow1().getDao().getThick() - 5);
		} else if (e.getActionCommand().equals("ERASER")) {
			main.getWindow1().getDao().setThick(1);
			main.getWindow1().setDist(0);
			main.getWindow1().getDao().setThick(main.getWindow1().getDao().getThick());
			main.getWindow1().setNew_color(new Color(255, 255, 255));
		} else if (e.getActionCommand().equals("UNDO")) {
			System.out.println("before size = " + main.getWindow1().getVc().size());

			for (int i = 0; i < main.getWindow1().getAfter().get(main.getWindow1().getAfter().size()-1) - main.getWindow1().getBefore().get(main.getWindow1().getBefore().size()-1); i++) {
				main.getWindow1().getUndo().add(main.getWindow1().getVc().get(main.getWindow1().getVc().size() - 1));
				main.getWindow1().getVc().remove(main.getWindow1().getVc().size() - 1);
			}
			//main.getWindow1().repaint();
			System.out.println("after size = " + main.getWindow1().getVc().size());
		} else if (e.getActionCommand().equals("REDO")) {
			for (int i = 0; i < main.getWindow1().getUndo().size(); i++) {
				main.getWindow1().getVc().add(main.getWindow1().getUndo().get(main.getWindow1().getUndo().size() - 1));
				main.getWindow1().getUndo().remove(main.getWindow1().getUndo().size() - 1);
			}
			//main.getWindow1().repaint();
		}
	}

}