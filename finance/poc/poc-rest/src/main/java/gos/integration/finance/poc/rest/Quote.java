package gos.integration.finance.poc.rest;

public class Quote {
  private String symbol;
  private float price;
  private float change;

  Quote() {}

  Quote(String symbol, float price, float change) {
    this.symbol = symbol;
    this.price = price;
    this.change = change;
  }

  public String getSymbol() {
    return this.symbol;
  }

  public float getPrice() {
    return this.price;
  }

  public float getChange() {
    return this.change;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public void setChange(float change) {
    this.change = change;
  }
}
