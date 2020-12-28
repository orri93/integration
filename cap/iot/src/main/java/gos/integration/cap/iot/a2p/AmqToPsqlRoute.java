package gos.integration.cap.iot.a2p;

import java.util.Map;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmqToPsqlRoute extends RouteBuilder {
	private static final Logger Log =
			LoggerFactory.getLogger(AmqToPsqlRoute.class);
	
	private String from;
	private Processor processor;
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	public void setProcessor(Processor processor) {
		this.processor = processor;
	}

	@Override
	public void configure() throws Exception {
		Log.info("Creating Route for ActiveMQ Queue to PostgreSQL JDBC");
		Log.info("From: " + from);
		from(from).unmarshal().json(JsonLibrary.Gson, Map.class).process(processor)
		.to("jdbc:dataSource");
		//.to("log:testing");
	}
}
