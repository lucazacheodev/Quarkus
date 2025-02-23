package it.lucadev;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusIntegrationTest;

@QuarkusIntegrationTest
class MyControllerIT {

    @Test
    void testCreateAndGetEntity() {

        given()
                .when()
                .get("/api/entity")
                .then()
                .statusCode(200);
    }
}