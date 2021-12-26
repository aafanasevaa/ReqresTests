package aafanasyevaa;

import io.restassured.RestAssured;

import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.core.Is.is;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ApiTests {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    @DisplayName("LIST <RESOURCE>")
    public void getResourceList() {
        given()
                .when()
                .get("api/unknown")
                .then()
                .statusCode(200)
                .body("total", is(12));
    }

    @Test
    @DisplayName("SINGLE USER NOT FOUND")
    public void UserNotFoundTest() {
        given()
                .when()
                .get("api/users/23")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("CREATE A USER")
    public void CreateUser() {
        given()
                .contentType(JSON)
                .body("{ \"name\": \"morpheus\", \"job\": \"leader\" }")
                .when()
                .post("api/users")
                .then()
                .statusCode(201)
                .body("name", is("morpheus"));
    }

    @Test
    @DisplayName("REGISTER - UNSUCCESSFUL")
    public void UnsuccessfulRegister() {
        given()
                .contentType(JSON)
                .body("{ \"email\": \"sydney@fife\" }")
                .when()
                .post("api/register")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    @DisplayName("DELETE")
    public void deleteUser() {
        given()
                .when()
                .delete("api/users/2")
                .then()
                .statusCode(204);
    }
}

