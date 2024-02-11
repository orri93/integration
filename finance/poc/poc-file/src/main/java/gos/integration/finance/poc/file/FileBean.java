package gos.integration.finance.poc.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FileBean {
  private static final Logger Log = LoggerFactory.getLogger(FileBean.class);

  public String handle(String input) {
    Log.info("Handling input: " + input);
    return input.toUpperCase();
  }
}
