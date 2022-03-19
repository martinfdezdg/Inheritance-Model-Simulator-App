package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import simulator.control.Controller;

public class MainWindow extends JFrame {
	Controller _ctrl;

	// CONSTRUCTOR
	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
		//this.setUndecorated(true);
		this.setSize(800, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	// GUI
	private void initGUI() {
		ControlPanel controlPanel = new ControlPanel(_ctrl);

		BodiesTable bodiesTable = new BodiesTable(_ctrl);
		bodiesTable.setPreferredSize(new Dimension(400, 150));

		Viewer viewer = new Viewer(_ctrl);
		viewer.setPreferredSize(new Dimension(400, 650));

		StatusBar statusBar = new StatusBar(_ctrl);

		JPanel bodiesPanel = new JPanel();
		bodiesPanel.setLayout(new BoxLayout(bodiesPanel, BoxLayout.Y_AXIS));
		bodiesPanel.setBackground(Color.black);
		bodiesPanel.add(bodiesTable);
		bodiesPanel.add(viewer);

		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		mainPanel.add(controlPanel, BorderLayout.PAGE_START);
		mainPanel.add(bodiesPanel, BorderLayout.CENTER);
		mainPanel.add(statusBar, BorderLayout.PAGE_END);
	}
}
