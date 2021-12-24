

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
	// 메뉴바 생성
	private MenuBar mb = new MenuBar();

	// 메뉴 FILE과 하위 메뉴 생성
	private Menu file = new Menu("FILE");
	private MenuItem fnew = new MenuItem("NEW");
	private MenuItem fopen = new MenuItem("OPEN");
	private MenuItem fsave = new MenuItem("SAVE");
	private MenuItem fexit = new MenuItem("EXIT");

	// 메뉴 OPTION과 하위 메뉴 생성
	private Menu option = new Menu("OPTION");
	private Menu odraw = new Menu("DRAW");

	// check를 할 수 있는 메뉴아이템
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

	// x,y : 그래픽 객체의 시작 좌표값
	// x1,y1 : 그래픽 객체의 종료 좌표값
	// dist : 그려질 도형의 형태
	private int x, y, x1, y1, dist;

	// 색상
	private Color color;
	// 도형 채움 여부
	private boolean fill;
	// 그려질 객체들은 DrawInfo 형태로 ArrayList에 저장될 것임.
	private ArrayList<DrawInfo> vc = new ArrayList<>();

	public Design() {
		super("그림판");
		init();
		start();
		setSize(500, 500);
		setVisible(true);
	}

	void init() {
		// 프레임에 메뉴바를 셋팅하고
		this.setMenuBar(mb);
		// 메뉴들을 추가
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

	// 리스너들을 작동
	void start() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		// 마우스와 관련된 리스너
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		// 메뉴와 관련된 리스너
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
	// 화면에 실제 그림이 그려지는 paint 메서드
	public void paint(Graphics g) {
		// 그려질 객체들의 색상은 메뉴 OPTION - COLOR에 체크된 값에 따라서 달라진다.
		Color c = new Color(ocred.getState() ? 255 : 0, ocgreen.getState() ? 255 : 0, ocblue.getState() ? 255 : 0);
		g.setColor(c);
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
			}
		}
	}

	@Override
	// 메뉴에서 버튼 클릭시 발생되는 이벤트 처리
	public void actionPerformed(ActionEvent e) {
		// FILE - NEW
		if (e.getSource() == fnew) {
			vc.clear(); // 그래픽배열을 비워서 초기화
			x = y = x1 = y1 = dist = 0; // 좌표와 도형 정보를 0으로 초기화
			this.repaint();
			// FILE - OPEN
		} else if (e.getSource() == fopen) {
			// 파일 다이얼로그를 열기(LOAD)모드로 메모리에 띄운다.
			FileDialog fdlg = new FileDialog(this, "열기", FileDialog.LOAD);
			fdlg.setVisible(true); // 다이얼로그를 화면에 보여주기
			// 다이얼로그에서 선택된 경로와 파일명을 변수에 담는다.
			String dir = fdlg.getDirectory();
			String file = fdlg.getFile();
			if (dir == null || file == null) { // 파일 선택 없이 다이얼로그를 종료할시 작업 수행 없이 바로 리턴
				return;
			}
			// ObjectInputStream oos 에 파일경로와 이름을 설정한다.
			try {
				ObjectInputStream oos = new ObjectInputStream(
						new BufferedInputStream(new FileInputStream(new File(dir, file))));
				// ObjectInputStream이 파일을 읽어서 그래픽배열에 담는다.
				vc = (ArrayList) oos.readObject();
				oos.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			// FILE - SAVE
		} else if (e.getSource() == fsave) {
			// 파일 다이얼로그를 저장(SAVE)모드로 메모리에 띄운다.
			FileDialog fdl = new FileDialog(this, "저장", FileDialog.SAVE);
			fdl.setVisible(true); // 다이얼로그를 화면에 보여주기
			// 다이얼로그에서 선택된 경로와 파일명을 변수에 담는다.
			String dir = fdl.getDirectory();
			String file = fdl.getFile();
			if (dir == null || file == null) { // 파일 선택 없이 다이얼로그를 종료할시 작업 수행 없이 바로 리턴
				return;
			}
			// ObjectOutputStream oos에 파일경로와 이름을 설정한다.
			try {
				ObjectOutputStream oos = new ObjectOutputStream(
						new BufferedOutputStream(new FileOutputStream(new File(dir, file))));
				// 그래픽배열의 내용을 ObjectOutputStream이 지정된 경로와 이름으로 저장한다.
				oos.writeObject(vc);
				oos.close();
			} catch (Exception ee) {
				ee.printStackTrace();
			}
			// FILE - EXIT
		} else if (e.getSource() == fexit) {
			System.exit(0); // 프로그램 종료
		}
	}

	@Override
	// 메뉴 중 DRAW와 PROPERTY 에서 체크상태가 바뀌는 이벤트 발생시
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == odpen) {
			dist = 0; // 도형 모양 설정
			// 클릭된 것 외에는 체크가 해제되도록 설정
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
	// 마우스 드래그 이벤트 발생시
	public void mouseDragged(MouseEvent e) {
		// 마우스 현재 위치를 x1,y1에 저장
		x1 = e.getX();
		y1 = e.getY();
		// 도형 형태가 PEN(자유곡선)이면
		if (dist == 0) {
			Color c = new Color(ocred.getState() ? 255 : 0, ocgreen.getState() ? 255 : 0, ocblue.getState() ? 255 : 0);
			DrawInfo di = new DrawInfo(x, y, x1, y1, dist, c, opfill.getState());
			// 마우스 버튼이 눌린 이전 위치와 드래그된 현재 위치로 이루어진.
			// 거의 '점' 수준의 아주 작은 Line을 그래픽 배열에 담는다.
			vc.add(di);
			// 현재의 마우스 위치를 시작 위치로 재설정한다.
			x = x1;
			y = y1;
		} // 결과적으로 작은 라인들이 모여서 곡선으로 보이게 한다.
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
	// 마우스 버튼이 눌리는 순간의 이벤트 (드래그의 시작 위치를 저장)
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	// 마우스 버튼을 떼는 순간의 이벤트
	public void mouseReleased(MouseEvent e) {
		x1 = e.getX();
		y1 = e.getY();
		Color c = new Color(ocred.getState() ? 255 : 0, ocgreen.getState() ? 255 : 0, ocblue.getState() ? 255 : 0);
		DrawInfo di = new DrawInfo(x, y, x1, y1, dist, c, opfill.getState());
		// 도형의 시작, 종료위치와 색상, 채움여부를 그래픽배열에 저장한다.
		vc.add(di);
		this.repaint();
	}
}