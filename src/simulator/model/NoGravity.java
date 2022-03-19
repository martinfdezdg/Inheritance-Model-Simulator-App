package simulator.model;

import java.util.List;

import simulator.factories.NoGravityBuilder;

public class NoGravity implements GravityLaws {

	// CONSTRUCTOR
	public NoGravity() {
	}

	// ACTIONS
	public void apply(List<Body> bodies) {
	}

	// TOSTRING
	public String toString() {
		return NoGravityBuilder.DESC;
	}
}