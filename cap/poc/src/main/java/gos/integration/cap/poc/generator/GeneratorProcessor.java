package gos.integration.cap.poc.generator;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class GeneratorProcessor implements Processor {
	private Simulation simulation;

	public void setSimulation(Simulation simulation) {
		this.simulation = simulation;
	}

	public void process(Exchange exchange) throws Exception {
		Values values = this.simulation.getNext();
		StringBuilder bodyBuilder = new StringBuilder(
				"INSERT INTO a03(tsz, td1, tv1, td2, tv2, td3, tv3, td4, tv4) VALUES (now()");
		for(Tool tool : values.getTools()) {
			bodyBuilder.append(',');
			bodyBuilder.append(tool.getDepth());
			bodyBuilder.append(',');
			bodyBuilder.append(tool.getValue());
		}
		bodyBuilder.append(')');
		Message out = exchange.getOut();
		String body = bodyBuilder.toString();
		out.setBody(body);
	}
}
