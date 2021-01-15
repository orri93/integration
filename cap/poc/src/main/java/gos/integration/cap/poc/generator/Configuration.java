package gos.integration.cap.poc.generator;

import gos.integration.cap.poc.generator.configuration.Generator;
import gos.integration.cap.poc.generator.configuration.Timer;

public class Configuration {
	private Timer timer;
	private Generator generator;

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public Generator getGenerator() {
		return generator;
	}

	public void setGenerator(Generator generator) {
		this.generator = generator;
	}
}
