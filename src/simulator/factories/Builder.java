package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Builder<T> {
	protected String typeTag;
	protected String desc;

	// CONSTRUCTOR
	public Builder(String typeTag, String desc) {
		this.typeTag = typeTag;
		this.desc = desc;
	}

	// INSTANCE_CREATION
	public T createInstance(JSONObject info) throws IllegalArgumentException {
		if (typeTag.equals(info.get("type"))) {
			return createTheInstance(info.getJSONObject("data"));
		} else
			return null;
	}

	// INSTANCE_CREATION:BODY/LAW
	protected abstract T createTheInstance(JSONObject jsonObject);

	// GETS
	public JSONObject getBuilderInfo() {
		JSONObject builderInfo = new JSONObject();
		builderInfo.put("type", typeTag);
		builderInfo.put("data", createData());
		builderInfo.put("desc", desc);
		return builderInfo;
	}

	// TRANSFORM
	public double[] jsonArrayToDoubleArray(JSONArray jsonArray) {
		double[] array = new double[jsonArray.length()];
		for (int i = 0; i < jsonArray.length(); i++) {
			array[i] = jsonArray.getDouble(i);
		}
		return array;
	}

	// TEMPLATE
	public JSONObject createData() {
		return new JSONObject();
	}
}