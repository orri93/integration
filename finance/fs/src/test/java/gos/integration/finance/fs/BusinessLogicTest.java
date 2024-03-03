package gos.integration.finance.fs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BusinessLogic.class, Configuration.class})
public class BusinessLogicTest {

  @Autowired
  private BusinessLogic businessLogic;

  @Test
  public void testSymbolsExclusions() {
    //List<String> symbols = List.of("AAPL", "GOOGL", "CS", "MSFT", "TSLA");
    //List<String> result = businessLogic.symbolsExclusions(symbols);
    //assertEquals(4, result.size());
  }
}
