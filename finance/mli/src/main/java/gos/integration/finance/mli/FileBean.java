package gos.integration.finance.mli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FileBean {
  private static final Logger Log = LoggerFactory.getLogger(FileBean.class);

  public String handle(String input) {
    MlRowData mlRowData = MlRowData.parse(input);
    if (mlRowData.isValid()) {
      Log.info("Row is valid");
      return mlRowData.toString();
    } else {
      Log.warn("Row is invalid");
      return "Invalid row";
    }
  }
}
