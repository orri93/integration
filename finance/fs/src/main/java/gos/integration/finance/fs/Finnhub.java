package gos.integration.finance.fs;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

@Component
public class Finnhub {
  private static final Logger Log = LoggerFactory.getLogger(Finnhub.class);

  @Autowired
  private Secrets secrets;

  public MarketStatus queryMarketStatus() {
    String queryUrl = String.format("%s/stock/market-status?exchange=US&token=%s", secrets.getFinnhubUrl(), secrets.getFinnhubApiKey());
    RestClient restClient = RestClient.create();
    @SuppressWarnings("null")
    String result = restClient.get().uri(queryUrl).retrieve().body(String.class);
    Map<String, Object> resultMap = parseFinnhubResults(result, List.of("isOpen", "holiday"));
    if (resultMap.containsKey("isOpen") && resultMap.containsKey("holiday")) {
      return new MarketStatus((Boolean) resultMap.get("isOpen"), (String) resultMap.get("holiday"));
    }
    return null;
  }

  public QuoteValue queryQuote(String symbol) {
    String queryUrl = String.format("%s/quote?symbol=%s&token=%s", secrets.getFinnhubUrl(), symbol, secrets.getFinnhubApiKey());
    RestClient restClient = RestClient.create();
    @SuppressWarnings("null")
    String result = restClient.get().uri(queryUrl).retrieve().body(String.class);
    Map<String, Object> resultMap = parseFinnhubResults(result, List.of("c", "d"));
    if (resultMap.containsKey("c") &&  resultMap.containsKey("d")) {
      float c = 0.0f, d = 0.0f;
      if (resultMap.get("c") instanceof Float) {
        c = (float)resultMap.get("c");
      }
      if (resultMap.get("d") instanceof Float) {
        d = (float)resultMap.get("d");
      }
      return new QuoteValue(BigDecimal.valueOf((double)c), BigDecimal.valueOf((double)d));
    }
    return null;
  }

  private static Map<String, Object> parseFinnhubResults(String result, List<String> fields) {
    Map<String, Object> resultMap = new HashMap<>();
    JsonFactory factory = new JsonFactory();
    try {
      JsonParser parser = factory.createParser(result);
      JsonToken token = parser.nextToken();
      while (token != JsonToken.END_OBJECT) {
        if (token == JsonToken.FIELD_NAME) {
          String fieldName = parser.getCurrentName();
          if (fields.contains(fieldName)) {
            token = parser.nextToken();
            if (token == JsonToken.VALUE_STRING) {
              resultMap.put(fieldName, parser.getValueAsString());
            } else if (token == JsonToken.VALUE_NUMBER_INT) {
              resultMap.put(fieldName, parser.getIntValue());
            } else if (token == JsonToken.VALUE_NUMBER_FLOAT) {
              resultMap.put(fieldName, parser.getFloatValue());
            } else if (token == JsonToken.VALUE_TRUE) {
              resultMap.put(fieldName, Boolean.valueOf(true));
            } else if (token == JsonToken.VALUE_FALSE) {
              resultMap.put(fieldName, Boolean.valueOf(false));
            } else if (token == JsonToken.VALUE_NULL) {
              resultMap.put(fieldName, null);
            }
          }
        }
        token = parser.nextToken();
      }
    } catch (IOException e) {
      Log.error("Failed to parse Finnhub results", e);
    }
    return resultMap;
  }
}
