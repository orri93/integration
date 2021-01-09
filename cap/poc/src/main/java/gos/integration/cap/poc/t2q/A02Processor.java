package gos.integration.cap.poc.t2q;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class A02Processor implements Processor {
	static final String JMSTimestamp = "JMSTimestamp";
	static final String E = "e";
	static final String T = "t";

	private DateFormat sdf;

	public A02Processor() {
		this.sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
		this.sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	@SuppressWarnings("unchecked")
	public void process(Exchange exchange) throws Exception {
		Long ts = null;
		Date tsd = null;
		String tss = null;
		Message in = exchange.getIn();
		ts = in.getHeader(JMSTimestamp, -1L, Long.class);
		if(ts != null) {
			tsd = new Date(ts);
			if(tsd != null) {
				tss = sdf.format(tsd);
			}
		}
		Map<String, Object> map = (Map<String, Object>)(in.getBody(HashMap.class));
		if (map != null) {
			if(!(map.containsKey(E) || ts == null)) {
				map.put(E, ts);
			}
			if(!(map.containsKey(T) || tss == null)) {
				map.put(T, tss);
			}
		}
		Message out = exchange.getOut();
		out.setBody(map);
	}
}
