package gos.integration.finance.fs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MlDataBase {
  private static final Logger Log = LoggerFactory.getLogger(MlDataBase.class);

  String PURCHASED_SYMBOLS_QUERY = "SELECT symbol FROM ml WHERE action = 'Purchase' GROUP BY symbol";

  @Autowired
  private Secrets secrets;

  public List<String> queryPurchasedSymbols() {
    try (Connection connection = createConnection()) {
      if (connection != null) {
        List<String> symbols = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(PURCHASED_SYMBOLS_QUERY);
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()) {
          symbols.add(resultSet.getString(1));
        }
        return symbols;
      } else {
        Log.error("Failed to connect to database");
        return null;
      }
    } catch (SQLException e) {
      Log.error("Failed to connect to database", e);
      return null;
    }
  }

  private Connection createConnection() throws SQLException {
    String url = secrets.getUrl();
    String username = secrets.getUsername();
    String password = secrets.getPassword();
    return DriverManager.getConnection(url, username, password);
  }
}
