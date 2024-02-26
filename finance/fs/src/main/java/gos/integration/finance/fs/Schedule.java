package gos.integration.finance.fs;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Schedule {
  private static final Logger Log = LoggerFactory.getLogger(Schedule.class);

  @Autowired
  @Qualifier("mlSymbolInformation")
  private SingletonData.MlSymbolInformation symbolInformation;

  @Autowired
  private MlDataBase mlDataBase;

  @Autowired
  private AlphaVantage alphaVantage;

  @Scheduled(fixedRate = 5000)
  public void scheduleTaskWithFixedRate() {
    List<String> purchasedSymbols = mlDataBase.queryPurchasedSymbols();
    symbolInformation.applySymbols(purchasedSymbols);
    String symbol = symbolInformation.nextSymbol();
    GlobalQuoteValue quote = alphaVantage.queryGlobalQuote(symbol);
    symbolInformation.updateQuoteValue(symbol, quote);
  }
}
