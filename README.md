## Followup Project
Code I wrote for a follow-up to a job interview.

## Why I built the project this way
At my current job, we use mostly JEE for everything. Because Microservices are emerging as a popular way to build composable
distributed applicatons/systems, I thought it would be good to use some technologies that are driving Microservice development
in Java.

For the text analysis and searching / scoring, I decided to use Lucene again, a library I had briefly used in the past.

For ease of development and handover, I decided to run the application datastores completely in memory
(H2 for RDBMS, RAMDirectory for Lucene).

After these decisions I was ready to start developing.

#### List of technologies used in this project
- `Spring Boot` to allow easy bootstrapping of a self contained service (what they call a 'fat' jar file).
- `Spring Data JPA` instead of direct JPA to reduce the amoint of boilerplate persistence code to write.
- `Spring Web` as the framework for the RESTful plumbing and serialization/deserialization.
- `JSR 303 Bean Validation` for annotation based validation of submitted data (`@Validate` annotation in the RestController)
- `Lucene` to implement the probabalistic searching.

## How to run this project.
In this section I will describes the steps required to run this project. My IDE settings are absent because my home directory name
is stored in them, this would reveal my identity.

#### Pre-requisites
- `Gradle` (which will be downloaded by `gradlew` if it is missing on the system).
- When deploying in a VM at least 1GB of RAM is required due to all the in memory data.

#### Cloning and running
- Clone the project from github.
- Navigate to the project directory and run `./gradlew clean build bootRun`. This compiles the code, runs all the tests and finally
starts up a server running on `http://localhost:8080`. The application endpoints are at `/customer` and `/duplicate/customer`.

#### Odds and ends
- Under `src/test/resources` is a file called `mock.json`. This file contains the dataset I personally used for testing.
- Under `scripts` are several commandline scripts that use `curl` and `python -m json.tool` to run rest commands and pretty print the output.
All of these assume that the application is running on `http://localhost:8080`.
    - `load_mock_data.sh` loads the data into the in memory datastores.
    - `show_customer.sh`  prompts for a user ID and shows the data for that user ID.
    - `show_duplicates.sh` prompts for a user ID and uses the ID entered to query the server.
    - `delete_customer.sh` prompts for a user ID and deletes the ID entered.

## Post Mortem
Overall the implementation of this project went swiftly, and I was able to do most of it with a high degree of confidence.

Initially, I was sharing the JPA classes on the RESTController directly and this worked just fine.
Spring Boot even offers you away to go directly from the REST service into the database with nothing more than a few annotations.
However, I did not like the tight coupling between what's in the database and what's being presented by the REST service.
Additionally, because Validation Bean annotations look so similar to JPA annotations the end result was just a big mess.
I ended up separating the two into a JPA `Customer` and a REST `CustomerResource`
(the latter of which has a few methods for to/from conversion).

I knew Lucene would be great for the probabilistic matching of records and it did not disappoint, it is a great library.
I briefly played around with different tokenizers and analyzers to improve the search result. I even used a doublemetaphone
phonetic analyzer on some of the fields, but the results stayed remarkably similar, after this I decided to just use the
`StandardAnalyzer` for all fields.

I will note that even though for this proof of concept using Lucene directly worked out fine, for a production deployment
it would make more sense to use a 3rd party search server product (both Solr and ElasticSearch are based on Lucene).

Another observation is that the microservice architecture seems to lead to fairly sparse domain models.
I think this is to be expected with services that do relatively little processing.
Ultimately whichever service or webapp composes multiple microservices will have the burden of creating a structured
representation, be it through functional programming or OOP with a rich domain model.

During most of the development I used integration tests to check my work, they have good coverage but take a long time
to run. This makes me think they would hurt developer productivity in the long run and they wouldn't fit well in a
red -> green -> refactor workflow.

In the end, I feel confident in recommending a solution similar to this one to deal with a Record Linkage problem.

I would make the following changes if this was developed for a production system:

- Use a 3rd party search product (`ElasticSearch`, `Solr`).
- Make the concern of this service strictly CRUD of the customer information.
- Create a new service for searching/adding customers to the 3rd Party Search product.
- Register these services through something like [Netflix's Eureka](http://https://github.com/Netflix/eureka/), perhaps using [Spring Boot Netflix](http://cloud.spring.io/spring-cloud-netflix/spring-cloud-netflix.html)
- Add a way to expose API documentation (something like [Swagger](http://swagger.io/))
- Better usage of Java 8 language features.
