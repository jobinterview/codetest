package net.foobar.codetest;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.foobar.codetest.rest.resources.CustomerResource;
import net.foobar.codetest.rest.resources.DuplicateResource;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Integration Tests for the {@link net.foobar.codetest.rest.DuplicateEndpoint}.
 *
 * Created by foobar on 1/30/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest(randomPort = true)
public class DuplicateEndpointIntegrationTest {
    protected final TestRestTemplate restTemplate = new TestRestTemplate();

    @Value("${local.server.port}")
    protected int port;

    private boolean isDatabaseInitialized;

    @Before
    public final void populateDatabase() throws IOException, URISyntaxException {
        if (!isDatabaseInitialized) {
            isDatabaseInitialized = true;

            final ObjectMapper mapper = new ObjectMapper();

            // Parse the mock.json from the test resources directory.
            Files.lines(Paths.get(DuplicateEndpointIntegrationTest.class.getResource("/mock.json").toURI())).forEach(line -> {
                try {
                    final HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    final HttpEntity<String> entity = new HttpEntity<>(line, headers);
                    final CustomerResource resource = mapper.readValue(line, CustomerResource.class);
                    restTemplate.postForLocation("http://localhost:" + this.port + "/customer", resource, CustomerResource.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Test
    public void testGetDuplicateCustomers() throws IOException {
        Long id = 5L;
        // Capture the generic type at runtime using {@link ParameterizedTypeReference}
        ResponseEntity<List<DuplicateResource>> res = restTemplate.exchange(
                URI.create("http://localhost:" + this.port + "/duplicate/customer/" + id),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<DuplicateResource>>() {});
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(25, res.getBody().size());
        assertEquals(id, res.getBody().get(0).getCustomer().getId());
    }
}
