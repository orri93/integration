package gos.integration.finance.fs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public final class SingletonData {

  static class Status {
    private final Lock marketStatusLock = new ReentrantLock();
    private MarketStatus marketStatus;

    public Status() {
      this.marketStatus = null;
    }

    public MarketStatus getMarketStatus() {
      try {
        marketStatusLock.lock();
        return marketStatus;
      } finally {
        marketStatusLock.unlock();
      }
    }

    public void setMarketStatus(MarketStatus marketStatus) {
      try {
        marketStatusLock.lock();
        this.marketStatus = marketStatus;
      } finally {
        marketStatusLock.unlock();
      }
    }
  }

  // This class is a singleton and is used to store the symbol information
  static class MlSymbolInformation {
    private final Lock quoteValueMapLock = new ReentrantLock();
    private List<String> symbolList = null;
    private Map<String, QuoteValue> quoteValueMap;
    private int symbolIndex = -1;

    public MlSymbolInformation() {
      this.quoteValueMap = new HashMap<>();
    }

    public List<String> getSymbolList() {
      return symbolList;
    }

    public QuoteValue getQuoteValue(String symbol) {
      try {
        quoteValueMapLock.lock();
        if (quoteValueMap.containsKey(symbol)) {
          return quoteValueMap.get(symbol);
        } else {
          return null;
        }
      } finally {
        quoteValueMapLock.unlock();
      }
    }

    public boolean applySymbols(List<String> symbols) {
      boolean isChanged = true;
      if (symbolList == null) {
        symbolList = new ArrayList<>();
        applySymbolList(symbols);
      } else {
        if (symbolList.size() == symbols.size()) {
          if(isSymbolsSame(symbols)) {
            isChanged = false;
          } else {
            symbolList.clear();
            applySymbolList(symbols);
          }
        } else {
          symbolList.clear();
          applySymbolList(symbols);
        }
      }
      if (isChanged) {
        symbolIndex = symbolList.size() > 0 ? 0 : -1;
      }
      return isChanged;
    }

    public String nextSymbol() {
      if (symbolIndex >= 0) {
        String symbol = symbolList.get(symbolIndex);
        symbolIndex++;
        if (symbolIndex >= symbolList.size()) {
          symbolIndex = 0;
        }
        return symbol;
      } else {
        return null;
      }
    }

    public void updateQuoteValue(String symbol, QuoteValue quoteValue) {
      try {
        quoteValueMapLock.lock();
        quoteValueMap.put(symbol, quoteValue);
      } finally {
        quoteValueMapLock.unlock();
      }
    }

    private boolean isSymbolsSame(List<String> symbols) {
      for (String symbol : symbols) {
        if (!symbolList.contains(symbol)) {
          return false;
        }
      }
      return true;
    }

    private void applySymbolList(List<String> symbolList) {
      for(String symbol : symbolList) {
        this.symbolList.add(symbol);
      }
    }
  }

  @Bean
  @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
  public Status status() {
    return new Status();
  }

  @Bean
  @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
  public MlSymbolInformation mlSymbolInformation() {
    return new MlSymbolInformation();
  }
}
