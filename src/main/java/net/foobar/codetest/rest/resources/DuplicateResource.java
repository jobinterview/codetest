package net.foobar.codetest.rest.resources;

/**
 * DuplicateResource is a Bean used for communication by {@link net.foobar.codetest.rest.DuplicateEndpoint}.
 * <p>
 * Created by foobar on 1/31/16.
 */

public final class DuplicateResource {
    private CustomerResource customer;
    private Float score;

    public DuplicateResource() {
    }

    public DuplicateResource(final CustomerResource resource, final Float score) {
        this.customer = resource;
        this.score = score;
    }

    public final CustomerResource getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerResource customer) {
        this.customer = customer;
    }

    public final Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    /**
     * @return Returns a readable string representation of a {@link DuplicateResource}
     */
    @Override
    public String toString() {
        return "DuplicateResource{" +
                "customer=" + customer +
                ", score=" + score +
                '}';
    }
}
