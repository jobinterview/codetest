package net.foobar.codetest.search;

/**
 * Intermediate object for transferring Customer ID and it's Score.
 * <p>
 * Created by foobar on 1/30/16.
 */

class CustomerSearchResult {
    private final Long id;
    private final Float score;

    public CustomerSearchResult(Long id, Float score) {
        this.id = id;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public Float getScore() {
        return score;
    }
}
