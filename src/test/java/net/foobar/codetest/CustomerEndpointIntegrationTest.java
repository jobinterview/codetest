package net.foobar.codetest;

import net.foobar.codetest.rest.resources.CustomerResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.URI;

import static org.junit.Assert.*;

/**
 * Integration tests for the {@link net.foobar.codetest.rest.CustomerEndpoint}.
 *
 * Created by foobar on 1/30/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest(randomPort = true)
public class CustomerEndpointIntegrationTest {
    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Value("${local.server.port}")
    protected int port;


    @Test
    public void testCreateCustomer() throws Exception {
        CustomerResource resource = createCustomerResource();
        URI result = restTemplate.postForLocation("http://localhost:" + this.port + "/customer", resource, CustomerResource.class);

        assertTrue(result.toString().matches(".*\\/\\d+$")); // Assert that we received a Location with a new resource Id.
        assertNotNull(result);
    }

    @Test
    public void testCreateCustomerFailMissingField() throws Exception {
        CustomerResource resource = createCustomerResource();
        resource.setEmail(null);
        ResponseEntity response = restTemplate.postForEntity(URI.create("http://localhost:" + this.port + "/customer"), resource, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testCreateCustomerFailFieldToLong() throws Exception {
        CustomerResource resource = createCustomerResource();
        resource.setFirstName("JohnJohnJohnJohnJohnJohnJohnJohnJohn");
        ResponseEntity response = restTemplate.postForEntity(URI.create("http://localhost:" + this.port + "/customer"), resource, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testGetCustomer() throws Exception {
        CustomerResource resource = createCustomerResource();
        URI result = restTemplate.postForLocation("http://localhost:" + this.port + "/customer", resource);
        ResponseEntity<CustomerResource> response = restTemplate.getForEntity(result.toString(), CustomerResource.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resource.getEmail(), response.getBody().getEmail());
    }


    @Test
    public void testGetCustomerNotFound() throws Exception {
        ResponseEntity response = restTemplate.getForEntity("http://localhost:" + this.port + "/customer/1111111111", ResponseEntity.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        CustomerResource resource = createCustomerResource();
        URI result = restTemplate.postForLocation("http://localhost:" + this.port + "/customer", resource);
        restTemplate.delete(result);
        ResponseEntity response = restTemplate.getForEntity(result.toString(), ResponseEntity.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        CustomerResource resource = createCustomerResource();
        URI result = restTemplate.postForLocation("http://localhost:" + this.port + "/customer", resource);
        ResponseEntity<CustomerResource> response = restTemplate.getForEntity(result.toString(), CustomerResource.class);
        CustomerResource updated = response.getBody();
        updated.setFirstName("Jack");
        updated.setLastName("Sparrow");
        restTemplate.put("http://localhost:" + this.port + "/customer/" + updated.getId(), updated, CustomerResource.class);
        response = restTemplate.getForEntity(result.toString(), CustomerResource.class);
        assertEquals(response.getBody().getFirstName(), updated.getFirstName());
        assertEquals(response.getBody().getLastName(), updated.getLastName());
    }

    private static CustomerResource createCustomerResource() {
        CustomerResource resource = new CustomerResource();
        resource.setCompanyName("Doe Testing LLC.");
        resource.setFirstName("John");
        resource.setLastName("Doe");
        resource.setEmail("john@doe.com");
        resource.setAddress("221 Baker St.");
        resource.setCity("Somecity");
        resource.setState("Somestate");
        resource.setCountry("Somecountry");
        resource.setZipCode("12345");
        return resource;
    }
}
