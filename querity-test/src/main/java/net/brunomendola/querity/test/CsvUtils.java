package net.brunomendola.querity.test;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.brunomendola.querity.test.domain.Person;
import net.brunomendola.querity.test.jackson.PersonMixin;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class CsvUtils {
  static <T> List<T> readCsv(String path, Class<T> entityClass) throws IOException {
    CsvSchema csvSchema = CsvSchema.emptySchema().withHeader().withNullValue("");
    CsvMapper csvMapper = new CsvMapper();
    csvMapper.addMixIn(Person.class, PersonMixin.class);
    csvMapper.registerModule(new JavaTimeModule());
    MappingIterator<T> entitiesIterator = csvMapper.readerFor(entityClass)
        .with(csvSchema)
        .readValues(new ClassPathResource(path).getInputStream());
    return entitiesIterator.readAll();
  }
}
