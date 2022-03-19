package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import org.json.JSONException;
import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver {
	private JButton openButton;
	private JButton physicsButton;
	private JButton runButton;
	private JButton stopButton;
	private JButton exitButton;
	JSpinner stepsSpinner;
	JSpinner delaySpinner;
	JTextField deltaTimeField;

	private Controller _ctrl;
	private int physicChoosen = 0;

	private volatile Thread thread;

	// CONSTRUCTOR
	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		initGUI();
		_ctrl.addObserver(this);
	}

	// GUI
	private void initGUI() {
		openButton = new JButton();
		openButton.setBorderPainted(false);
		openButton.setIcon(new ImageIcon(new ImageIcon("resources/icons/open.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
		openButton.setToolTipText("Open file");
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				openAction();
			}
		});

		physicsButton = new JButton();
		physicsButton.setBorderPainted(false);
		physicsButton.setIcon(new ImageIcon(new ImageIcon("resources/icons/physics.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
		physicsButton.setToolTipText("Gravity laws selector");
		physicsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				physicsAction();
			}
		});

		runButton = new JButton();
		runButton.setBorderPainted(false);
		runButton.setIcon(new ImageIcon(new ImageIcon("resources/icons/run.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
		runButton.setToolTipText("Run");
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				runAction();
			}
		});

		stopButton = new JButton();
		stopButton.setBorderPainted(false);
		stopButton.setIcon(new ImageIcon(new ImageIcon("resources/icons/stop.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
		stopButton.setToolTipText("Stop");
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				stopAction();
			}
		});

		exitButton = new JButton();
		exitButton.setBorderPainted(false);
		exitButton.setIcon(new ImageIcon(new ImageIcon("resources/icons/exit.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
		exitButton.setToolTipText("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				exitAction();
			}
		});

		stepsSpinner = new JSpinner(new SpinnerNumberModel(10000, 0, 50000, 100));
		stepsSpinner.setMinimumSize(new Dimension(90, 30));
		stepsSpinner.setMaximumSize(new Dimension(90, 30));
		stepsSpinner.setPreferredSize(new Dimension(90, 30));

		delaySpinner = new JSpinner(new SpinnerNumberModel(1, 0, 1000, 1));
		delaySpinner.setMinimumSize(new Dimension(90, 30));
		delaySpinner.setMaximumSize(new Dimension(90, 30));
		delaySpinner.setPreferredSize(new Dimension(90, 30));

		deltaTimeField = new JTextField();
		deltaTimeField.setMinimumSize(new Dimension(90, 30));
		deltaTimeField.setMaximumSize(new Dimension(90, 30));
		deltaTimeField.setPreferredSize(new Dimension(90, 30));

		JToolBar controlBar = new JToolBar();
		//controlBar.setUI(null);
		controlBar.setBackground(Color.black);
		this.add(controlBar);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		controlBar.add(openButton);
		controlBar.add(physicsButton);
		controlBar.add(runButton);
		controlBar.add(stopButton);
		JLabel delay = new JLabel(" Delay:");
		delay.setBackground(Color.black);
		delay.setForeground(Color.white);
		delay.setOpaque(true);
		JLabel steps = new JLabel(" Steps:");
		steps.setBackground(Color.black);
		steps.setForeground(Color.white);
		steps.setOpaque(true);
		JLabel deltaTime = new JLabel(" Delta-Time:");
		deltaTime.setBackground(Color.black);
		deltaTime.setForeground(Color.white);
		deltaTime.setOpaque(true);
		controlBar.add(delay);
		controlBar.add(delaySpinner);
		controlBar.add(steps);
		controlBar.add(stepsSpinner);
		controlBar.add(deltaTime);
		controlBar.add(deltaTimeField);
		controlBar.add(Box.createHorizontalGlue());
		controlBar.add(exitButton);
	}

	// ACTIONS
	private void openAction() {
		JFileChooser fileChooser = new JFileChooser();
		int ret = fileChooser.showOpenDialog(this.getParent());
		if (ret == JFileChooser.APPROVE_OPTION) {
			try {
				_ctrl.reset();
				InputStream is = new FileInputStream(fileChooser.getSelectedFile());
				_ctrl.loadBodies(is);
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(this.getParent(), "The file can't be found.");
			} catch (JSONException e) {
				JOptionPane.showMessageDialog(this.getParent(), "The file can't be opened.");
			}
		}
	}

	private void physicsAction() {
		int n = _ctrl.getGravityLawsFactory().getInfo().size();
		Object[] options = new Object[n];
		for (int i = 0; i < n; ++i) {
			options[i] = _ctrl.getGravityLawsFactory().getInfo().get(i).get("desc");
		}
		String option = (String) JOptionPane.showInputDialog(null, "Select gravity laws to be used",
				"Gravity laws selector", JOptionPane.DEFAULT_OPTION, null, options, options[physicChoosen]);
		for (int i = 0; i < n; ++i) {
			if (options[i].equals(option)) {
				physicChoosen = i;
			}
		}
		_ctrl.setGravityLaws(_ctrl.getGravityLawsFactory().getInfo().get(physicChoosen));
	}

	private void runAction() {
		try {
			float deltaTime = Float.parseFloat(deltaTimeField.getText());
			if (deltaTime < 0)
				JOptionPane.showMessageDialog(this.getParent(), "Delta-Time must be a positive.");
			else {
				_ctrl.setDeltaTime(deltaTime);
				setButtonsState(false);
				thread = new Thread(new Runnable() {
					public void run() {
						run_sim((Integer) stepsSpinner.getValue(), (Integer) delaySpinner.getValue());
						setButtonsState(true);
						thread = null;
					}
				});
				thread.start();
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this.getParent(), "Delta-Time field must be a number.");
		}
	}

	private void stopAction() {
		if (thread != null)
			thread.interrupt();
	}

	private void exitAction() {
		int option = JOptionPane.showOptionDialog(this.getParent(), "Are sure you want to quit?", "Quit",
				JOptionPane.YES_NO_OPTION, JOptionPane.DEFAULT_OPTION, null, null, null);
		if (option == 0)
			System.exit(0);
	}

	private void run_sim(int n, int delay) {
		while (n > 0 & !thread.isInterrupted()) {
			try {
				_ctrl.run(1);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this.getParent(), e.getMessage());
				setButtonsState(true);
				return;
			}
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				thread.interrupt();
				return;
			}
			n--;
		}
	}

	// SET
	private void setButtonsState(boolean state) {
		openButton.setEnabled(state);
		physicsButton.setEnabled(state);
		runButton.setEnabled(state);
		exitButton.setEnabled(state);
	}

	// SimulatorObserver methods
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				deltaTimeField.setText("" + dt);
			}
		});
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				deltaTimeField.setText("" + dt);
			}
		});
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				deltaTimeField.setText("" + dt);
			}
		});
	}

	@Override
	public void onGravityLawsChanged(String gLawsDesc) {
	}
}