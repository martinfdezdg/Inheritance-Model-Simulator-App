package simulator.factories;

import org.json.JSONObject;
import simulator.model.GravityLaws;
import simulator.model.FallingToCenterGravity;

public class FallingToCenterGravityBuilder extends Builder<GravityLaws> {
	public static final String TYPE_TAG = "ftcg";
	public static final String DESC = "Falling to center gravity";

	// CONSTRUCTOR
	public FallingToCenterGravityBuilder() {
		super(TYPE_TAG, DESC);
	}

	// INSTANCE_CREATION:LAW
	public GravityLaws createTheInstance(JSONObject jsonObject) {
		return new FallingToCenterGravity();
	}
}
