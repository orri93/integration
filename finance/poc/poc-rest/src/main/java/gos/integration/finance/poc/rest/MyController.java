package gos.integration.finance.poc.rest;

  import java.util.LinkedList;
  import java.util.List;

  import org.springframework.web.bind.annotation.GetMapping;
  import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
  @GetMapping("/test")
  List<Employee> test() {
    LinkedList<Employee> list = new LinkedList<>();
    list.add(new Employee("John", "Developer"));
    list.add(new Employee("Jane", "Manager"));
    return list;
  }

  @GetMapping("/quotes")
  List<Quote> quotes() {
    LinkedList<Quote> list = new LinkedList<>();
    list.add(new Quote("AAPL", 123.45f, 1.23f));
    list.add(new Quote("GOOGL", 2345.67f, 12.34f));
    return list;
  }

  @GetMapping("/mcu")
  McuData mcu() {
    McuData mcu = new McuData();
    mcu.setValue(123.45f);
    mcu.setChange(1.23f);
    mcu.addQuote(new Quote("AAPL", 123.45f, 1.23f));
    mcu.addQuote(new Quote("GOOGL", 2345.67f, 12.34f));
    return mcu;
  }
}
