package day1;

import org.testng.annotations.Test;
import java.util.HashMap;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class HTTPRequestTests {

    int id;

    @Test(priority = 1)
    void getUsers() {
        given()
        .when()
            .get("https://reqres.in/api/users?page=2")
        .then()
            .statusCode(200)
            .body("page", equalTo(2))
            .log().all();
    }

    @Test(priority = 2)
    void createUser() {

        HashMap<String, String> hm = new HashMap<>();
        hm.put("name", "sahil");
        hm.put("job", "engineer");

        id = given()
                .contentType("application/json")
                .body(hm)
            .when()
                .post("https://reqres.in/api/users")
                .jsonPath().getInt("id");
    }

    @Test(priority = 3, dependsOnMethods = {"createUser"})
    void updateUser() {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("name", "sunny");
        hm.put("job", "teacher");

        given()
             .contentType("application/json")
             .body(hm)
        .when()
             .put("https://reqres.in/api/users/" + id)
        .then()
            .statusCode(200)
            .log().all();
    }

    @Test(priority = 4, dependsOnMethods = {"updateUser"})
    void deleteUser() {
        given()
        .when()
            .delete("https://reqres.in/api/users/" + id)
        .then()
            .statusCode(204)
            .log().all();
    }
}
