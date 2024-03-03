package gos.integration.finance.fs;

public class MarketStatus {
  private boolean isOpen;
  private String holiday;

  public MarketStatus(boolean isOpen, String holiday) {
    this.isOpen = isOpen;
    this.holiday = holiday;
  }

  public boolean isOpen() {
    return isOpen;
  }

  public String getHoliday() {
    return holiday;
  }
}
