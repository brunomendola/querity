package net.brunomendola.querity.spring.web;

import net.brunomendola.querity.api.Condition;
import net.brunomendola.querity.api.Query;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueritySpringWebMvcTestController {
  @GetMapping(value = "/query", produces = MediaType.APPLICATION_JSON_VALUE)
  public Query getQuery(@RequestParam(required = false) Query q) {
    return q;
  }

  @GetMapping(value = "/query-with-preprocessor", produces = MediaType.APPLICATION_JSON_VALUE)
  public Query getQueryWithPreprocessor(@RequestParam(required = false) @WithPreprocessor("preprocessor1") Query q) {
    return q;
  }

  @GetMapping(value = "/count", produces = MediaType.APPLICATION_JSON_VALUE)
  public Condition getCount(@RequestParam(required = false) Condition filter) {
    return filter;
  }

  @GetMapping(value = "/count-with-preprocessor", produces = MediaType.APPLICATION_JSON_VALUE)
  public Condition getCountWithPreprocessor(@RequestParam(required = false) @WithPreprocessor("preprocessor1") Condition filter) {
    return filter;
  }
}
