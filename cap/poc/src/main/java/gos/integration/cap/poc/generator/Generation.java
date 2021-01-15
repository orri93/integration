package gos.integration.cap.poc.generator;

import java.util.Random;

import gos.integration.cap.poc.generator.configuration.Range;

public class Generation {
	private Random random;
	private gos.integration.cap.poc.generator.configuration.Tool configuration;
	
	private double y0;
	private double y1;
	private double y2;
	private double y3;
	
	public Generation(Random random, gos.integration.cap.poc.generator.configuration.Tool configuration) {
		this.random = random;
		this.configuration = configuration;
		this.y0 = generate(this.configuration.getRange(), this.random);
		this.y1 = generate(this.configuration.getRange(), this.random);
		this.y2 = generate(this.configuration.getRange(), this.random);
		this.y3 = generate(this.configuration.getRange(), this.random);
	}
	
	public void shift() {
		this.y0 = this.y1;
		this.y1 = this.y2;
		this.y2 = this.y3;
		this.y3 = generate(this.configuration.getRange(), this.random);
	}
	
	public double depth(double depth, double noiseSd) {
		return depth + configuration.getOffset() + random.nextGaussian() * noiseSd;
	}
	
	public double value(double mu, double noiseSd) {
		return Interpolation.cubic(this.y0, this.y1, this.y2, this.y3, mu) + random.nextGaussian() * noiseSd;
	}
	
	private double generate(Range range, Random random) {
		double d = range.getTo() - range.getFrom();
		return range.getFrom() + random.nextDouble() * d;
	}
}
