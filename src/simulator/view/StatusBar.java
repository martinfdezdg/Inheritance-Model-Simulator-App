package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel implements SimulatorObserver {
	private JLabel _currTime; // for current time
	private JLabel _currLaws; // for gravity laws
	private JLabel _numOfBodies; // for number of bodies

	// CONSTRUCTOR
	StatusBar(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}

	// GUI
	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));

		_currTime = new JLabel();
		_currTime.setBackground(Color.black);
		_currTime.setForeground(Color.white);
		_currTime.setOpaque(true);
		_currTime.setMinimumSize(new Dimension(100, 20));
		_currTime.setMaximumSize(new Dimension(100, 20));
		_currTime.setPreferredSize(new Dimension(100, 20));

		_numOfBodies = new JLabel();
		_numOfBodies.setBackground(Color.black);
		_numOfBodies.setForeground(Color.white);
		_numOfBodies.setOpaque(true);
		_numOfBodies.setMinimumSize(new Dimension(100, 20));
		_numOfBodies.setMaximumSize(new Dimension(100, 20));
		_numOfBodies.setPreferredSize(new Dimension(100, 20));

		_currLaws = new JLabel();
		_currLaws.setBackground(Color.black);
		_currLaws.setForeground(Color.white);
		_currLaws.setOpaque(true);
		_currLaws.setMinimumSize(new Dimension(250, 20));
		_currLaws.setMaximumSize(new Dimension(250, 20));
		_currLaws.setPreferredSize(new Dimension(250, 20));

		JToolBar statusBar = new JToolBar();
		this.add(statusBar);
		this.setBackground(Color.black);
		statusBar.setUI(null);
		JLabel time = new JLabel("Time: ");
		time.setForeground(Color.white);
		JLabel bodies = new JLabel("Bodies: ");
		bodies.setForeground(Color.white);
		JLabel laws = new JLabel("Laws: ");
		laws.setForeground(Color.white);
		statusBar.add(new JSeparator(SwingConstants.VERTICAL));
		statusBar.add(time);
		statusBar.add(_currTime);
		statusBar.add(new JSeparator(SwingConstants.VERTICAL));
		statusBar.add(bodies);
		statusBar.add(_numOfBodies);
		statusBar.add(new JSeparator(SwingConstants.VERTICAL));
		statusBar.add(laws);
		statusBar.add(new JLabel("Laws: "));
		statusBar.add(_currLaws);
	}

	// SimulatorObserver methods
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_currTime.setText("" + time);
				_currLaws.setText(gLawsDesc);
				_numOfBodies.setText("" + bodies.size());
			}
		});
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_currTime.setText("" + time);
				_currLaws.setText(gLawsDesc);
				_numOfBodies.setText("" + bodies.size());
			}
		});
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_numOfBodies.setText("" + bodies.size());
			}
		});
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_currTime.setText("" + time);
				_numOfBodies.setText("" + bodies.size());
			}
		});
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
	}

	@Override
	public void onGravityLawsChanged(String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_currLaws.setText(gLawsDesc);
			}
		});
	}
}
