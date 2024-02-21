package gos.integration.finance.poc.file;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class FileProcessor implements Processor {

  @Override
  public void process(Exchange exchange) throws Exception {
    throw new UnsupportedOperationException("Unimplemented method 'process'");
  }
}
