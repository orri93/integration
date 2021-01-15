package gos.integration.cap.poc.generator;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gos.integration.cap.poc.generator.configuration.Timer;

public class GeneratorRoute extends RouteBuilder {
	private static final Logger Log =
			LoggerFactory.getLogger(GeneratorRoute.class);
	
	private Timer timer;
	private Processor processor;

	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	
	public void setProcessor(Processor processor) {
		this.processor = processor;
	}
	
	@Override
	public void configure() throws Exception {
		String from = String.format("timer://generator?fixedRate=true&period=%d", timer.getPeriod());
		Log.info("Creating a Generator Route to PostgreSQL JDBC");
		Log.debug("from: " + from);
		from(from).process(processor).to("jdbc:dataSource");
	}
}
