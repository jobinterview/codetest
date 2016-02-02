package net.foobar.codetest.rest;

import net.foobar.codetest.rest.resources.DuplicateResource;
import net.foobar.codetest.search.DuplicateCustomerFinder;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Currently offers a single endpoint used for retrieving Duplicate Customer records.
 * <p>
 * Created by foobar on 1/31/16.
 */

@RestController
class DuplicateEndpoint {

    @Autowired
    private DuplicateCustomerFinder finder;

    /**
     * @param id the Id of the customer to find duplicates for.
     * @return a {@link Iterable} {@link DuplicateResource} objects.
     * @throws IOException
     * @throws ParseException
     */
    @RequestMapping(value = "/duplicate/customer/{id}", method = RequestMethod.GET)
    public final ResponseEntity<List<DuplicateResource>> getDuplicateCustomers(@PathVariable final Long id) throws IOException, ParseException {
        return new ResponseEntity<>(finder.findDuplicateCustomersById(id), HttpStatus.OK);
    }
}
