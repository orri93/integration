package gos.integration.finance.fs;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "gos.finance")
public class Configuration {

  static class BusinessLogic {
    private List<String> symbolExclusions;

    public List<String> getSymbolExclusions() {
      return symbolExclusions;
    }

    public void setSymbolExclusions(List<String> symbolExclusions) {
      this.symbolExclusions = symbolExclusions;
    }
  }

  private String secrets;
  private BusinessLogic businessLogic;

  public String getSecrets() {
    return secrets;
  }

  public BusinessLogic getBusinessLogic() {
    return businessLogic;
  }

  public void setSecrets(String secrets) {
    this.secrets = secrets;
  }

  public void setBusinessLogic(BusinessLogic businessLogic) {
    this.businessLogic = businessLogic;
  }
}
