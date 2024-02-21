package gos.integration.finance.mli;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MlRowData {
  private static final Logger Log = LoggerFactory.getLogger(MlRowData.class);

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
  private static final DecimalFormatSymbols DECIMAL_FORMAT_SYMBOLS = new DecimalFormatSymbols(Locale.US);
  private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00", DECIMAL_FORMAT_SYMBOLS);

  private Date tradeDate;
  private Date settlementDate;
  private String description;
  private String symbol;
  private BigDecimal quantity;
  private BigDecimal price;
  private BigDecimal amount;

  private MlRowData(String line) {}

  static {
    DECIMAL_FORMAT.setParseBigDecimal(true);
  }

  public Date getTradeDate() {
    return tradeDate;
  }

  public Date getSettlementDate() {
    return settlementDate;
  }

  public String getDescription() {
    return description;
  }

  public String getSymbol() {
    return symbol;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public boolean isValid() {
    return tradeDate != null && settlementDate != null && description != null;
  }

  public static MlRowData parse(String line) {
    MlRowData mlRowData = new MlRowData(line);
    String[] cells = line.split(",");
    if (cells.length >= 9) {
      String tradeDateText = cells[0].replace("\"", "").strip();
      String settlementDateText = cells[1].replace("\"", "").strip();
      mlRowData.description = cells[2].replace("\"", "").strip();
      mlRowData.symbol = cells[4].replace("\"", "").strip();
      String quantityText = cells[5].replace("\"", "").strip();
      String priceText = cells[6].replace("\"", "").strip();
      String amountText = cells[7].replace("\"", "").strip();
      mlRowData.tradeDate = parseDate(tradeDateText);
      mlRowData.settlementDate = parseDate(settlementDateText);
      mlRowData.quantity = parseNumber(quantityText);
      mlRowData.price = parseAmount(priceText);
      mlRowData.amount = parseAmount(amountText);
    } else {
      Log.info("Skip line with less than 9 cells");
    }
    return mlRowData;
  }

  private static Date parseDate(String dateText) {
    if (!dateText.isBlank()) {
      ParsePosition parsePosition = new ParsePosition(0);
      return DATE_FORMAT.parse(dateText, parsePosition);
    } else {
      return null;
    }
  }

  private static BigDecimal parseNumber(String numberText) {
    if (!numberText.isBlank()) {
      ParsePosition parsePosition = new ParsePosition(0);
      return (BigDecimal)DECIMAL_FORMAT.parse(numberText, parsePosition);
    } else {
      return null;
    }
  }

  private static BigDecimal parseAmount(String amountText) {
    if (!amountText.isBlank()) {
      ParsePosition parsePosition = new ParsePosition(0);
      amountText = amountText.replace("$", "");
      return (BigDecimal)DECIMAL_FORMAT.parse(amountText, parsePosition);
    } else {
      return null;
    }
  }
}
