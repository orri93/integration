package gos.integration.cap.poc.a02;

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

import gos.integration.cap.poc.a2p.AmqToPsqlRoute;
import gos.integration.cap.poc.a2p.JsonToSqlA02Processor;
import gos.integration.cap.poc.configuration.Configuration;
import gos.integration.cap.poc.t2q.A02Processor;
import gos.integration.cap.poc.t2q.A02Route;

@SpringBootApplication
@EnableAutoConfiguration
public class LocalApplication {
	private static final Logger Log =
			LoggerFactory.getLogger(LocalApplication.class);
	
	public static void main(String[] args) {
		Log.info("Starting A02 POC Integration");
		SpringApplication.run(LocalApplication.class, args);
	}
	
	@Component
	@ConfigurationProperties(prefix = "gos.poca02")
	class PocConfiguration extends Configuration {
	}
	
	@Component
	class A02PocProcessor extends A02Processor {
		@Override
		public void process(Exchange exchange) throws Exception {
			super.process(exchange);
		}
	}
	
	@Component
	class Amq2PsqlProcessor extends JsonToSqlA02Processor {
		@Override
		public void process(Exchange exchange) throws Exception {
			super.process(exchange);
		}
	}

	@Component
	class A02PocRoute extends A02Route {
		@Autowired
		private A02PocProcessor a02PocProcessor;
		
		@Autowired
		private PocConfiguration configuration;
				
		@Override
		public void configure() throws Exception {
			super.setFrom("activemq:topic:" + configuration.getTopic());
			super.setQueues(configuration.getQueues());
			super.setProcessor(a02PocProcessor);
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
		private PocConfiguration configuration;

		@Override
		public void configure() throws Exception {
			super.setFrom("activemq:queue:" + configuration.getQueues().getLocal());
			super.setProcessor(amq2Psqlprocessor);
			super.configure();
		}
	}
}
