package Design;

import java.awt.BasicStroke;
import java.awt.CheckboxMenuItem;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import Action.MenuAction;
import Action.MouseAction;
import Action.ToolAction;
import Dao.DrawInfo;

public class Design extends Frame {
	private DrawInfo dao = new DrawInfo();
	private MenuBar mb = new MenuBar();

	private Menu Menu_file = new Menu("FILE");
	private MenuItem Item_new = new MenuItem("NEW");
	private MenuItem Item_load = new MenuItem("OPEN");
	private MenuItem Item_save = new MenuItem("SAVE");

	private Menu Menu_Tool = new Menu("FIGURE");
	private MenuItem Item_pen = new MenuItem("PEN");
	private MenuItem Item_line = new MenuItem("LINE");
	private MenuItem Item_poly = new MenuItem("POLY");
	private MenuItem Item_triangle = new MenuItem("TRIANGLE");
	private MenuItem Item_square = new MenuItem("RECT");
	private MenuItem Item_circle = new MenuItem("CIRCLE");

	private Menu Menu_Option = new Menu("OPTION");
	private MenuItem Item_undo = new MenuItem("UNDO");
	private MenuItem Item_redo = new MenuItem("REDO");
	private MenuItem Item_text = new MenuItem("TEXT");
	private MenuItem Item_color = new MenuItem("COLOR");

	private Menu Item_thick = new Menu("THICK");
	private MenuItem thick_up = new MenuItem("UP");
	private MenuItem thick_down = new MenuItem("DOWN");

	private MenuItem Item_eraser = new MenuItem("ERASER");
	private CheckboxMenuItem Item_fill = new CheckboxMenuItem("FILL");

	private int dist;

	private Color new_color;

	private ArrayList<DrawInfo> vc = new ArrayList<>();
	private ArrayList<DrawInfo> undo = new ArrayList<>();
	private ArrayList<Integer> before = new ArrayList<>();
	private ArrayList<Integer> after = new ArrayList<>();

	public DrawInfo getDao() {
		return dao;
	}

	public void setDao(DrawInfo dao) {
		this.dao = dao;
	}
	
	public int getDist() {
		return dist;
	}

	public void setDist(int dist) {
		this.dist = dist;
	}

	public Color getNew_color() {
		return new_color;
	}

	public void setNew_color(Color new_color) {
		this.new_color = new_color;
	}

	public CheckboxMenuItem getItem_fill() {
		return Item_fill;
	}

	public void setItem_fill(CheckboxMenuItem item_fill) {
		Item_fill = item_fill;
	}

	public ArrayList<DrawInfo> getVc() {
		return vc;
	}

	public void setVc(ArrayList<DrawInfo> vc) {
		this.vc = vc;
	}
	
	public ArrayList<DrawInfo> getUndo() {
		return undo;
	}
	
	public ArrayList<Integer> getBefore() {
		return before;
	}

	public void setBefore(ArrayList<Integer> before) {
		this.before = before;
	}

	public ArrayList<Integer> getAfter() {
		return after;
	}

	public void setAfter(ArrayList<Integer> after) {
		this.after = after;
	}

	public void setUndo(ArrayList<DrawInfo> undo) {
		this.undo = undo;
	}

	public Design() {
		super("Paint Board");
		init();
		start();
		setSize(1000, 700);
	}

	void init() {
		this.setMenuBar(mb);
		mb.add(Menu_file);
		Menu_file.add(Item_new);
		Menu_file.addSeparator();
		Menu_file.add(Item_load);
		Menu_file.add(Item_save);

		mb.add(Menu_Tool);
		Menu_Tool.add(Item_pen);
		Menu_Tool.add(Item_line);
		Menu_Tool.add(Item_poly);
		Menu_Tool.add(Item_square);
		Menu_Tool.add(Item_circle);
		Menu_Tool.add(Item_triangle);

		mb.add(Menu_Option);
		Menu_Option.add(Item_text);
		Menu_Option.add(Item_color);
		Menu_Option.add(Item_thick);
		Menu_Option.add(Item_fill);
		Menu_Option.add(Item_eraser);
		Menu_Option.add(Item_undo);
		Menu_Option.add(Item_redo);

		Item_thick.add(thick_up);
		Item_thick.add(thick_down);
	}

