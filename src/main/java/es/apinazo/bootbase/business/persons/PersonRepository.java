package es.apinazo.bootbase.business.persons;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * Person {@link org.springframework.data.repository.Repository} with CRUD operations and.
 * <a href="https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#query-by-example">
 * queries by example</a>.
 *
 * {@link QueryByExampleExecutor} enables queries by example using {@link org.springframework.data.domain.Example}
 * and {@link org.springframework.data.domain.ExampleMatcher}.
 */
public interface PersonRepository extends
    CrudRepository<Person, Integer>,
    QueryByExampleExecutor<Person> {
}
