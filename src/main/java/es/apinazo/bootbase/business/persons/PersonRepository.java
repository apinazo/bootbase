package es.apinazo.bootbase.business.persons;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * Person {@link org.springframework.data.repository.Repository} with CRUD operations and.
 * <a href="https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#query-by-example">
 * queries by example</a>.
 *
 * {@link QueryByExampleExecutor} enables queries by example using {@link org.springframework.data.domain.Example}
 * and {@link org.springframework.data.domain.ExampleMatcher}.
 *
 * {@link PagingAndSortingRepository} allows paging and sorting capabilities to all CRUD methods.
 */
public interface PersonRepository extends
    JpaRepository<Person, Integer>,
    QueryByExampleExecutor<Person>,
    PagingAndSortingRepository<Person, Integer> {

    Person findByFirstName(String name);

}
