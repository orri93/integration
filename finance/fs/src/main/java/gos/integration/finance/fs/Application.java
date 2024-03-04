package gos.integration.finance.fs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableAutoConfiguration
public class Application {
  private static final Logger Log = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) {
    Log.info("Starting Finance Integration Service");
    //String userDir = System.getProperty("user.dir");
    //Log.info("User directory: " + userDir);
    SpringApplication.run(Application.class, args);
  }
}
