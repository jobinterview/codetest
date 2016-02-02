package net.foobar.codetest.rest;

import net.foobar.codetest.persistence.CustomerRepository;
import net.foobar.codetest.persistence.entities.Customer;
import net.foobar.codetest.rest.resources.CustomerResource;
import net.foobar.codetest.search.CustomerIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;

/**
 * CustomerEndpoint is a Spring {@link RestController} offering CRUD functionality for Customer records.
 * Validation of input is handled by the Validation Bean Syntax in the {@link CustomerResource} class.
 * Created by foobar on 1/30/16.
 */

@RestController
class CustomerEndpoint {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private CustomerIndex index;

    /**
     * Creates a new customer by saving it in the database and adding it to the search index.
     *
     * @param resource {@link CustomerResource} automatically deserialized by Spring from a request to this endpoint.
     * @param request  {@link HttpServletRequest} received by this endpoint.
     * @return a {@link ResponseEntity} with the URI to the newly created resource in the Location header.
     * @throws IOException
     */
    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    public final
    @ResponseBody
    ResponseEntity<?> createCustomer(@Valid @RequestBody final CustomerResource resource,
                                     final HttpServletRequest request) throws IOException {
        resource.setId(null); // only new resources here.
        final Customer result = repository.save(resource.toCustomer());
        index.addCustomer(result);
        final URI location = URI.create(request.getRequestURL().append('/').append(result.getId()).toString());
        return ResponseEntity.created(location).build();
    }


    /**
     * Updates an existing customer by saving it to the database and updating the search index.
     *
     * @param resource a {@link CustomerResource} automatically deserialized by Spring from a request.
     * @return {@link ResponseEntity} with HttpStatus 204 NO_CONTENT status indicating success.
     * Or HttpStatus 404 NOT_FOUND if target entity does not exist.
     * @throws IOException
     */
    @RequestMapping(value = "/customer/{id}", method = RequestMethod.PUT)
    public final
    @ResponseBody
    ResponseEntity<?> updateCustomer(@PathVariable final Long id,
                                     @Valid @RequestBody final CustomerResource resource) throws IOException {
        if (repository.exists(id)) {
            resource.setId(id);
            final Customer result = repository.save(resource.toCustomer());
            System.out.println(result);
            index.updateCustomer(result);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Retrieves an existing customer by Id.
     *
     * @param id {@link Long} id of the customer we're trying to retrieve.
     * @return {@link CustomerResource} that Spring will serialize to JSON.
     */
    @RequestMapping(value = "/customer/{id}", method = RequestMethod.GET)
    public final ResponseEntity<?> getCustomer(@PathVariable final Long id) {
        final Customer customer = repository.findOne(id);
        return customer != null ? ResponseEntity.ok(CustomerResource.fromCustomer(customer)) : ResponseEntity.notFound().build();
    }

    /**
     * Deletes an existing customer from the database and from the index.
     *
     * @param id {@link Long} id of the customer we're trying to delete.
     * @return {@link ResponseEntity} with HTTP Status ok.
     */
    @RequestMapping(value = "/customer/{id}", method = RequestMethod.DELETE)
    public final ResponseEntity<?> deleteCustomer(@PathVariable final Long id) throws IOException {
        repository.delete(id);
        index.deleteCustomerById(id);
        return ResponseEntity.ok().build();
    }
}
