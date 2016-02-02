package net.foobar.codetest.search;

import net.foobar.codetest.persistence.CustomerRepository;
import net.foobar.codetest.persistence.entities.Customer;
import net.foobar.codetest.rest.resources.CustomerResource;
import net.foobar.codetest.rest.resources.DuplicateResource;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Service class that provides the Business Logic for finding duplicate customers to the RestController.
 * <p>
 * Created by foobar on 1/30/16.
 */

@Service
public final class DuplicateCustomerFinder {
    @Autowired
    private CustomerRepository repository;

    @Autowired
    private CustomerIndex index;

    /**
     * Searches the index for duplicate customers, retrieves the database records for the matching ids,
     * and returns {@link Iterable} of {@link DuplicateResource}
     *
     * @param id Long id of the customer to find duplicates for.
     * @return {@link Iterable} of {@link DuplicateResource} objects.
     * @throws IOException
     * @throws ParseException
     */
    public final List<DuplicateResource> findDuplicateCustomersById(Long id) throws IOException, ParseException {
        Customer queryCustomer = repository.findOne(id);
        if (queryCustomer != null) {
            List<DuplicateResource> results = new ArrayList<>();
            index.searchForSimilarCustomers(queryCustomer).stream().forEach(customerSearchResult -> {
                Customer customer = repository.findOne(customerSearchResult.getId());
                if (customer != null) {
                    results.add(new DuplicateResource(CustomerResource.fromCustomer(customer), customerSearchResult.getScore()));
                }
            });
            return results;
        }
        return Collections.emptyList();
    }
}