	void start() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		this.addMouseListener(new MouseAction());
		this.addMouseMotionListener(new MouseAction());

		Item_pen.addActionListener(new ToolAction());
		Item_line.addActionListener(new ToolAction());
		Item_poly.addActionListener(new ToolAction());
		Item_square.addActionListener(new ToolAction());
		Item_circle.addActionListener(new ToolAction());
		Item_triangle.addActionListener(new ToolAction());

		Item_color.addActionListener(new ToolAction());
		Item_fill.addItemListener(new ToolAction());
		Item_eraser.addActionListener(new MenuAction());
		Item_text.addActionListener(new MenuAction());
		Item_undo.addActionListener(new MenuAction());
		Item_redo.addActionListener(new MenuAction());
		
		Item_new.addActionListener(new MenuAction());
		Item_load.addActionListener(new MenuAction());
		Item_save.addActionListener(new MenuAction());

		thick_up.addActionListener(new MenuAction());
		thick_down.addActionListener(new MenuAction());
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Color c = dao.getColor();
		g2.setColor(c);
		g2.setStroke(new BasicStroke(dao.getThick()));
		if (dist == 1) {
			g2.drawLine(dao.getX(), dao.getY(), dao.getX1(), dao.getY1());
		} else if (dist == 2) {
			if (dao.isFill()) {
				g2.fillRect(dao.getX(), dao.getY(), dao.getX1() - dao.getX(), dao.getY1() - dao.getY());
			} else {
				g2.drawRect(dao.getX(), dao.getY(), dao.getX1() - dao.getX(), dao.getY1() - dao.getY());
			}
		} else if (dist == 3) {
			if (dao.isFill()) {
				g2.fillOval(dao.getX(), dao.getY(), dao.getX1() - dao.getX(), dao.getY1() - dao.getY());
			} else {
				g2.drawOval(dao.getX(), dao.getY(), dao.getX1() - dao.getX(), dao.getY1() - dao.getY());
			}
		} else if (dist == 4) {
			int[] x_points = { dao.getX1() - dao.getX(), dao.getX(), dao.getX1() + dao.getX() };
			int[] y_points = { dao.getY1() + dao.getY(), dao.getY(), dao.getY1() + dao.getY() };
			if (dao.isFill()) {
				g.drawPolygon(x_points, y_points, x_points.length);
			} else {
				g.fillPolygon(x_points, y_points, x_points.length);
			}
		}

		for (int i = 0; i < vc.size(); i++) {
			DrawInfo imsi = vc.get(i);
			g2.setColor(imsi.getColor());
			g2.setStroke(new BasicStroke(imsi.getThick()));
			if (imsi.getType() == 1 || imsi.getType() == 0) {
				g2.drawLine(imsi.getX(), imsi.getY(), imsi.getX1(), imsi.getY1());
			} else if (imsi.getType() == 2) {
				if (imsi.isFill()) {
					g2.fillRect(imsi.getX(), imsi.getY(), imsi.getX1() - imsi.getX(), imsi.getY1() - imsi.getY());
				} else {
					g2.drawRect(imsi.getX(), imsi.getY(), imsi.getX1() - imsi.getX(), imsi.getY1() - imsi.getY());
				}
			} else if (imsi.getType() == 3) {
				if (imsi.isFill()) {
					g2.fillOval(imsi.getX(), imsi.getY(), imsi.getX1() - imsi.getX(), imsi.getY1() - imsi.getY());
				} else {
					g2.drawOval(imsi.getX(), imsi.getY(), imsi.getX1() - imsi.getX(), imsi.getY1() - imsi.getY());
				}
			} else if (imsi.getType() == 4) {
				int[] x_points = { imsi.getX1() - imsi.getX(), imsi.getX(), imsi.getX1() + imsi.getX() };
				int[] y_points = { imsi.getY1() + imsi.getY(), imsi.getY(), imsi.getY1() + imsi.getY() };
				if (imsi.isFill()) {
					g.drawPolygon(x_points, y_points, x_points.length);
				} else {
					g.fillPolygon(x_points, y_points, x_points.length);
				}
			}
		}
	}
}