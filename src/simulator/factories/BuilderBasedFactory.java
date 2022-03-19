package simulator.factories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
	private ArrayList<Builder<T>> list;
	private ArrayList<JSONObject> infoList;

	// CONSTRUCTOR
	public BuilderBasedFactory(ArrayList<Builder<T>> builders) {
		this.list = builders;
		infoList = new ArrayList<JSONObject>();
		for (Builder<T> b : list) {
			infoList.add(b.getBuilderInfo());
		}
	}

	// INSTANCE_CREATION:BODY
	public T createInstance(JSONObject info) throws IllegalArgumentException {
		T object = null;
		for (int i = 0; i < list.size() && object == null; ++i) {
			object = list.get(i).createInstance(info);
		}
		if (object == null)
			throw new IllegalArgumentException(); // Exception(): type not found
		else
			return object;
	}

	// GETS
	public List<JSONObject> getInfo() {
		return Collections.unmodifiableList(infoList);
	}
}