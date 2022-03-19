package simulator.factories;

import org.json.JSONObject;
import simulator.model.GravityLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<GravityLaws> {
	public static final String TYPE_TAG = "nlug";
	public static final String DESC = "Newton’s law of universal gravitation";

	// CONSTRUCTOR
	public NewtonUniversalGravitationBuilder() {
		super(TYPE_TAG, DESC);
	}

	// INSTANCE_CREATION:LAW
	public GravityLaws createTheInstance(JSONObject jsonObject) {
		return new NewtonUniversalGravitation();
	}
}