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

		// �޴���
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// �޴� ������
		// ����
		JMenu Menu_file = new JMenu("����");
		menuBar.add(Menu_file);

		// �޴� ������
		// ���θ����
		JMenuItem Item_new = new JMenuItem("���θ����");
		Menu_file.add(Item_new);
		// �����ϱ�
		JMenuItem Item_save = new JMenuItem("�����ϱ�");
		Menu_file.add(Item_save);
		// �ҷ�����
		JMenuItem Item_load = new JMenuItem("�ҷ�����");
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

		JButton button_color = new JButton("����");
		button_color.setBackground(Color.WHITE);
		toolBar.add(button_color);

		JButton button_size = new JButton("ũ��");
		button_size.setBackground(Color.WHITE);
		toolBar.add(button_size);

		JButton button_tool = new JButton("����");
		button_tool.setBackground(Color.WHITE);
		toolBar.add(button_tool);

		JButton button_figure = new JButton("����");
		button_figure.setBackground(Color.WHITE);
		toolBar.add(button_figure);

		Item_new.addActionListener(new MenuAction());
		Item_save.addActionListener(new MenuAction());
		Item_load.addActionListener(new MenuAction());
		button_color.addActionListener(new ToolAction());
		button_size.addActionListener(new ToolAction());
		button_tool.addActionListener(new ToolAction());
		button_figure.addActionListener(new ToolAction());
		// choice_figure.addItemListener("�簢��");

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

		// �׷��� ��ü���� ������ �޴� OPTION - COLOR�� üũ�� ���� ���� �޶�����.
		Color c = color;
		g.setColor(c);
		System.out.println("C = " + c);
		// �޴� OPTION - DRAW, PROPERTY �� üũ�� �޴��� ����
		// ��, �� �簢��, ä���� �簢��, �� ��, ä���� �� ���� �׷�����.
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
		// �迭�� �׸��� �Էµ� ����ŭ �ݺ�
		for (int i = 0; i < vc.size(); i++) {
			// �迭���� 1���� �׸������� imsi�� ��´�.
			DrawInfo imsi = vc.get(i);
			// imsi�� ���������� �����ͼ� ���� �׸��� �׸� �׷��� ��ü g�� ��´�.
			g.setColor(imsi.getColor());
			// imsi�� ���������� ���� �׸��� �׸���.
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
