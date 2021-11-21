package net.brunomendola.querity.spring.web;

import net.brunomendola.querity.api.Query;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueritySpringWebMvcTestController {
  @GetMapping(value = "/query", produces = MediaType.APPLICATION_JSON_VALUE)
  Query getQuery(@RequestParam(required = false) Query q) {
    return q;
  }
}
