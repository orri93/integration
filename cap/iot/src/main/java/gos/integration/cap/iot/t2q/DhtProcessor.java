package gos.integration.cap.iot.t2q;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class DhtProcessor implements Processor {
	static final String JMSTimestamp = "JMSTimestamp";
	static final String TS = "ts";
	static final String RH = "RH";
	static final String T = "T";
	
	private DateFormat sdf;
	
	public DhtProcessor() {
		this.sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	@SuppressWarnings("unchecked")
	public void process(Exchange exchange) throws Exception {
		Message in = exchange.getIn();
		Map<String, Object> map = (Map<String, Object>)(in.getBody(HashMap.class));
		if (map != null) {
			if(!map.containsKey(TS)) {
				Long ts = in.getHeader(JMSTimestamp, -1L, Long.class);
				Date tsd = new Date(ts);
				String tss = sdf.format(tsd);
				map.put(TS, tss);
			}
			if(map.containsKey(RH)) {
				Double rh = (Double)map.get(RH);
				if(rh != null) {
					if(rh < 0.0 || rh > 100.0) {
						map.remove(RH);
					}
				}
			}
			if(map.containsKey(T)) {
				Double t = (Double)map.get(T);
				if(t != null) {
					if(t < -10.0 || t > 100.0) {
						map.remove(T);
					}
				}
			}
		}
		Message out = exchange.getOut();
		out.setBody(map);
	}
}
