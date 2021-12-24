

import java.awt.CheckboxMenuItem;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Dao.DrawInfo;

public class Design extends Frame implements MouseListener, MouseMotionListener, ItemListener, ActionListener {
	// �޴��� ����
	private MenuBar mb = new MenuBar();

	// �޴� FILE�� ���� �޴� ����
	private Menu file = new Menu("FILE");
	private MenuItem fnew = new MenuItem("NEW");
	private MenuItem fopen = new MenuItem("OPEN");
	private MenuItem fsave = new MenuItem("SAVE");
	private MenuItem fexit = new MenuItem("EXIT");

	// �޴� OPTION�� ���� �޴� ����
	private Menu option = new Menu("OPTION");
	private Menu odraw = new Menu("DRAW");

	// check�� �� �� �ִ� �޴�������
	private CheckboxMenuItem odpen = new CheckboxMenuItem("PEN", true);
	private CheckboxMenuItem odline = new CheckboxMenuItem("LINE");
	private CheckboxMenuItem odrec = new CheckboxMenuItem("RECT");
	private CheckboxMenuItem odcircle = new CheckboxMenuItem("CIRCLE");

	private Menu ocolor = new Menu("COLOR");
	private CheckboxMenuItem ocred = new CheckboxMenuItem("RED", true);
	private CheckboxMenuItem ocblue = new CheckboxMenuItem("BLUE");
	private CheckboxMenuItem ocgreen = new CheckboxMenuItem("GREEN");

	private Menu oprop = new Menu("PROPERTY");
	private CheckboxMenuItem opdraw = new CheckboxMenuItem("DRAW", true);
	private CheckboxMenuItem opfill = new CheckboxMenuItem("FILL");

	// x,y : �׷��� ��ü�� ���� ��ǥ��
	// x1,y1 : �׷��� ��ü�� ���� ��ǥ��
	// dist : �׷��� ������ ����
	private int x, y, x1, y1, dist;

	// ����
	private Color color;
	// ���� ä�� ����
	private boolean fill;
	// �׷��� ��ü���� DrawInfo ���·� ArrayList�� ����� ����.
	private ArrayList<DrawInfo> vc = new ArrayList<>();

	public Design() {
		super("�׸���");
		init();
		start();
		setSize(500, 500);
		setVisible(true);
	}

	void init() {
		// �����ӿ� �޴��ٸ� �����ϰ�
		this.setMenuBar(mb);
		// �޴����� �߰�
		mb.add(file);
		file.add(fnew);
		file.addSeparator();
		file.add(fopen);
		file.add(fsave);
		file.addSeparator();
		file.add(fexit);
		mb.add(option);
		option.add(odraw);
		odraw.add(odpen);
		odraw.add(odline);
		odraw.add(odrec);
		odraw.add(odcircle);
		option.addSeparator();
		option.add(ocolor);
		ocolor.add(ocred);
		ocolor.add(ocblue);
		ocolor.add(ocgreen);
		option.addSeparator();
		option.add(oprop);
		oprop.add(opdraw);
		oprop.add(opfill);
	}

