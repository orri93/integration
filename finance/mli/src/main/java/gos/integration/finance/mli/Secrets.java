package gos.integration.finance.mli;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:secrets.properties")
public class Secrets {
  @Value("${finance.database.url}")
  private String url;

  @Value("${finance.database.username}")
  private String username;

  @Value("${finance.database.password}")
  private String password;

  public String getUrl() {
    return url;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }
}
