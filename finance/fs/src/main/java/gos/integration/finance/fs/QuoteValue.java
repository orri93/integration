package gos.integration.finance.fs;

import java.math.BigDecimal;
import java.util.Date;

public class QuoteValue {
  private BigDecimal value;
  private BigDecimal change;
  private Date lastUpdatedTime;

  public QuoteValue(BigDecimal value, BigDecimal change) {
    this.value = value;
    this.change = change;
    this.lastUpdatedTime = new Date();
  }

  public BigDecimal getValue() {
    return value;
  }

  public BigDecimal getChange() {
    return change;
  }

  public Date getLastUpdatedTime() {
    return lastUpdatedTime;
  }
}
