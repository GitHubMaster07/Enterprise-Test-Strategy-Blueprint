package examples.contracts;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.model.RequestResponsePact;

import org.junit.Rule;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class UserContractTest {

    @Rule
    public PactProviderRuleMk2 mockProvider =
            new PactProviderRuleMk2("UserService", "localhost", 8080, this);

    @Pact(consumer = "UserClient")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        return builder
                .uponReceiving("Get user by ID")
                .path("/users/123")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(new PactDslJsonBody()
                        .stringType("id", "123")
                        .stringType("name", "Mock User")
                        .stringType("email", "mock@example.com"))
                .toPact();
    }

    @Test
    @PactVerification
    public void verifyContract() {
        given()
                .baseUri("http://localhost:8080")
                .get("/users/123")
                .then()
                .statusCode(200);
    }
}
