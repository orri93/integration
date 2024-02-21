package gos.integration.finance.poc.rest;

import java.util.LinkedList;
import java.util.List;

public class McuData {
  private List<Quote> quotes;
  private float value;
  private float change;

  public McuData() {
    this.quotes = new LinkedList<>();
  }

  public void addQuote(Quote quote) {
    quotes.add(quote);
  }

  public List<Quote> getQuotes() {
    return quotes;
  }

  public float getValue() {
    return value;
  }

  public float getChange() {
    return change;
  }

  public void setQuotes(List<Quote> quotes) {
    this.quotes = quotes;
  }

  public void setValue(float value) {
    this.value = value;
  }

  public void setChange(float change) {
    this.change = change;
  }
}
