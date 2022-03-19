package simulator.factories;

import org.json.JSONObject;
import simulator.model.GravityLaws;
import simulator.model.NoGravity;

public class NoGravityBuilder extends Builder<GravityLaws> {
	public static final String TYPE_TAG = "ng";
	public static final String DESC = "No gravity";

	// CONSTRUCTOR
	public NoGravityBuilder() {
		super(TYPE_TAG, DESC);
	}

	// INSTANCE_CREATION:LAW
	public GravityLaws createTheInstance(JSONObject jsonObject) {
		return new NoGravity();
	}
}