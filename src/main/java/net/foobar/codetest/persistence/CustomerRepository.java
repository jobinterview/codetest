package net.foobar.codetest.persistence;

import net.foobar.codetest.persistence.entities.Customer;
import org.springframework.data.repository.CrudRepository;

/**
 * A Spring Data repository that extends CrudRepository. It means to eliminate boilerplate persistence mapping code.
 * <p>
 * Created by foobar on 1/30/16.
 */

@SuppressWarnings("unchecked")
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    @Override
    Customer save(Customer customer);

    @Override
    void delete(Customer customer);

    @Override
    Customer findOne(Long aLong);
}
