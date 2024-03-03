package gos.integration.finance.fs;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

  @Autowired
  @Qualifier("status")
  private SingletonData.Status status;

  @Autowired
  @Qualifier("mlSymbolInformation")
  private SingletonData.MlSymbolInformation symbolInformation;

  @GetMapping("/status")
  public MarketStatus getStatus() {
    return status.getMarketStatus();
  }

  @GetMapping("/rdd")
  public RemoteDisplayData getRemoteDisplayData() {
    List<Quote> quoteList = new ArrayList<>();
    List<String> symbolList = symbolInformation.getSymbolList();
    for (String symbol : symbolList) {
      QuoteValue quoteValue = symbolInformation.getQuoteValue(symbol);
      if (quoteValue != null) {
        BigDecimal value = quoteValue.getValue().setScale(4, RoundingMode.HALF_UP);
        BigDecimal change = quoteValue.getChange().setScale(4, RoundingMode.HALF_UP);
        Quote quote = new Quote(symbol, value, change);
        quoteList.add(quote);
      }
    }
    return new RemoteDisplayData(status.getMarketStatus(), quoteList);
  }
}
