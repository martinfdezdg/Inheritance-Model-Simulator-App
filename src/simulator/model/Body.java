package simulator.model;

import simulator.misc.Vector;

public class Body {
	protected String id;
	protected Vector v;
	protected Vector a;
	protected Vector p;
	protected double m;

	// CONSTRUCTOR
	public Body(String id, Vector v, Vector a, Vector p, double m) {
		this.id = id;
		this.v = v;
		this.a = a;
		this.p = p;
		this.m = m;
	}

	// GET
	public String getId() {
		return id;
	}

	public Vector getVelocity() {
		return v;
	}

	public Vector getAcceleration() {
		return a;
	}

	public Vector getPosition() {
		return p;
	}

	public double getMass() {
		return m;
	}

	// SET
	void setVelocity(Vector v) {
		this.v = v;
	}

	void setAcceleration(Vector a) {
		this.a = a;
	}

	void setPosition(Vector p) {
		this.p = p;
	}

	void move(double t) {
		p = p.plus(v.scale(t).plus(a.scale(0.5 * t * t)));
		v = v.plus(a.scale(t));
	}

	// TOSTRING
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("{  \"id\": \"");
		s.append(id);
		s.append("\", \"mass\": ");
		s.append(m);
		s.append(", \"pos\": ");
		s.append(p);
		s.append(", \"vel\": ");
		s.append(v);
		s.append(", \"acc\": ");
		s.append(a);
		s.append(" }");
		return s.toString();
	}

	// REDEFINITION
	public boolean equals(Body body) {
		if (this == body)
			return true;
		if (body == null)
			return false;
		if (this.getClass() != body.getClass())
			return false;
		Body aux = (Body) body;
		if (this.id == null)
			return aux.id == null;
		return this.id.equals(body.id);
	}
}
