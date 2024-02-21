package gos.integration.finance.poc.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class Application {
  private static final Logger Log = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) {
    Log.info("Starting Finance REST POC Integration");
    SpringApplication.run(Application.class, args);
  }
}
