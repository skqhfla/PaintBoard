package Action;

import java.awt.FileDialog;
import java.awt.Frame;
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
		if (e.getActionCommand().equals("저장하기")) {
			FileDialog fdl = new FileDialog(Main.getWindow1(), "저장", FileDialog.SAVE);
			fdl.setVisible(true);
			String dir = fdl.getDirectory();
			String file = fdl.getFile();
			if (dir == null || file == null) {
				return;
			}
			try {
				ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File(dir, file))));
				oos.writeObject(main.getWindow1().getVc());
				oos.close();
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}
		else if (e.getActionCommand().equals("불러오기")) {
			FileDialog fdlg = new FileDialog(main.getWindow1(), "열기", FileDialog.LOAD);
			fdlg.setVisible(true);
			String dir = fdlg.getDirectory();
			String file = fdlg.getFile();
			if (dir == null || file == null) {
				return;
			}
			try {
				ObjectInputStream oos = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File(dir, file))));
				main.getWindow1().setVc((ArrayList<DrawInfo>) oos.readObject());
				oos.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} 
		else if (e.getActionCommand().equals("새로만들기")) {
			main.getWindow1().getVc().clear();
			// 그래픽배열을 비워서 초기화
			main.getWindow1().getDi().setX(0);
			main.getWindow1().getDi().setY(0);
			main.getWindow1().getDi().setX1(0);
			main.getWindow1().getDi().setY1(0);
			main.getWindow1().setDist(0);
			// 좌표와 도형 정보를 0으로 초기화
			main.getWindow1().repaint();
		}
	}

}
