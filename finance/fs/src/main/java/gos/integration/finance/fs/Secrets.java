package gos.integration.finance.fs;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class Secrets {
  private static Properties properties;

  @Autowired
  private Configuration configuration;

  public String getUrl() {
    return properties.getProperty("finance.database.url");
  }

  public String getUsername() {
    return properties.getProperty("finance.database.username");
  }

  public String getPassword() {
    return properties.getProperty("finance.database.password");
  }

  public String getAlphaVantageUrl() {
    return properties.getProperty("alphavantage.url");
  }

  public String getAlphaVantageApiKey() {
    return properties.getProperty("alphavantage.apikey");
  }

  public String getFinnhubUrl() {
    return properties.getProperty("finnhub.url");
  }

  public String getFinnhubApiKey() {
    return properties.getProperty("finnhub.apikey");
  }

  @PostConstruct
  private void init() throws IOException {
    if (properties == null) {
      loadProperties(configuration.getSecrets());
    }
  }

  private static void loadProperties(String fileName) throws IOException {
    FileInputStream fis = new FileInputStream(fileName);
    properties = new Properties();
    properties.load(fis);
    fis.close();
  }
}
