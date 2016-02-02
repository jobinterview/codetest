package net.foobar.codetest.rest.resources;

import net.foobar.codetest.persistence.entities.Customer;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * CustomerResource is a Bean used for REST communication using JSR 303 Bean Validation annotations.
 * These annotations are enforced by the RestController Endpoints.
 * <p>
 * Created by foobar on 1/30/16.
 */

public final class CustomerResource {
    private Long id;

    @NotNull
    @Size(min = 2, max = 255)
    private String companyName;

    @NotNull
    @Size(min = 2, max = 32)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 32)
    private String lastName;

    @NotNull
    @Email
    private String email;

    @Size(min = 2, max = 1024)
    private String address;

    @Size(min = 2, max = 64)
    private String city;

    @Size(min = 2, max = 64)
    private String state;

    @Size(min = 2, max = 48)
    private String country;

    @Size(min = 2, max = 16)
    private String zipCode;

    /**
     * Helper method to create a CustomerResource from a {@link Customer}
     *
     * @param customer the Customer we want to convert.
     * @return a new {@link CustomerResource}
     */
    public static CustomerResource fromCustomer(Customer customer) {
        CustomerResource resource = new CustomerResource();
        resource.setId(customer.getId());
        resource.setCompanyName(customer.getCompanyName());
        resource.setFirstName(customer.getFirstName());
        resource.setLastName(customer.getLastName());
        resource.setEmail(customer.getEmail());
        resource.setAddress(customer.getAddress());
        resource.setCity(customer.getCity());
        resource.setState(customer.getState());
        resource.setCountry(customer.getCountry());
        resource.setZipCode(customer.getZipCode());
        return resource;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Helper method to create a {@link Customer} that can be stored with
     * JPA
     *
     * @return a new {@link Customer}
     */
    public final Customer toCustomer() {
        Customer customer = new Customer();
        customer.setId(this.id);
        customer.setCompanyName(this.companyName);
        customer.setFirstName(this.firstName);
        customer.setLastName(this.lastName);
        customer.setEmail(this.email);
        customer.setAddress(this.address);
        customer.setCity(this.city);
        customer.setState(this.state);
        customer.setCountry(this.country);
        customer.setZipCode(this.zipCode);
        return customer;
    }

    /**
     * @return Returns a readable string representation of a {@link CustomerResource}
     */
    @Override
    public final String toString() {
        return "CustomerResource{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
