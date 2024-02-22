package gos.integration.finance.mli;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MlRowData {
  private static final Logger Log = LoggerFactory.getLogger(MlRowData.class);

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
  private static final DecimalFormatSymbols DECIMAL_FORMAT_SYMBOLS = new DecimalFormatSymbols(Locale.US);
  private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00", DECIMAL_FORMAT_SYMBOLS);

  private Date tradeDate;
  private Date settlementDate;
  private String action;
  private String description;
  private String symbol;
  private BigDecimal quantity;
  private BigDecimal price;
  private BigDecimal amount;

  private MlRowData() {}

  public static enum Column {
    TRADE_DATE,
    SETTLEMENT_DATE,
    DESCRIPTION,
    SYMBOL,
    QUANTITY,
    PRICE,
    AMOUNT
  }

  static {
    DECIMAL_FORMAT.setParseBigDecimal(true);
  }

  public Date getTradeDate() {
    return tradeDate;
  }

  public Date getSettlementDate() {
    return settlementDate;
  }

  public String getAction() {
    return action;
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

  public void setAction(String action) {
    this.action = action;
  }

  public static boolean isHeaderRow(String line) {
    String[] cells = line.split(" ,");
    if (cells.length >= 9) {
      String tradeDateText = cells[0].replace("\"", "").strip();
      String settlementDateText = cells[1].replace("\"", "").strip();
      return tradeDateText.equals("Trade Date") && settlementDateText.equals("Settlement Date");
    } else {
      return false;
    }
  }

  public static Map<Column, Integer> parseHeader(String line) {
    Map<Column, Integer> header = new HashMap<>();
    String[] cells = line.split(" ,");
    if (cells.length >= 9) {
      for (int i = 0; i < cells.length; i++) {
        Log.info("Header cell " + i + ": " + cells[i]);
        String cell = cells[i].replace("\"", "").strip();
        if (cell.equals("Trade Date")) {
          header.put(Column.TRADE_DATE, i);
        } else if (cell.equals("Settlement Date")) {
          header.put(Column.SETTLEMENT_DATE, i);
        } else if (cell.equals("Description")) {
          header.put(Column.DESCRIPTION, i);
        } else if (cell.startsWith("Symbol")) {
          header.put(Column.SYMBOL, i);
        } else if (cell.equals("Quantity")) {
          header.put(Column.QUANTITY, i);
        } else if (cell.equals("Price")) {
          header.put(Column.PRICE, i);
        } else if (cell.equals("Amount")) {
          header.put(Column.AMOUNT, i);
        }
      }
    }
    return header;
  }

  public static MlRowData parse(Map<Column, Integer> columnMap, String line) {
    MlRowData mlRowData = new MlRowData();
    String[] cells = line.split(" ,");
    if (cells.length >= 9) {
      if (columnMap.containsKey(Column.TRADE_DATE)) {
        String tradeDateText = cells[columnMap.get(Column.TRADE_DATE)].replace("\"", "").strip();
        mlRowData.tradeDate = parseDate(tradeDateText);
      }
      if (columnMap.containsKey(Column.SETTLEMENT_DATE)) {
        String settlementDateText = cells[columnMap.get(Column.SETTLEMENT_DATE)].replace("\"", "").strip();
        mlRowData.settlementDate = parseDate(settlementDateText);
      }
      if (columnMap.containsKey(Column.DESCRIPTION)) {
        mlRowData.description = cells[columnMap.get(Column.DESCRIPTION)].replace("\"", "").strip();
      }
      if (columnMap.containsKey(Column.SYMBOL)) {
        mlRowData.symbol = cells[columnMap.get(Column.SYMBOL)].replace("\"", "").strip();
      }
      if (columnMap.containsKey(Column.QUANTITY)) {
        String quantityText = cells[columnMap.get(Column.QUANTITY)].replace("\"", "").strip();
        mlRowData.quantity = parseNumber(quantityText);
      }
      if (columnMap.containsKey(Column.PRICE)) {
        String priceText = cells[columnMap.get(Column.PRICE)].replace("\"", "").strip();
        mlRowData.price = parseAmount(priceText);
      }
      if (columnMap.containsKey(Column.AMOUNT)) {
        String amountText = cells[columnMap.get(Column.AMOUNT)].replace("\"", "").strip();
        mlRowData.amount = parseAmount(amountText);
      }
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
      BigDecimal amount = (BigDecimal)DECIMAL_FORMAT.parse(amountText, parsePosition);
      return amount;
    } else {
      return null;
    }
  }
}
