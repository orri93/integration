package gos.integration.cap.iot.t2q;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class DhtProcessor implements Processor {

	@SuppressWarnings("unchecked")
	public void process(Exchange exchange) throws Exception {
		Message in = exchange.getIn();
		Map<String, Object> map = (Map<String, Object>)(in.getBody(HashMap.class));
		
		if (map != null) {
			if(!map.containsKey("ts")) {
				Long ts = in.getHeader("JMSTimestamp", -1L, Long.class);
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
				Date tsd = new Date(ts);
				String tss = sdf.format(tsd);
				map.put("ts", tss);
			}
		}
		
		Message out = exchange.getOut();
		out.setBody(map);
	}

}
