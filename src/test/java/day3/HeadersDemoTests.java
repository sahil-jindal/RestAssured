package day3;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
public class HeadersDemoTests {

    @Test(priority = 1)
    void testHeaders() {
        given()
        .when()
            .get("https://www.google.com")
        .then()
            .header("Content-Type", "text/html; charset=ISO-8859-1")
            .header("Content-Encoding", "gzip")
            .header("Server", "gws");
    }

    @Test(priority = 2)
    void getHeadersInfo() {
        Response body = given().when()
                .get("https://www.google.com");

//        String cookie_value = body.getHeader("Content-Type");
//        System.out.println(STR."Content-type is \{cookie_value}");

        Headers myheaders = body.getHeaders();

        for(Header myheader: myheaders) {
            System.out.println(myheader.getName() + " = " + myheader.getValue());
        }
    }
}
