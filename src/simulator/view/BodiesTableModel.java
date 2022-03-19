package simulator.view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {
	private String[] columns = { "ID", "Mass", "Position", "Velocity", "Acceleration" };
	private List<Body> bodies;

	// CONSTRUCTOR
	BodiesTableModel(Controller ctrl) {
		bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}

	// GET
	public int getRowCount() {
		return bodies == null ? 0 : bodies.size();
	}

	public int getColumnCount() {
		return columns.length;
	}

	public String getColumnName(int column) {
		return columns[column];
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Object value = null;
		switch (columnIndex) {
		case 0:
			value = bodies.get(rowIndex).getId();
			break;
		case 1:
			value = bodies.get(rowIndex).getMass();
			break;
		case 2:
			value = bodies.get(rowIndex).getPosition();
			break;
		case 3:
			value = bodies.get(rowIndex).getVelocity();
			break;
		case 4:
			value = bodies.get(rowIndex).getAcceleration();
			break;
		}
		return value;
	}

	// SimulatorObserver methods
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				BodiesTableModel.this.bodies = bodies;
				fireTableStructureChanged();
			}
		});
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				BodiesTableModel.this.bodies = bodies;
				fireTableStructureChanged();
			}
		});
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				BodiesTableModel.this.bodies = bodies;
				fireTableStructureChanged();
			}
		});
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				BodiesTableModel.this.bodies = bodies;
				fireTableStructureChanged();
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
