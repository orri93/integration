package gos.integration.finance.poc.file;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FileRoute extends RouteBuilder {
  private static final Logger Log = LoggerFactory.getLogger(FileRoute.class);

  @Override
  public void configure() throws Exception {
    Log.info("Configuring file route");
    from("file:src/main/resources/data?noop=true")
      .split(body().tokenize("\n")).streaming()
      .to("bean:fileBean?method=handle")
      .to("file:src/main/resources/data/out");
  }
}
