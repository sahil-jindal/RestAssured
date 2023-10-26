package day3;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class CookieDemoTests {

    @Test(priority = 1)
    void testCookies() {
        given()
        .when()
            .get("https://www.google.com")
        .then()
            .cookie("AEC", not(equalTo("Ackid1QEFeykR4UVmTACqVDbQ0ULYWlp2I1FUAHaGbd00wKwocNgn50id-g")))
            .log().all();
    }

    @Test(priority = 2)
    void getCookiesInfo() {
        Response body = given().when()
                .get("https://www.google.com");

        //String cookie_value = body.getCookie("AEC");
        //System.out.println(STR."Value of cookie AEC is \{cookie_value}");

        Map<String, String> cookies = body.getCookies();

        for(Map.Entry<String, String> cookie: cookies.entrySet()) {
            System.out.println(cookie.getKey() + " = " + cookie.getValue());
        }
    }
}
