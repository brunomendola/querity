package net.brunomendola.querity.test;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class CsvUtils {
  static <T> List<T> readCsv(String path, Class<T> entityClass) throws IOException {
    CsvSchema csvSchema = CsvSchema.emptySchema().withHeader();
    CsvMapper csvMapper = new CsvMapper();
    csvMapper.registerModule(new JavaTimeModule());
    MappingIterator<T> entitiesIterator = csvMapper.readerFor(entityClass)
        .with(csvSchema)
        .readValues(new ClassPathResource(path).getInputStream());
    return entitiesIterator.readAll();
  }
}
