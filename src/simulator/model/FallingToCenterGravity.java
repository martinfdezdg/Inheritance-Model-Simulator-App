package simulator.model;

import java.util.List;

import simulator.factories.FallingToCenterGravityBuilder;

public class FallingToCenterGravity implements GravityLaws {
	public static final double g = 9.81;

	// CONSTRUCTOR
	public FallingToCenterGravity() {
	}

	// ACTIONS
	public void apply(List<Body> bodies) {
		for (Body i : bodies) {
			i.setAcceleration(i.getPosition().direction().scale(-g));
		}
	}

	// TOSTRING
	public String toString() {
		return FallingToCenterGravityBuilder.DESC;
	}
}
