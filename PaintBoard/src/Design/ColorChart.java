package Design;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Main.Main;

public class ColorChart extends JFrame {
	Color newForegroundColor;
	Main main = new Main();
	
	public ColorChart() {
		setName("Color");
		setBounds(100, 100, 450, 300);
		
		final JColorChooser chooser = new JColorChooser();
		ColorSelectionModel model = chooser.getSelectionModel();

		ChangeListener listener = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				newForegroundColor = chooser.getColor();
				main.getWindow1().setNew_color(newForegroundColor);
			}
		};
		
		model.addChangeListener(listener);
		add(chooser);
		main.getWindow1().setNew_color(newForegroundColor);
		pack();
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
	}
	
	
}