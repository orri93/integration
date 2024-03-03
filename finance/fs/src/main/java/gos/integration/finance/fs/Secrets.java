package gos.integration.finance.fs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("secrets.properties")
public class Secrets {
  @Value("${finance.database.url}")
  private String url;

  @Value("${finance.database.username}")
  private String username;

  @Value("${finance.database.password}")
  private String password;

  @Value("${alphavantage.url}")
  private String alphaVantageUrl;

  @Value("${alphavantage.apikey}")
  private String alphaVantageApiKey;

  @Value("${finnhub.url}")
  private String finnhubUrl;

  @Value("${finnhub.apikey}")
  private String finnhubApiKey;

  public String getUrl() {
    return url;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getAlphaVantageUrl() {
    return alphaVantageUrl;
  }

  public String getAlphaVantageApiKey() {
    return alphaVantageApiKey;
  }

  public String getFinnhubUrl() {
    return finnhubUrl;
  }

  public String getFinnhubApiKey() {
    return finnhubApiKey;
  }
}
