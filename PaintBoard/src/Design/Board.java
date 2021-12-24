package Design;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

import Action.MenuAction;
import Action.MouseAction;
import Action.ToolAction;
import Dao.DrawInfo;

public class Board extends JFrame {
	private JPanel contentPane;
	private ArrayList<DrawInfo> vc = new ArrayList<>();
	DrawInfo di = new DrawInfo();
	int dist;

	public ArrayList<DrawInfo> getVc() {
		return vc;
	}

	public void setVc(ArrayList<DrawInfo> vc) {
		this.vc = vc;
	}

	public DrawInfo getDi() {
		return di;
	}

	public void setDi(DrawInfo di) {
		this.di = di;
	}

	public int getDist() {
		return dist;
	}

	public void setDist(int dist) {
		this.dist = dist;
	}

	public Board() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setSize(500, 500);
		setTitle("Paint Board");

		// 메뉴바
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// 메뉴 콘텐츠
		// 파일
		JMenu Menu_file = new JMenu("파일");
		menuBar.add(Menu_file);

		// 메뉴 아이펨
		// 새로만들기
		JMenuItem Item_new = new JMenuItem("새로만들기");
		Menu_file.add(Item_new);
		// 저장하기
		JMenuItem Item_save = new JMenuItem("저장하기");
		Menu_file.add(Item_save);
		// 불러오기
		JMenuItem Item_load = new JMenuItem("불러오기");
		Menu_file.add(Item_load);

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JToolBar toolBar = new JToolBar();
		toolBar.setBackground(SystemColor.window);
		GridBagConstraints gbc_toolBar = new GridBagConstraints();
		gbc_toolBar.insets = new Insets(0, 0, 5, 5);
		gbc_toolBar.fill = GridBagConstraints.BOTH;
		gbc_toolBar.gridx = 0;
		gbc_toolBar.gridy = 0;
		contentPane.add(toolBar, gbc_toolBar);

		JButton button_color = new JButton("색상");
		button_color.setBackground(Color.WHITE);
		toolBar.add(button_color);

		JButton button_size = new JButton("크기");
		button_size.setBackground(Color.WHITE);
		toolBar.add(button_size);

		JButton button_tool = new JButton("도구");
		button_tool.setBackground(Color.WHITE);
		toolBar.add(button_tool);

		JButton button_figure = new JButton("도형");
		button_figure.setBackground(Color.WHITE);
		toolBar.add(button_figure);

		Item_new.addActionListener(new MenuAction());
		Item_save.addActionListener(new MenuAction());
		Item_load.addActionListener(new MenuAction());
		button_color.addActionListener(new ToolAction());
		button_size.addActionListener(new ToolAction());
		button_tool.addActionListener(new ToolAction());
		button_figure.addActionListener(new ToolAction());
		// choice_figure.addItemListener("사각형");

		this.addMouseListener(new MouseAction());
		this.addMouseMotionListener(new MouseAction());
	}

	public void paint(Graphics g) {
		int x = di.getX();
		int y = di.getY();
		int x1 = di.getX1();
		int y1 = di.getY1();
		int dist = this.dist;
		Color color = di.getColor();
		boolean fill = di.isFill();

		// 그려질 객체들의 색상은 메뉴 OPTION - COLOR에 체크된 값에 따라서 달라진다.
		Color c = color;
		g.setColor(c);
		System.out.println("C = " + c);
		// 메뉴 OPTION - DRAW, PROPERTY 에 체크된 메뉴에 따라
		// 선, 빈 사각형, 채워진 사각형, 빈 원, 채워진 원 으로 그려진다.
		if (dist == 1 || dist == 0) {
			g.drawLine(x, y, x1, y1);
		} else if (dist == 2) {
			if (fill) {
				g.fillRect(x, y, x1 - x, y1 - y);
			} else {
				g.drawRect(x, y, x1 - x, y1 - y);
			}
		} else if (dist == 3) {
			if (fill) {
				g.fillOval(x, y, x1 - x, y1 - y);
			} else {
				g.drawOval(x, y, x1 - x, y1 - y);
			}
		} else if (dist == 6) {
			int[] x_points = { x, x + 60, x + 120 };
			int[] y_points = { y + 60, y, y + 60 };
			if (fill) {
				g.drawPolygon(x_points, y_points, x_points.length);
			} else {
				g.fillPolygon(x_points, y_points, x_points.length);
			}

		}
		// 배열에 그림이 입력된 수만큼 반복
		for (int i = 0; i < vc.size(); i++) {
			// 배열에서 1개의 그림정보를 imsi에 담는다.
			DrawInfo imsi = vc.get(i);
			// imsi의 색상정보를 가져와서 실제 그림을 그릴 그래픽 객체 g에 담는다.
			g.setColor(imsi.getColor());
			// imsi의 도형정보를 토대로 그림을 그린다.
			if (imsi.getType() == 1 || imsi.getType() == 0) {
				g.drawLine(imsi.getX(), imsi.getY(), imsi.getX1(), imsi.getY1());
			} else if (imsi.getType() == 2) {
				if (imsi.isFill()) {
					g.fillRect(imsi.getX(), imsi.getY(), imsi.getX1() - imsi.getX(), imsi.getY1() - imsi.getY());
				} else {
					g.drawRect(imsi.getX(), imsi.getY(), imsi.getX1() - imsi.getX(), imsi.getY1() - imsi.getY());
				}
			} else if (imsi.getType() == 3) {
				if (imsi.isFill()) {
					g.fillOval(imsi.getX(), imsi.getY(), imsi.getX1() - imsi.getX(), imsi.getY1() - imsi.getY());
				} else {
					g.drawOval(imsi.getX(), imsi.getY(), imsi.getX1() - imsi.getX(), imsi.getY1() - imsi.getY());
				}
			} else if (imsi.getType() == 6) {
				int[] x_points = { imsi.getX(), imsi.getX() + 60, imsi.getX() + 120 };
				int[] y_points = { imsi.getY() + 60, imsi.getY(), imsi.getY() + 60 };
				if (imsi.isFill()) {
					g.drawPolygon(x_points, y_points, x_points.length);
				} else {
					g.fillPolygon(x_points, y_points, x_points.length);
				}
			}
		}
	}

}
