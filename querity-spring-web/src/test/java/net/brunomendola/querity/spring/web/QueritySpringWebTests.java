package net.brunomendola.querity.spring.web;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(controllers = QueritySpringWebMvcTestController.class)
@Import(QueritySpringWebAutoConfiguration.class)
class QueritySpringWebTests {
  @Autowired
  MockMvc mockMvc;

  /**
   * Tests JSON deserialization and serialization of a Query object given as a REST endpoint query parameter
   */
  @ParameterizedTest
  @ValueSource(strings = {
      /* single simple condition */   "{\"filter\":{\"propertyName\":\"lastName\",\"operator\":\"EQUALS\",\"value\":\"Skywalker\"}}",
      /* no value condition */        "{\"filter\":{\"propertyName\":\"lastName\",\"operator\":\"IS_NULL\"}}",
      /* conditions wrapper */        "{\"filter\":{\"logic\":\"AND\",\"conditions\":[{\"propertyName\":\"lastName\",\"operator\":\"EQUALS\",\"value\":\"Skywalker\"}]}}",
      /* nested conditions wrapper */ "{\"filter\":{\"logic\":\"AND\",\"conditions\":[{\"propertyName\":\"lastName\",\"operator\":\"EQUALS\",\"value\":\"Skywalker\"},{\"logic\":\"OR\",\"conditions\":[{\"propertyName\":\"firstName\",\"operator\":\"EQUALS\",\"value\":\"Anakin\"},{\"propertyName\":\"firstName\",\"operator\":\"EQUALS\",\"value\":\"Luke\"}]}]}}",
      /* pagination */                "{\"pagination\":{\"page\":1,\"pageSize\":20}}",
      /* sort */                      "{\"sort\":[{\"propertyName\":\"lastName\"},{\"propertyName\":\"firstName\",\"direction\":\"DESC\"}]}",
  })
  void givenJsonQuery_whenGetQuery_thenReturnTheSameQueryAsResponse(String query) throws Exception {
    mockMvc.perform(get("/query")
            .queryParam("q", query))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(query, false));
  }
}
