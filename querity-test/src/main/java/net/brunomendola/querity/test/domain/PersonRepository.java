package net.brunomendola.querity.test.domain;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface PersonRepository<T extends Person, ID> extends PagingAndSortingRepository<T, ID> {
}
