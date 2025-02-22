package it.lucadev;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.restassured.http.ContentType;

@QuarkusTest
@TestProfile(OracleTestProfile.class)
class MyControllerIT {

    @Test
    void testCreateAndGetEntity() {
        
        given()
                .contentType(ContentType.JSON)
                .body("{\"field\": \"Integration Test\"}")
                .when()
                .post("/api/entity")
                .then()
                .statusCode(200);

        given()
                .pathParam("id", 1)
                .when()
                .get("/api/entity/{id}")
                .then()
                .statusCode(200)
                .body("field", equalTo("Integration Test"));
    }
}