package gos.integration.finance.mli;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MlDataBase {
  private static final Logger Log = LoggerFactory.getLogger(MlDataBase.class);

  String SQL_LOOKUP = "SELECT id FROM ml WHERE trade_date = ? AND settlement_date = ? AND action = ? AND description = ? AND amount = ?";
  String SQL_INSERT = "INSERT INTO ml (trade_date, settlement_date, action, description, symbol, quantity, price, amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

  @Autowired
  private Secrets secrets;

  public void ProcessMlRowData(MlRowData mlRowData) {
    try (Connection connection = createConnection()) {
      if (connection != null) {
        long id = lookup(connection, mlRowData);
        if (id > 0) {
          Log.info("Found existing record with id " + id);
        } else {
          insert(connection, mlRowData);
          Log.info("No existing record found");
        }
        Log.info("Connected to database");
      } else {
        Log.error("Failed to connect to database");
      }
    } catch (SQLException e) {
      Log.error("Failed to connect to database", e);
    }
  }

  private Connection createConnection() throws SQLException {
    String url = secrets.getUrl();
    String username = secrets.getUsername();
    String password = secrets.getPassword();
    return DriverManager.getConnection(url, username, password);
  }

  private long lookup(Connection connection, MlRowData mlRowData) throws SQLException {
    PreparedStatement statement = connection.prepareStatement(SQL_LOOKUP);
    statement.setDate(1, new java.sql.Date(mlRowData.getTradeDate().getTime()));
    statement.setDate(2, new java.sql.Date(mlRowData.getSettlementDate().getTime()));
    statement.setString(3, mlRowData.getAction());
    statement.setString(4, mlRowData.getDescription());
    statement.setBigDecimal(5, mlRowData.getAmount());
    ResultSet resultSet = statement.executeQuery();
    if (resultSet.next()) {
      return resultSet.getLong(1);
    } else {
      return 0;
    }
  }

  private int insert(Connection connection, MlRowData mlRowData) throws SQLException {
    PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
    statement.setDate(1, new java.sql.Date(mlRowData.getTradeDate().getTime()));
    statement.setDate(2, new java.sql.Date(mlRowData.getSettlementDate().getTime()));
    statement.setString(3, mlRowData.getAction());
    statement.setString(4, mlRowData.getDescription());
    statement.setString(5, mlRowData.getSymbol());
    statement.setBigDecimal(6, mlRowData.getQuantity());
    statement.setBigDecimal(7, mlRowData.getPrice());
    statement.setBigDecimal(8, mlRowData.getAmount());
    return statement.executeUpdate();
  }
}
