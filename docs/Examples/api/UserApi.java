package examples.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UserApi {

    private final String baseUrl;

    public UserApi(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Response createUser(String payload) {
        return RestAssured.given()
                .baseUri(baseUrl)
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/users");
    }

    public Response getUser(String id) {
        return RestAssured.given()
                .baseUri(baseUrl)
                .get("/users/" + id);
    }
}
