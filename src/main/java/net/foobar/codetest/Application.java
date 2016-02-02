package net.foobar.codetest;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * The entry class for a Spring Boot application.
 * <p>
 * Created by foobar on 1/30/16.
 */
@SuppressWarnings("SpringFacetCodeInspection")
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        new Application()
                .configure(new SpringApplicationBuilder(Application.class))
                .run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

}
