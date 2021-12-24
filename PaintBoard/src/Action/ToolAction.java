package Action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Design.ColorChart;
import Main.Main;

public class ToolAction implements ActionListener {
	Main main = new Main();
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			tryingCorrectNumberFormats(e);
		} catch (NumberFormatException e2) {
		}
	}

	private void tryingCorrectNumberFormats(ActionEvent e) {
		if (e.getActionCommand().equals("색상")) {
			ColorChart chart = null;
			chart = new ColorChart();
			chart.setVisible(true);
		} else if (e.getActionCommand().equals("크기")) {

		} else if (e.getActionCommand().equals("도구")) {

		} else if (e.getActionCommand().equals("도형")) {
			main.getWindow1().setDist(2);
		}
	}
}
