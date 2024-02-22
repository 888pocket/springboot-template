package com.example.webfullstack.auth.controller;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AuthControllerE2ETest {

    @Test
    public void createUser_EndToEndTest() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"name\": \"testUser\","
                        + "\"email\": \"test@example.com\","
                        + "\"password\": \"testPassword\"}")
                .post("/auth/signup")
                .then()
                .statusCode(200);
    }

    @Test
    public void readUser_EndToEndTest_UserExists() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .get("/auth/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("testUser"))
                .body("email", equalTo("test@example.com"));
    }

    @Test
    public void readUser_EndToEndTest_UserNotFound() {
        // 존재하지 않는 사용자 ID에 대한 요청
        RestAssured.given()
                .contentType(ContentType.JSON)
                .get("/auth/999")
                .then()
                .statusCode(404);
    }

    @Test
    public void updateUser_EndToEndTest() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"name\": \"updateUser\","
                        + "\"email\": \"update@example.com\","
                        + "\"password\": \"updatePassword\"}")
                .put("/auth/1")
                .then()
                .statusCode(200);
    }

    @Test
    public void deleteUser_EndToEndTest() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"name\": \"updateUser\","
                        + "\"email\": \"update@example.com\","
                        + "\"password\": \"updatePassword\"}")
                .delete("/auth/1")
                .then()
                .statusCode(200);
    }
}