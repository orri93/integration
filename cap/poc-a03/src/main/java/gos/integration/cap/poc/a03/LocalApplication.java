package gos.integration.cap.poc.a03;

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

import gos.integration.cap.poc.generator.Configuration;
import gos.integration.cap.poc.generator.GeneratorProcessor;
import gos.integration.cap.poc.generator.GeneratorRoute;
import gos.integration.cap.poc.generator.Simulation;

@SpringBootApplication
@EnableAutoConfiguration
public class LocalApplication {
	private static final Logger Log =
			LoggerFactory.getLogger(LocalApplication.class);
	
	public static void main(String[] args) {
		Log.info("Starting A03 POC Integration");
		SpringApplication.run(LocalApplication.class, args);
	}
	
	@Component
	@ConfigurationProperties(prefix = "gos.poca03")
	class PocConfiguration extends Configuration {
	}
	
	@Component
	class PocProcessor extends GeneratorProcessor {
		@Override
		public void process(Exchange exchange) throws Exception {
			super.process(exchange);
		}
	}
	
	@Component
	class PocSimulation extends Simulation {
	}
	
	@Component
	class A03Route extends GeneratorRoute {

		@Autowired
		DataSource dataSource;

		public DataSource getDataSource() {
			return dataSource;
		}

		public void setDataSource(DataSource dataSource) {
			this.dataSource = dataSource;
		}
		
		@Autowired
		PocConfiguration configuration;
		
		@Autowired
		private PocProcessor pocProcessor;
		
		@Autowired
		PocSimulation simulation;

		@Override
		public void configure() throws Exception {
			simulation.setGenerator(configuration.getGenerator());
			simulation.initialize();
			pocProcessor.setSimulation(simulation);
			super.setTimer(configuration.getTimer());
			super.setProcessor(pocProcessor);
			super.configure();
		}
	}
}
