package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.GravityLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

public class Controller {
	private PhysicsSimulator simulator;
	private Factory<Body> bodyFactory;
	private Factory<GravityLaws> gravityLawsFactory;

	// CONSTRUCTOR
	public Controller(PhysicsSimulator simulator, Factory<Body> bodyFactory, Factory<GravityLaws> gravityLawsFactory) {
		this.simulator = simulator;
		this.bodyFactory = bodyFactory;
		this.gravityLawsFactory = gravityLawsFactory;
	}

	// ACTIONS
	// load bodies from JSON file
	public void loadBodies(InputStream in) {
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		JSONArray array = jsonInput.getJSONArray("bodies");
		for (int i = 0; i < array.length(); ++i) {
			simulator.addBody(bodyFactory.createInstance(array.getJSONObject(i)));
		}
	}

	// prints line of step simulation
	public void run(int n, OutputStream out) {
		if (out != null) {
			PrintStream p = new PrintStream(out);
			p.print("{\n\"states\": [\n");
			p.print(simulator.toString() + ",\n");
			for (int i = 0; i < n - 1; ++i) {
				simulator.advance();
				p.print(simulator.toString() + "," + "\n");
			}
			simulator.advance();
			p.print(simulator.toString() + "\n]\n}\n");
		}
	}

	public void run(int n) {
		for (int i = 0; i < n; ++i) {
			simulator.advance();
		}
	}

	public void addObserver(SimulatorObserver o) {
		simulator.addObserver(o);
	}

	public void reset() {
		simulator.reset();
	}

	// GET
	public Factory<GravityLaws> getGravityLawsFactory() {
		return gravityLawsFactory;
	}

	// SET
	public void setDeltaTime(double dt) {
		simulator.setDeltaTime(dt);
	}

	public void setGravityLaws(JSONObject info) {
		GravityLaws law = gravityLawsFactory.createInstance(info);
		simulator.setGravityLaws(law);
	}
}