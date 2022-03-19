package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.MassLossingBody;

public class MassLossingBodyBuilder extends Builder<Body> {
	public static final String TYPE_TAG = "mlb";
	public static final String DESC = "Mass losing body";

	// CONSTRUCTOR
	public MassLossingBodyBuilder() {
		super(TYPE_TAG, DESC);
	}

	// INSTANCE_CREATION:BODY
	public MassLossingBody createTheInstance(JSONObject jsonObject) throws IllegalArgumentException {
		Vector v = new Vector(jsonArrayToDoubleArray(jsonObject.getJSONArray("vel")));
		Vector a = new Vector(v.dim()); // acc is empty
		Vector p = new Vector(jsonArrayToDoubleArray(jsonObject.getJSONArray("pos")));
		Double m = jsonObject.getDouble("mass");
		Double factor = jsonObject.getDouble("factor");
		Double freq = jsonObject.getDouble("freq");
		if (m < 0)
			throw new IllegalArgumentException("Mass can't be nagtive");
		if (factor < 0)
			throw new IllegalArgumentException("Factor can't be nagtive");
		if (freq < 0)
			throw new IllegalArgumentException("Frequency can't be nagtive");
		return new MassLossingBody(jsonObject.getString("id"), v, a, p, m, factor, freq);
	}

	// TEMPLATE
	public JSONObject createData() {
		JSONObject data = super.createData();
		data.put("id", "identification");
		data.put("pos", "position");
		data.put("vel", "velocity");
		data.put("mass", "mass");
		data.put("factor", "factor");
		data.put("freq", "frequency");
		return data;
	}
}