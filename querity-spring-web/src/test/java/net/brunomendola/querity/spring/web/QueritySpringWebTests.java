package net.brunomendola.querity.spring.web;

import org.junit.jupiter.api.Test;
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
   *
   * @throws Exception
   */
  @Test
  void givenJsonQueryAsQueryParameter_whenGetQuery_thenReturnTheSameQueryAsResponse() throws Exception {
    String filter = "{\"filter\":{\"logic\":\"AND\",\"conditions\":[{\"propertyName\":\"lastName\",\"operator\":\"EQUALS\",\"value\":\"Skywalker\"}]}}";
    mockMvc.perform(get("/query")
            .queryParam("q", filter))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(filter, false));
  }
}
