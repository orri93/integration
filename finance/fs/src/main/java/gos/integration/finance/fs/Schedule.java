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
  @Qualifier("status")
  private SingletonData.Status status;

  @Autowired
  @Qualifier("mlSymbolInformation")
  private SingletonData.MlSymbolInformation symbolInformation;

  @Autowired
  private BusinessLogic businessLogic;

  @Autowired
  private MlDataBase mlDataBase;

  @Autowired
  private Finnhub finnhub;

  @Scheduled(fixedRate = 4000)
  public void scheduleTaskWithFixedRate() {
    MarketStatus marketStatus = finnhub.queryMarketStatus();
    status.setMarketStatus(marketStatus);

    List<String> purchasedSymbols = mlDataBase.queryPurchasedSymbols();
    List<String> filteredSymbols = businessLogic.symbolsExclusions(purchasedSymbols);
    symbolInformation.applySymbols(filteredSymbols);
    String symbol = symbolInformation.nextSymbol();
    QuoteValue quoteValue = finnhub.queryQuote(symbol);
    symbolInformation.updateQuoteValue(symbol, quoteValue);
  }
}
