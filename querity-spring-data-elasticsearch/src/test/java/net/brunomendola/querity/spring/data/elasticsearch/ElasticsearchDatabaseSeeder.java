package net.brunomendola.querity.spring.data.elasticsearch;

import net.brunomendola.querity.spring.data.elasticsearch.domain.Person;
import net.brunomendola.querity.spring.data.elasticsearch.domain.PersonRepository;
import net.brunomendola.querity.test.DatabaseSeeder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElasticsearchDatabaseSeeder extends DatabaseSeeder<Person> {

  private final PersonRepository personRepository;

  public ElasticsearchDatabaseSeeder(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  @Override
  protected Class<Person> getEntityClass() {
    return Person.class;
  }

  @Override
  protected void saveEntities(List<Person> entities) {
    personRepository.saveAll(entities);
  }

}