	// �����ʵ��� �۵�
	void start() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		// ���콺�� ���õ� ������
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		// �޴��� ���õ� ������
		odpen.addItemListener(this);
		odline.addItemListener(this);
		odrec.addItemListener(this);
		odcircle.addItemListener(this);
		fnew.addActionListener(this);
		fopen.addActionListener(this);
		fsave.addActionListener(this);
		fexit.addActionListener(this);
		opdraw.addItemListener(this);
		opfill.addItemListener(this);
	}

	@Override
	// ȭ�鿡 ���� �׸��� �׷����� paint �޼���
	public void paint(Graphics g) {
		// �׷��� ��ü���� ������ �޴� OPTION - COLOR�� üũ�� ���� ���� �޶�����.
		Color c = new Color(ocred.getState() ? 255 : 0, ocgreen.getState() ? 255 : 0, ocblue.getState() ? 255 : 0);
		g.setColor(c);
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
			}
		}
	}

	@Override
	// �޴����� ��ư Ŭ���� �߻��Ǵ� �̺�Ʈ ó��
	public void actionPerformed(ActionEvent e) {
		// FILE - NEW
		if (e.getSource() == fnew) {
			vc.clear(); // �׷��ȹ迭�� ����� �ʱ�ȭ
			x = y = x1 = y1 = dist = 0; // ��ǥ�� ���� ������ 0���� �ʱ�ȭ
			this.repaint();
			// FILE - OPEN
		} else if (e.getSource() == fopen) {
			// ���� ���̾�α׸� ����(LOAD)���� �޸𸮿� ����.
			FileDialog fdlg = new FileDialog(this, "����", FileDialog.LOAD);
			fdlg.setVisible(true); // ���̾�α׸� ȭ�鿡 �����ֱ�
			// ���̾�α׿��� ���õ� ��ο� ���ϸ��� ������ ��´�.
			String dir = fdlg.getDirectory();
			String file = fdlg.getFile();
			if (dir == null || file == null) { // ���� ���� ���� ���̾�α׸� �����ҽ� �۾� ���� ���� �ٷ� ����
				return;
			}
			// ObjectInputStream oos �� ���ϰ�ο� �̸��� �����Ѵ�.
			try {
				ObjectInputStream oos = new ObjectInputStream(
						new BufferedInputStream(new FileInputStream(new File(dir, file))));
				// ObjectInputStream�� ������ �о �׷��ȹ迭�� ��´�.
				vc = (ArrayList) oos.readObject();
				oos.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			// FILE - SAVE
		} else if (e.getSource() == fsave) {
			// ���� ���̾�α׸� ����(SAVE)���� �޸𸮿� ����.
			FileDialog fdl = new FileDialog(this, "����", FileDialog.SAVE);
			fdl.setVisible(true); // ���̾�α׸� ȭ�鿡 �����ֱ�
			// ���̾�α׿��� ���õ� ��ο� ���ϸ��� ������ ��´�.
			String dir = fdl.getDirectory();
			String file = fdl.getFile();
			if (dir == null || file == null) { // ���� ���� ���� ���̾�α׸� �����ҽ� �۾� ���� ���� �ٷ� ����
				return;
			}
			// ObjectOutputStream oos�� ���ϰ�ο� �̸��� �����Ѵ�.
			try {
				ObjectOutputStream oos = new ObjectOutputStream(
						new BufferedOutputStream(new FileOutputStream(new File(dir, file))));
				// �׷��ȹ迭�� ������ ObjectOutputStream�� ������ ��ο� �̸����� �����Ѵ�.
				oos.writeObject(vc);
				oos.close();
			} catch (Exception ee) {
				ee.printStackTrace();
			}
			// FILE - EXIT
		} else if (e.getSource() == fexit) {
			System.exit(0); // ���α׷� ����
		}
	}

	@Override
	// �޴� �� DRAW�� PROPERTY ���� üũ���°� �ٲ�� �̺�Ʈ �߻���
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == odpen) {
			dist = 0; // ���� ��� ����
			// Ŭ���� �� �ܿ��� üũ�� �����ǵ��� ����
			odpen.setState(true);
			odline.setState(false);
			odrec.setState(false);
			odcircle.setState(false);
		} else if (e.getSource() == odline) {
			dist = 1;
			odpen.setState(false);
			odline.setState(true);
			odrec.setState(false);
			odcircle.setState(false);
		} else if (e.getSource() == odrec) {
			dist = 2;
			odpen.setState(false);
			odline.setState(false);
			odrec.setState(true);
			odcircle.setState(false);
		} else if (e.getSource() == odcircle) {
			dist = 3;
			odpen.setState(false);
			odline.setState(false);
			odrec.setState(false);
			odcircle.setState(true);
		} else if (e.getSource() == opdraw) {
			opdraw.setState(true);
			opfill.setState(false);
		} else if (e.getSource() == opfill) {
			opdraw.setState(false);
			opfill.setState(true);
		}
	}

	@Override
	// ���콺 �巡�� �̺�Ʈ �߻���
	public void mouseDragged(MouseEvent e) {
		// ���콺 ���� ��ġ�� x1,y1�� ����
		x1 = e.getX();
		y1 = e.getY();
		// ���� ���°� PEN(�����)�̸�
		if (dist == 0) {
			Color c = new Color(ocred.getState() ? 255 : 0, ocgreen.getState() ? 255 : 0, ocblue.getState() ? 255 : 0);
			DrawInfo di = new DrawInfo(x, y, x1, y1, dist, c, opfill.getState());
			// ���콺 ��ư�� ���� ���� ��ġ�� �巡�׵� ���� ��ġ�� �̷����.
			// ���� '��' ������ ���� ���� Line�� �׷��� �迭�� ��´�.
			vc.add(di);
			// ������ ���콺 ��ġ�� ���� ��ġ�� �缳���Ѵ�.
			x = x1;
			y = y1;
		} // ��������� ���� ���ε��� �𿩼� ����� ���̰� �Ѵ�.
		this.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	// ���콺 ��ư�� ������ ������ �̺�Ʈ (�巡���� ���� ��ġ�� ����)
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	// ���콺 ��ư�� ���� ������ �̺�Ʈ
	public void mouseReleased(MouseEvent e) {
		x1 = e.getX();
		y1 = e.getY();
		Color c = new Color(ocred.getState() ? 255 : 0, ocgreen.getState() ? 255 : 0, ocblue.getState() ? 255 : 0);
		DrawInfo di = new DrawInfo(x, y, x1, y1, dist, c, opfill.getState());
		// ������ ����, ������ġ�� ����, ä�򿩺θ� �׷��ȹ迭�� �����Ѵ�.
		vc.add(di);
		this.repaint();
	}
}