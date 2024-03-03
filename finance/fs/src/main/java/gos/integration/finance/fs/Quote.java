package gos.integration.finance.fs;

import java.math.BigDecimal;

public class Quote {
  private String symbol;
  private BigDecimal price;
  private BigDecimal change;

  Quote() {}

  Quote(String symbol, BigDecimal price, BigDecimal change) {
    this.symbol = symbol;
    this.price = price;
    this.change = change;
  }

  public String getSymbol() {
    return this.symbol;
  }

  public BigDecimal getPrice() {
    return this.price;
  }

  public BigDecimal getChange() {
    return this.change;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public void setChange(BigDecimal change) {
    this.change = change;
  }
}
