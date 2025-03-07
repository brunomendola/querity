package net.brunomendola.querity.spring.data.elasticsearch.domain;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository
    extends net.brunomendola.querity.test.domain.PersonRepository<Person, String>, ElasticsearchRepository<Person, String> {
}
