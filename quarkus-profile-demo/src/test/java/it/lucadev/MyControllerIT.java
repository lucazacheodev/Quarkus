package it.lucadev;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(OracleTestProfile.class)
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