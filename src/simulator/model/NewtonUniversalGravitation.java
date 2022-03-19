package simulator.model;

import java.util.List;

import simulator.factories.NewtonUniversalGravitationBuilder;
import simulator.misc.Vector;

public class NewtonUniversalGravitation implements GravityLaws {
	static private final double G = 6.67E-11;

	// CONSTRUCTOR
	public NewtonUniversalGravitation() {
	}

	// ACTIONS
	public void apply(List<Body> bodies) {
		for (Body i : bodies) {
			if (i.getMass() == 0.0) {
				Vector a = new Vector(i.getAcceleration().dim());
				i.setAcceleration(a);
				i.setVelocity(a);
			} else {
				Vector Fi = new Vector(i.getAcceleration().dim());
				for (Body j : bodies) {
					if (!i.equals(j)) {
						double fij = (G * i.getMass() * j.getMass())
								/ (j.getPosition().minus(i.getPosition()).magnitude()
										* j.getPosition().minus(i.getPosition()).magnitude());
						Vector Fij = (j.getPosition().minus(i.getPosition())).direction().scale(fij);
						Fi = Fi.plus(Fij);
					}
				}
				i.setAcceleration(Fi.scale(1 / i.getMass()));
			}
		}
	}

	// TOSTRING
	public String toString() {
		return NewtonUniversalGravitationBuilder.DESC;
	}
}