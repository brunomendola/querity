package net.brunomendola.querity.spring.data.jpa.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository
    extends net.brunomendola.querity.test.domain.PersonRepository<Person, Long>, JpaRepository<Person, Long> {
}
