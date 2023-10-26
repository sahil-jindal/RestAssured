package day3;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class PathAndQueryParameterTests {

    // https://reqres.in/api/users?page=2&id=5
    @Test
    void testQueryAndPathParameters() {
        given()
            .pathParam("mypath", "users")
            .queryParam("page", 2)
            .queryParam("id", 5)
        .when()
            .get("https://reqres.in/api/{mypath}")
        .then()
            .statusCode(200)
            .log().all();
    }
}
