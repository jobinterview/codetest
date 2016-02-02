package net.foobar.codetest.persistence.entities;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

import javax.persistence.*;

/**
 * JPA entity for Customer objects.
 * <p>
 * Created by foobar on 1/30/16.
 */

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 255)
    private String companyName;

    @Column(nullable = false, length = 32)
    private String firstName;

    @Column(nullable = false, length = 32)
    private String lastName;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = true, length = 1024)
    private String address;

    @Column(nullable = true, length = 64)
    private String city;

    @Column(nullable = true, length = 64)
    private String state;

    @Column(nullable = true, length = 48)
    private String country;

    @Column(nullable = true, length = 16)
    private String zipCode;

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
     * Helper method that creates a Lucene Document with all non null, non blank fields.
     *
     * @return Created Lucene Document.
     */
    public final Document toLuceneDocument() {
        Document doc = new Document();
        doc.add(new TextField("id", "" + this.getId(), Field.Store.YES));

        if (this.getCompanyName() != null && !this.getCompanyName().isEmpty()) {
            doc.add(new TextField("companyName", this.getCompanyName(), Field.Store.NO));
        }
        if (this.getFirstName() != null && !this.getFirstName().isEmpty()) {
            doc.add(new TextField("firstName", this.getFirstName(), Field.Store.NO));
        }
        if (this.getLastName() != null && !this.getLastName().isEmpty()) {
            doc.add(new TextField("lastName", this.getLastName(), Field.Store.NO));
        }
        if (this.getEmail() != null && !this.getEmail().isEmpty()) {
            doc.add(new TextField("email", this.getEmail(), Field.Store.NO));
        }
        if (this.getAddress() != null && !this.getAddress().isEmpty()) {
            doc.add(new TextField("address", this.getAddress(), Field.Store.NO));
        }
        if (this.getCity() != null && !this.getCity().isEmpty()) {
            doc.add(new TextField("city", this.getCity(), Field.Store.NO));
        }
        if (this.getState() != null && !this.getState().isEmpty()) {
            doc.add(new TextField("state", this.getState(), Field.Store.NO));
        }
        if (this.getCountry() != null && !this.getCountry().isEmpty()) {
            doc.add(new TextField("country", this.getCountry(), Field.Store.NO));
        }
        if (this.getZipCode() != null && !this.getZipCode().isEmpty()) {
            doc.add(new TextField("zipCode", this.getZipCode(), Field.Store.NO));
        }
        return doc;
    }

    /**
     * @return Returns a readable string representation of a {@link Customer}
     */
    @Override
    public String toString() {
        return "Customer{" +
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

