package simulator.model;

import simulator.misc.Vector;

public class MassLossingBody extends Body {
	protected double lossFactor;
	protected double lossFrecuency;
	protected double c;

	// CONSTRUCTOR
	public MassLossingBody(String id, Vector v, Vector a, Vector p, double m, double lossFactor, double lossFrecuency) {
		super(id, v, a, p, m);
		this.lossFactor = lossFactor;
		this.lossFrecuency = lossFrecuency;
		c = 0.0;
	}

	// ACTIONS
	public void move(double t) {
		super.move(t);
		c += t;
		if (c >= lossFrecuency) {
			m = m * (1 - lossFactor);
			c = 0.0;
		}
	}
}
