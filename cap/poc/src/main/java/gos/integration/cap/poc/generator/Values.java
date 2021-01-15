package gos.integration.cap.poc.generator;

import java.util.LinkedList;

public class Values {
	private LinkedList<Tool> tools;
	private double mu;
	private int step;
	private int n;
	
	public Values(double mu, int step, int n) {
		this.tools = new LinkedList<Tool>();
		this.mu = mu;
		this.step = step;
		this.n = n;
	}
	
	public void add(Tool tool) {
		this.tools.add(tool);
	}

	public LinkedList<Tool> getTools() {
		return tools;
	}
	
	public double getMu() {
		return mu;
	}

	public int getStep() {
		return step;
	}

	public int getN() {
		return n;
	}
}
