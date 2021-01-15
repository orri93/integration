package gos.integration.cap.poc.generator.configuration;

import java.util.LinkedList;

public class Generator {
	private LinkedList<Tool> tools;
	private Depth depth;
	private Value value;
	
	public LinkedList<Tool> getTools() {
		return tools;
	}

	public void setTools(LinkedList<Tool> tools) {
		this.tools = tools;
	}

	public Depth getDepth() {
		return depth;
	}

	public void setDepth(Depth depth) {
		this.depth = depth;
	}

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}
}
