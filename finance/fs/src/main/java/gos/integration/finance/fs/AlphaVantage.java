package gos.integration.finance.fs;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

// For Java REST Client see
// https://docs.spring.io/spring-framework/reference/integration/rest-clients.html

@Component
public class AlphaVantage {
  private static final Logger Log = LoggerFactory.getLogger(AlphaVantage.class);

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
  private static final DecimalFormatSymbols DECIMAL_FORMAT_SYMBOLS = new DecimalFormatSymbols(Locale.US);
  private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.0000", DECIMAL_FORMAT_SYMBOLS);

  static {
    DECIMAL_FORMAT.setParseBigDecimal(true);
  }

  @Autowired
  private Secrets secrets;

  public GlobalQuoteValue queryGlobalQuote(String symbol) {
    RestClient restClient = RestClient.create();
    String queryUrl = String.format("%s?function=GLOBAL_QUOTE&symbol=%s&apikey=%s", secrets.getAlphaVantageUrl(), symbol, secrets.getAlphaVantageApiKey());
    @SuppressWarnings("null")
    String result = restClient.get().uri(queryUrl).retrieve().body(String.class);
    JsonFactory factory = new JsonFactory();
    try {
      JsonParser parser = factory.createParser(result);
      JsonToken token = parser.nextToken();
      String priceText = null;
      String latestTradingDayText = null;

      while (token != JsonToken.END_OBJECT) {
        if (token == JsonToken.FIELD_NAME) {
          String fieldName = parser.getCurrentName();
          if (fieldName.contains("price")) {
            token = parser.nextToken();
            if (token == JsonToken.VALUE_STRING) {
              priceText = parser.getValueAsString();
            }
          } else if (fieldName.contains("latest trading day")) {
            token = parser.nextToken();
            if (token == JsonToken.VALUE_STRING) {
              latestTradingDayText = parser.getValueAsString();
            }
          }
        }
        if (priceText != null && latestTradingDayText != null) {
          break;
        }
        token = parser.nextToken();
      }

      if (priceText != null && latestTradingDayText != null) {
        ParsePosition parsePosition = new ParsePosition(0);
        BigDecimal price =  (BigDecimal)DECIMAL_FORMAT.parse(priceText, parsePosition);
        parsePosition = new ParsePosition(0);
        Date latestTradingDay = DATE_FORMAT.parse(latestTradingDayText, parsePosition);
        return new GlobalQuoteValue(price, latestTradingDay);
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return null;
  }
}
