package day2;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/*
Different ways to create POST request body
1) HashMap
2) Org.Json
3) POJO classes (Plain Old Java Object)
4) JSON file data
*/

public class WaysToCreatePostRequestBodyTests {

    //@Test(priority = 1)
    void testPostUsingHashMap() {
        HashMap<String, Object> data = new HashMap<>();

        data.put("name", "Scott");
        data.put("location", "France");
        data.put("phone", "123456");

        String[] courses = {"C", "C++"};
        data.put("courses", courses);

        given()
            .contentType("application/json")
            .body(data)
        .when()
            .post("http://localhost:3000/students")
        .then()
            .statusCode(201)
            .body("name", equalTo("Scott"))
            .body("location", equalTo("France"))
            .body("phone", equalTo("123456"))
            .body("courses[0]", equalTo("C"))
            .body("courses[1]", equalTo("C++"))
            .header("Content-Type", "application/json; charset=utf-8")
            .log().all();

    }

    //@Test(priority = 1)
    void testPostUsingJsonLibrary() {
        JSONObject data = new JSONObject();

        data.put("name", "Scott");
        data.put("location", "France");
        data.put("phone", "123456");

        String[] courses = {"C", "C++"};
        data.put("courses", courses);

        given()
            .contentType("application/json")
            .body(data.toString())
        .when()
            .post("http://localhost:3000/students")
        .then()
            .statusCode(201)
            .body("name", equalTo("Scott"))
            .body("location", equalTo("France"))
            .body("phone", equalTo("123456"))
            .body("courses[0]", equalTo("C"))
            .body("courses[1]", equalTo("C++"))
            .header("Content-Type", "application/json; charset=utf-8")
            .log().all();
    }

    //@Test(priority = 1)
    void testPostUsingPojo() {
        Student data = new Student();

        data.setName("Scott");
        data.setLocation("France");
        data.setPhone("123456");

        String[] courses = {"C", "C++"};
        data.setCourses(courses);

        given()
            .contentType("application/json")
            .body(data)
        .when()
            .post("http://localhost:3000/students")
        .then()
            .statusCode(201)
            .body("name", equalTo("Scott"))
            .body("location", equalTo("France"))
            .body("phone", equalTo("123456"))
            .body("courses[0]", equalTo("C"))
            .body("courses[1]", equalTo("C++"))
            .header("Content-Type", "application/json; charset=utf-8")
            .log().all();
    }

    @Test(priority = 1)
    void testPostUsingJsonFile() throws FileNotFoundException {
        File f = new File(".\\body.json");
        FileReader fr = new FileReader(f);
        JSONTokener jt = new JSONTokener(fr);
        JSONObject data = new JSONObject(jt);

        given()
            .contentType("application/json")
            .body(data.toString())
        .when()
            .post("http://localhost:3000/students")
        .then()
            .statusCode(201)
            .body("name", equalTo("Scott"))
            .body("location", equalTo("France"))
            .body("phone", equalTo("123456"))
            .body("courses[0]", equalTo("C"))
            .body("courses[1]", equalTo("C++"))
            .header("Content-Type", "application/json; charset=utf-8")
            .log().all();
    }

    @Test(priority = 2)
    void testDelete() {
        given()
        .when()
            .delete("http://localhost:3000/students/4")
        .then()
            .statusCode(200);
    }
}
