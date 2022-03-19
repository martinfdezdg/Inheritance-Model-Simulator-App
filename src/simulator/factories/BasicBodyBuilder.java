package simulator.factories;

import org.json.JSONObject;
import simulator.misc.Vector;
import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body> {
	public static final String TYPE_TAG = "basic";
	public static final String DESC = "Basic body";

	// CONSTRUCTOR
	public BasicBodyBuilder() {
		super(TYPE_TAG, DESC);
	}

	// INSTANCE_CREATION:BODY
	public Body createTheInstance(JSONObject jsonObject) throws IllegalArgumentException {
		Vector v = new Vector(jsonArrayToDoubleArray(jsonObject.getJSONArray("vel")));
		Vector a = new Vector(v.dim());
		Vector p = new Vector(jsonArrayToDoubleArray(jsonObject.getJSONArray("pos")));
		Double m = jsonObject.getDouble("mass");
		if (m < 0)
			throw new IllegalArgumentException("Mass can't be negative");

		return new Body(jsonObject.getString("id"), v, a, p, m);
	}

	// TEMPLATE
	public JSONObject createData() {
		JSONObject data = super.createData();
		data.put("id", "identification");
		data.put("pos", "position");
		data.put("vel", "velocity");
		data.put("mass", "mass");
		return data;
	}
}