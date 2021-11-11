package net.brunomendola.querity.spring.data.mongodb.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository
    extends net.brunomendola.querity.test.domain.PersonRepository<Person, Long>, MongoRepository<Person, Long> {
}
