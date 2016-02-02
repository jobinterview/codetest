package net.foobar.codetest.search;

import net.foobar.codetest.persistence.entities.Customer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides a facade for dealing with Lucene Indexes.
 * <p>
 * Created by foobar on 1/30/16.
 */

@Component
public class CustomerIndex {
    private final RAMDirectory directory = new RAMDirectory();
    private final Analyzer analyzer = new StandardAnalyzer();

    /**
     * Adds a Customer to the index
     *
     * @param customer the customer to add.
     * @throws IOException
     */
    public void addCustomer(final Customer customer) throws IOException {
        final IndexWriterConfig config = new IndexWriterConfig(analyzer);
        try (IndexWriter writer = new IndexWriter(directory, config)) {
            writer.addDocument(customer.toLuceneDocument());
        }
    }

    /**
     * Deletes documents from the index related to the supplied id.
     *
     * @param id {@link Long} of the customer to delete from the index.
     * @throws IOException
     */
    public void deleteCustomerById(final Long id) throws IOException {
        final IndexWriterConfig config = new IndexWriterConfig(analyzer);
        try (IndexWriter writer = new IndexWriter(directory, config)) {
            writer.deleteDocuments(new Term("id", id.toString()));
        }
    }

    /**
     * Updates the document for the supplied customer
     *
     * @param customer {@link Customer} of the customer to be updated.
     * @throws IOException
     */
    public void updateCustomer(final Customer customer) throws IOException {
        final IndexWriterConfig config = new IndexWriterConfig(analyzer);
        try (IndexWriter writer = new IndexWriter(directory, config)) {
            writer.updateDocument(new Term("id", String.valueOf(customer.getId())), customer.toLuceneDocument());
        }
    }

    /**
     * Searches the index for other customers similar to the supplied customer
     *
     * @param customer the customer we want to find similar customers for
     * @return List of {@link CustomerSearchResult} containing id and score of similar matches.
     * @throws ParseException Thrown when an issue occurs parsing the Lucene Query String.
     * @throws IOException    Occurs if the Lucene index cannot be opened.
     */
    public List<CustomerSearchResult> searchForSimilarCustomers(Customer customer) throws ParseException, IOException {
        // The following looks kind of messy, it creates a query string used by lucene to execute a search on all fields.
        final String queryString = "email:" + customer.getEmail() + "^4" + ' ' + // boost score when email matches.
                "companyName:" + customer.getCompanyName() + '~' + ' ' + // fuzzy name search
                "firstName:" + customer.getFirstName() + '~' + ' ' +
                "lastName:" + customer.getLastName() + '~' + ' ' +
                "address:" + customer.getAddress() + ' ' +
                "city:" + customer.getCity() + '~' + ' ' +
                "state:" + customer.getState() + '~' + ' ' +
                "country:" + customer.getCountry() + '~' + ' ' +
                "zipCode:" + customer.getZipCode() + '~';

        try (IndexReader reader = DirectoryReader.open(directory)) {
            final Query query = new QueryParser("email", analyzer).parse(queryString);
            final IndexSearcher searcher = new IndexSearcher(reader);
            final TopDocs docs = searcher.search(query, 25);
            final List<CustomerSearchResult> result = new ArrayList<>();
            for (ScoreDoc doc : docs.scoreDocs) {
                result.add(new CustomerSearchResult(Long.valueOf(searcher.doc(doc.doc).get("id")), doc.score));
            }
            return result;
        }
    }
}
