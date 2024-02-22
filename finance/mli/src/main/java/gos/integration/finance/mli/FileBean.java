package gos.integration.finance.mli;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileBean {
  private static final Logger Log = LoggerFactory.getLogger(FileBean.class);

  private static Map<MlRowData.Column, Integer> columnMap = null;

  @Autowired
  private MlDataBase mlDataBase;

  public String handle(String input) {
    if (MlRowData.isHeaderRow(input)) {
      Log.info("Header row");
      FileBean.columnMap = MlRowData.parseHeader(input);
      return new String();
    } else {
      if (FileBean.columnMap != null) {
        MlRowData mlRowData = MlRowData.parse(FileBean.columnMap, input);
        if (mlRowData.isValid()) {
          String action = parseAction(mlRowData.getDescription());
          if (action != null) {
            mlRowData.setAction(action);
            mlDataBase.ProcessMlRowData(mlRowData);
            Log.info("Action: " + action);
            return action;
          } else {
            return new String();
          }
        } else {
          Log.warn("Row is invalid");
          return new String();
        }
      } else {
        Log.error("No header row");
        return new String();
      }
    }
  }

  public static String parseAction(String description) {
    if (description.startsWith("Funds Received")) {
      return "FundsReceived";
    } else if (description.startsWith("Purchase")) {
      return "Purchase";
    } else if (description.startsWith("Dividend")) {
      return "Dividend";
    } else if (description.startsWith("Foreign Tax Withholding")) {
      return "ForeignTaxWithholding";
    } else if (description.startsWith("Foreign Dividend")) {
      return "ForeignDividend";
    } else if (description.startsWith("Cash In Lieu")) {
      return "CashInLieu";
    } else if (description.startsWith("Exchange")) {
      return "Exchange";
    } else if (description.startsWith("Withdrawal")) {
      return "Withdrawal";
    } else if (description.startsWith("Transaction Fee")) {
      return "TransactionFee";
    } else {
      Log.warn("Unknown action for description '" + description + "'");
      return "Unknown";
    }
  }
}
