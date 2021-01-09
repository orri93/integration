package gos.integration.cap.poc.a2p;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class JsonToSqlA02Processor implements Processor {
	static final String NULL = "NULL";
	static final String E = "e";
	static final String T = "t";
	static final String A = "a";
	static final String B = "b";
	static final String C = "c";

	@SuppressWarnings("unchecked")
	public void process(Exchange exchange) throws Exception {
		String sql = null;
		Double e = null, a = null, b = null, c = null;
		Message in = exchange.getIn();
		Map<String, Object> map = (Map<String, Object>)(in.getBody(HashMap.class));
		if (map != null) {
			if(map.containsKey(E)) {
				e = (Double)map.get(E);
			}
			if(map.containsKey(A)) {
				a = (Double)map.get(A);
			}
			if(map.containsKey(B)) {
				b = (Double)map.get(B);
			}
			if(map.containsKey(C)) {
				c = (Double)map.get(C);
			}
			if(e != null) {
				e /= 1000;
				String values = String.format("to_timestamp(%f)", e);
				StringBuilder sqlb = new StringBuilder(
						"INSERT INTO a02(tsz, a, b, c) VALUES (" + values);
				appendValue(sqlb, a);
				appendValue(sqlb, b);
				appendValue(sqlb, c);
				sqlb.append(")");
				sql = sqlb.toString();
			}
		}
		Message out = exchange.getOut();
		out.setBody(sql);				
	}
	
	private static void appendValue(StringBuilder builder, Double value) {
		builder.append(", ");
		if(value != null) {
			builder.append(value);
		} else {
			builder.append(NULL);
		}
	}
}
