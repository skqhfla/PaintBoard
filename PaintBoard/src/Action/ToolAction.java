package Action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import Design.ColorChart;
import Main.Main;

public class ToolAction implements ActionListener, ItemListener {
	Main main = new Main();

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			tryingCorrectNumberFormats(e);
		} catch (NumberFormatException e2) {
		}
	}

	private void tryingCorrectNumberFormats(ActionEvent e) {
		if (e.getActionCommand().equals("PEN")) {
			main.getWindow1().setDist(0);
		} else if (e.getActionCommand().equals("LINE")) {
			main.getWindow1().setDist(1);
		} else if (e.getActionCommand().equals("RECT")) {
			main.getWindow1().setDist(2);
		} else if (e.getActionCommand().equals("CIRCLE")) {
			main.getWindow1().setDist(3);
		} else if (e.getActionCommand().equals("COLOR")) {
			ColorChart chart = new ColorChart();
			chart.setVisible(true);
		} else if (e.getActionCommand().equals("TRIANGLE")) {
			main.getWindow1().setDist(4);
		} else if (e.getActionCommand().equals("POLY")) {
			main.getWindow1().setDist(5);
		} 
		
		System.out.println("dist = " + main.getWindow1().getDist());
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == main.getWindow1().getItem_fill())
			main.getWindow1().getDao().setFill(!main.getWindow1().getDao().isFill());
	}
}