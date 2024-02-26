package gos.integration.finance.fs;

import java.math.BigDecimal;
import java.util.Date;

public class GlobalQuoteValue {
  private BigDecimal value;
  private Date latestTradingDay;
  private Date lastUpdatedTime;

  public GlobalQuoteValue(BigDecimal value, Date latestTradingDay) {
    this.value = value;
    this.latestTradingDay = latestTradingDay;
    this.lastUpdatedTime = new Date();
  }

  public BigDecimal getValue() {
    return value;
  }

  public Date getLatestTradingDay() {
    return latestTradingDay;
  }

  public Date getLastUpdatedTime() {
    return lastUpdatedTime;
  }
}
