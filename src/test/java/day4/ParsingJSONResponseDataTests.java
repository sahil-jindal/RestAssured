package day4;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class ParsingJSONResponseDataTests {

    //@Test(priority = 1)
    void testJsonResponse() {

        // Approach 1

        /*
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("http://localhost:3000/store")
        .then()
            .statusCode(200)
            .header("Content-Type", "application/json; charset=utf-8")
            .body("book[3].title", equalTo("The Lord of the Rings"));
         */

        // Approach 2
        Response res = given()
                            .contentType(ContentType.JSON)
                        .when()
                            .get("http://localhost:3000/store");

        Assert.assertEquals(res.getStatusCode(), 200);
        Assert.assertEquals(res.getHeader("Content-Type"), "application/json; charset=utf-8");

        Assert.assertEquals(res.jsonPath().getString("book[3].title"), "The Lord of the Rings");
    }

    @Test(priority = 2)
    void testJsonResponseBodyData() {
        Response res = given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:3000/store");

        /*
        Assert.assertEquals(res.getStatusCode(), 200);
        Assert.assertEquals(res.getHeader("Content-Type"), "application/json; charset=utf-8");

        String bookname = res.jsonPath().getString("book[3].title");
        Assert.assertEquals(bookname, "The Lord of the Rings");
        */

        // validation of a book title using JSON
        JSONObject jo = new JSONObject(res.asString());
        JSONArray books = jo.getJSONArray("book");

        boolean status = false;

        for (int i = 0; i < books.length(); i++) {
            String bookName = books.getJSONObject(i).getString("title");
            if(bookName.equals("The Lord of the Rings")) {
                status = true;
                break;
            }
        }

        Assert.assertTrue(status);

        // validate total book price ---------- validation 2

        float totalPrice = 0;

        for (int i = 0; i < books.length(); i++) {
            totalPrice += books.getJSONObject(i).getFloat("price");
        }

        Assert.assertEquals(totalPrice, 526);
    }
}