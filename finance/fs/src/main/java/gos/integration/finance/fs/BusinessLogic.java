package gos.integration.finance.fs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BusinessLogic {
  @Autowired
  private Configuration configuration;

  public List<String> symbolsExclusions(List<String> symbols) {
    List<String> exclusions = configuration.getBusinessLogic().getSymbolExclusions();
    List<String> result = new ArrayList<>(symbols);
    for (String exclusion : exclusions) {
      if (result.contains(exclusion)) {
        result.remove(exclusion);
      }
    }
    return result;
  }
}
