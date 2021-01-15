package gos.integration.cap.poc.generator;

import java.util.LinkedList;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gos.integration.cap.poc.generator.configuration.Depth;
import gos.integration.cap.poc.generator.configuration.Generator;
import gos.integration.cap.poc.generator.configuration.Noise;

public class Simulation {
	private static final Logger Log =
			LoggerFactory.getLogger(Simulation.class);
	
	private Random random;
	
	private LinkedList<Generation> generations;
	
	private Generator generator;
	private double depth;
	private int step;
	private int at;

	public Simulation() {
		this.random = new Random();
	}
	
	public void setGenerator(Generator generator) {
		this.generator = generator;
	}
	
	public void initialize() {
		Log.info("Initialize the Generator Simulation");
		this.random = new Random();
		this.generations = new LinkedList<Generation>();
		for(gos.integration.cap.poc.generator.configuration.Tool toolConfiguration : this.generator.getTools()) {
			Generation generation = new Generation(random, toolConfiguration);
			this.generations.add(generation);
		}
		Depth depth = this.generator.getDepth();
		this.depth = depth.getRange().getFrom();
		this.step = 0;
		this.at = 0;
	}
	
	public Values getNext() {
		Noise depthNoise = this.generator.getDepth().getNoise();
		Noise valueNoise = this.generator.getValue().getNoise();
		double mu = ((double)this.step) / ((double)this.generator.getValue().getStepCount());
		Values values = new Values(mu, this.step++, this.at++);
		for(Generation generation : this.generations) {
			if(this.step > this.generator.getValue().getStepCount()) {
				generation.shift();
			}
			double depth = generation.depth(this.depth, depthNoise.getSd());
			double value = generation.value(mu, valueNoise.getSd());
			Tool tool = new Tool(depth, value);
			values.add(tool);
		}
		if(this.step > this.generator.getValue().getStepCount()) {
			this.step = 0;
		}
		this.depth += this.generator.getDepth().getStep();
		if(this.depth >= this.generator.getDepth().getRange().getTo()) {
			this.depth = this.generator.getDepth().getRange().getFrom();
		}
		return values;
	}
}
