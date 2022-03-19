package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import simulator.control.Controller;
import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class Viewer extends JComponent implements SimulatorObserver {
	private int _centerX;
	private int _centerY;
	private double _scale;
	private List<Body> _bodies;
	private boolean _showHelp;

	// CONSTRUCTOR
	Viewer(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}

	// GUI
	private void initGUI() {
		// border with title
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Viewer",
				TitledBorder.LEFT, TitledBorder.TOP));
		_bodies = new ArrayList<>();
		_scale = 1.0;
		_showHelp = false;
		addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyChar()) {
				case '-':
					_scale = _scale * 1.1;
					break;
				case '+':
					_scale = Math.max(1000.0, _scale / 1.1);
					break;
				case '=':
					autoScale();
					break;
				case 'h':
					_showHelp = !_showHelp;
					break;
				default:
				}
				repaint();
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});

		addMouseListener(new MouseListener() {
			@Override
			public void mouseEntered(MouseEvent e) {
				requestFocus();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D gr = (Graphics2D) g;
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		// calculate the center
		_centerX = getWidth() / 2;
		_centerY = getHeight() / 2;
		
		// draw a cross at center
		gr.setColor(Color.white);
		ImageIcon icono = new javax.swing.ImageIcon("resources/icons/sun.gif");
		Image imagen = icono.getImage();
		gr.drawImage(imagen, (int) (_centerX - icono.getIconHeight() /4),
				(int) (_centerY - icono.getIconWidth() /4), (int) (icono.getIconHeight() /2),
				(int) (icono.getIconWidth() /2), null);
		
		// draw bodies
		ImageIcon icon;
		Image image;
		int i = 0;
		for (Body b : _bodies) {
			int bx = _centerX - 5 + (int) (b.getPosition().coordinate(0) / _scale);
			int by = _centerY - 5 - (int) (b.getPosition().coordinate(1) / _scale);
			switch (i) {
			case 0:
				icon = new javax.swing.ImageIcon("resources/icons/earth.gif");
				image = icon.getImage();
				gr.drawImage(image, bx - icon.getIconHeight() / 32, by - icon.getIconWidth() / 32,
						icon.getIconHeight() / 16, icon.getIconWidth() / 16, null);
				break;
			case 1:
				icon = new javax.swing.ImageIcon("resources/icons/Jupiter.gif");
				image = icon.getImage();
				gr.drawImage(image, bx - icon.getIconHeight() / 20, by - icon.getIconWidth() / 20,
						icon.getIconHeight() / 10, icon.getIconWidth() / 10, null);
				break;
			case 2:
				icon = new javax.swing.ImageIcon("resources/icons/moon.gif");
				image = icon.getImage();
				gr.drawImage(image, bx - icon.getIconHeight() / 30, by - icon.getIconWidth() / 30,
						icon.getIconHeight() / 15, icon.getIconWidth() / 15, null);
				break;
			case 3:
				icon = new javax.swing.ImageIcon("resources/icons/venus.gif");
				image = icon.getImage();
				gr.drawImage(image, bx - icon.getIconHeight() / 30, by - icon.getIconWidth() / 30,
						icon.getIconHeight() / 15, icon.getIconWidth() / 15, null);
				break;
				
			default:
				gr.setColor(Color.blue);
				gr.fillOval(_centerX - 5 + (int) (b.getPosition().coordinate(0) / _scale),
						_centerY - 5 - (int) (b.getPosition().coordinate(1) / _scale), 10, 10);
			}

			++i;

		}
		

		// draw help if _showHelp is true
		if (_showHelp) {
			gr.setColor(Color.white);
			gr.drawString("h: toggle help, +:zoom-in, -:zoom-out, =:fit", 10, 25);
			gr.drawString("Scaling ratio: " + _scale, 10, 40);
		}
	}

	// ACTIONS
	private void autoScale() {
		double max = 1.0;
		for (Body b : _bodies) {
			Vector p = b.getPosition();
			for (int i = 0; i < p.dim(); i++)
				max = Math.max(max, Math.abs(b.getPosition().coordinate(i)));
		}
		double size = Math.max(1.0, Math.min((double) getWidth(), (double) getHeight()));
		_scale = max > size ? 4.0 * max / size : 1.0;
	}

	// SimulatorObserver methods
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_bodies = bodies;
				// autoScale();
				repaint();
			}
		});
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_bodies = bodies;
				autoScale();
				repaint();
			}
		});
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_bodies = bodies;
				autoScale();
				repaint();
			}
		});
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_bodies = bodies;
				// autoScale();
				repaint();
			}
		});
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
	}

	@Override
	public void onGravityLawsChanged(String gLawsDesc) {
	}
}