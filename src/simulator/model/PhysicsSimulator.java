package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PhysicsSimulator {
	double time;
	double dt;
	GravityLaws law;
	ArrayList<Body> bodies;
	ArrayList<SimulatorObserver> observers;

	// CONSTRUCTOR
	public PhysicsSimulator(double t, GravityLaws law) {
		time = 0.0;
		dt = t;
		if (dt < 0)
			throw new IllegalArgumentException("Time can't be negative.");
		if (law == null)
			throw new IllegalArgumentException();
		this.law = law;
		bodies = new ArrayList<Body>();
		observers = new ArrayList<SimulatorObserver>();
	}

	// ACTIONS
	public void advance() {
		law.apply(bodies);
		for (Body i : bodies) {
			i.move(dt);
		}
		time += dt;
		for (SimulatorObserver o : observers) {
			o.onAdvance(Collections.unmodifiableList(bodies), time);
		}
	}

	public void addBody(Body b) {
		for (Body i : bodies) {
			if (i.equals(b))
				throw new IllegalArgumentException();
		}
		bodies.add(b);
		for (SimulatorObserver o : observers) {
			o.onBodyAdded(Collections.unmodifiableList(bodies), b);
		}
	}

	public void addObserver(SimulatorObserver o) {
		if (!observers.contains(o))
			observers.add(o);
		o.onRegister(Collections.unmodifiableList(bodies), time, dt, law.toString());
	}

	public void reset() {
		bodies.clear();
		time = 0.0;
		for (SimulatorObserver o : observers) {
			o.onReset(Collections.unmodifiableList(bodies), time, dt, law.toString());
		}
	}

	// SET
	public void setDeltaTime(double dt) throws IllegalArgumentException {
		if (dt < 0)
			throw new IllegalArgumentException();
		this.dt = dt;
		for (SimulatorObserver o : observers) {
			o.onDeltaTimeChanged(dt);
		}
	}

	public void setGravityLaws(GravityLaws law) throws IllegalArgumentException {
		if (law == null)
			throw new IllegalArgumentException();
		this.law = law;
		for (SimulatorObserver o : observers) {
			o.onGravityLawsChanged(law.toString());
		}
	}

	// TOSTRING
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("{ \"time\": ");
		s.append(time);
		s.append(", \"bodies\": [ ");
		for (int i = 0; i < bodies.size() - 1; ++i) {
			s.append(bodies.get(i).toString());
			s.append(", ");
		}
		s.append(bodies.get(bodies.size() - 1).toString());
		s.append(" ] }");
		return s.toString();
	}
}