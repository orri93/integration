package gos.integration.cap.iot;

import javax.sql.DataSource;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import gos.integration.cap.iot.a2p.AmqToPsqlRoute;
import gos.integration.cap.iot.a2p.JsonToSqlProcessor;
import gos.integration.cap.iot.configuration.Configuration;
import gos.integration.cap.iot.t2q.DhtProcessor;
import gos.integration.cap.iot.t2q.DhtRoute;

@SpringBootApplication
@EnableAutoConfiguration
public class RaspberryPiApplication {
	private static final Logger Log =
			LoggerFactory.getLogger(RaspberryPiApplication.class);
	
	public static void main(String[] args) {
		Log.info("Starting Raspberry Pi IoT Integration");
		SpringApplication.run(RaspberryPiApplication.class, args);
	}
	
	@Component
	@ConfigurationProperties(prefix = "gos.iotdht")
	class RaspberryPiConfiguration extends Configuration {
	}
	
	@Component
	class IotDhtProcessor extends DhtProcessor {
		@Override
		public void process(Exchange exchange) throws Exception {
			super.process(exchange);
		}
	}
	
	@Component
	class Amq2PsqlProcessor extends JsonToSqlProcessor {
		@Override
		public void process(Exchange exchange) throws Exception {
			super.process(exchange);
		}
	}
		
	@Component
	class IotDhtRoute extends DhtRoute {
		@Autowired
		private IotDhtProcessor dhtProcessor;
		
		@Autowired
		private RaspberryPiConfiguration configuration;
		
		@Override
		public void configure() throws Exception {
			super.setFrom("activemq:topic:" + configuration.getTopic());
			super.setQueues(configuration.getQueues());
			super.setProcessor(dhtProcessor);
			super.configure();
		}
	}

	@Component
	class Amq2PsqlRoute extends AmqToPsqlRoute {
		
		@Autowired
		DataSource dataSource;

		public DataSource getDataSource() {
			return dataSource;
		}

		public void setDataSource(DataSource dataSource) {
			this.dataSource = dataSource;
		}

		@Autowired
		private Amq2PsqlProcessor amq2Psqlprocessor;
		
		@Autowired
		private RaspberryPiConfiguration configuration;

		@Override
		public void configure() throws Exception {
			super.setFrom("activemq:queue:" + configuration.getQueues().getLocal());
			super.setProcessor(amq2Psqlprocessor);
			super.configure();
		}
	}
}
