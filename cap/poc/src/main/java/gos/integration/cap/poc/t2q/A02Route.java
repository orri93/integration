package gos.integration.cap.poc.t2q;

import java.util.Map;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gos.integration.cap.poc.configuration.Queues;

public class A02Route extends RouteBuilder {
	private static final Logger Log =
			LoggerFactory.getLogger(A02Route.class);
	
	private String from;
	private Queues queues;
	private Processor processor;
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	public void setQueues(Queues queues) {
		this.queues = queues;
	}
	
	public void setProcessor(Processor processor) {
		this.processor = processor;
	}

	@Override
	public void configure() throws Exception {
		Log.info("Creating Route for ActiveMQ topic to ActiveMQ Queue for A02");
		Log.info("From: " + from);
		from(from)
		.unmarshal().json(JsonLibrary.Gson, Map.class)
		.process(processor)
		.marshal().json(JsonLibrary.Gson, Map.class)
		//.to("log:a02");
		//.to("activemq:queue:" + queues.getRemote())
		.to("activemq:queue:" + queues.getLocal());
	}
}
