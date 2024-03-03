package gos.integration.finance.fs;

import java.util.List;

public class RemoteDisplayData {
  private MarketStatus marketStatus;
  private List<Quote> quoteList;

  public RemoteDisplayData(MarketStatus marketStatus, List<Quote> quoteList) {
    this.marketStatus = marketStatus;
    this.quoteList = quoteList;
  }

  public MarketStatus getMarketStatus() {
    return marketStatus;
  }

  public List<Quote> getQuoteList() {
    return quoteList;
  }
}
