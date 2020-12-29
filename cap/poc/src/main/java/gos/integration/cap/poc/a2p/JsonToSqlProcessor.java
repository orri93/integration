package gos.integration.cap.poc.a2p;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class JsonToSqlProcessor implements Processor {
	static final String JMSTimestamp = "JMSTimestamp";
	static final String NULL = "NULL";
	static final String Dht11 = "dht11";
	static final String TS = "ts";
	static final String RH = "RH";
	static final String AT = "t";
	static final String T = "T";

  private DateFormat sdf;
	
	public JsonToSqlProcessor() {
		this.sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	@SuppressWarnings("unchecked")
	public void process(Exchange exchange) throws Exception {
		String tss = null;
		Double rh = null, t = null, at = null, dht11 = null;
		Message in = exchange.getIn();
		Map<String, Object> map = (Map<String, Object>)(in.getBody(HashMap.class));
		if (map != null) {
			if(map.containsKey(TS)) {
				tss = map.get(TS).toString();
			} else {
				Long ts = in.getHeader(JMSTimestamp, -1L, Long.class);
				Date tsd = new Date(ts);
				tss = sdf.format(tsd);
			}
			if(map.containsKey(AT)) {
				at = (Double)map.get(AT);
			}
			if(map.containsKey(Dht11)) {
				dht11 = (Double)map.get(Dht11);
			}
			if(map.containsKey(RH)) {
				rh = (Double)map.get(RH);
			}
			if(map.containsKey(T)) {
				t = (Double)map.get(T);
			}
			
			if(tss != null && at != null && dht11 != null) {
				String values = String.format("'%s', %f, %f", tss, at, dht11);
				StringBuilder sqlb = new StringBuilder(
						"INSERT INTO a01(ts, at, status, rh, t) VALUES (" + values + ", ");
				if(rh != null) {
					sqlb.append(rh);
				} else {
					sqlb.append(NULL);
				}
				sqlb.append(", ");
				if(t != null) {
					sqlb.append(t);
				} else {
					sqlb.append(NULL);
				}
				sqlb.append(")");
				String sql = sqlb.toString();
				Message out = exchange.getOut();
				out.setBody(sql);				
			}
		}		
	}

}