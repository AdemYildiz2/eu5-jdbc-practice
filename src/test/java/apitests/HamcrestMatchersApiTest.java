package apitests;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import  static org.hamcrest.Matchers.*;
public class HamcrestMatchersApiTest {
    /*
          given accept type is Json
          And path param id is 15
          When user sends a get request to spartans/{id}
          Then status code is 200
          And content type is Json
          And json data has following
              "id": 15,
              "name": "Meta",
              "gender": "Female",
              "phone": 1938695106
           */

    //HamcrestMatchers is very powerful library for assertion working with restAssured(it is inside the resAssured)
    //we use methods of HamcrestMatchers to verify, here no need Junit and TestNG assertion that we used for path or jsonpath
    //directly  request and response are in the same statement.Basically HERE ,we are CHANNING RESTASSURED METHODS.
    @Test
    public void oneSpartanWithHamcrest() {
        given().accept(ContentType.JSON)
                .and().pathParam("id",15).
                when().get("http://54.237.100.89:8000/api/spartans/{id}")
                .then().statusCode(200)
                .and().assertThat().contentType(equalTo("application/json"))
                .and().assertThat().body("id",equalTo(15),
                "name",equalTo("Meta"),
                "gender",equalTo("Female"),
                "phone",equalTo(1938695106));

           //.assertThat() is optional you can remove
    }


    @Test
    public void teacherData(){
        given().accept(ContentType.JSON)
                .and().pathParam("id",10774)
                .when().log().all().get("http://api.cybertektraining.com/teacher/{id}")
                .then().statusCode(200)
                .and().assertThat().contentType(equalTo("application/json;charset=UTF-8"))
                .and().header("Content-Length",equalTo("295"))
                .and().header("Connection","Keep-Alive")
                .and().header("Date", notNullValue())
                .and().body("teachers.firstName[0]",equalTo("Florine"),
                "teachers.lastName[0]",equalTo("Hickle"),
                "teachers.gender[0]",equalTo("Male"))
                .log().all();

        //if you want to analyze
        //log().all() --> can be written at the and off respond to get all responds and see on the console
        // OR
        //log().all() --> can be written right before get() to get all requests and see on the console

    }

    //EX-1 about the list
    // Verify Alexander and Marteen in that list,(here basically we do verifying multiple inputs/values in a collection)
    @Test
    public void teachersWithDepartments(){
        given().accept(ContentType.JSON)
                .and().pathParam("name","Computer")
                .when().get("http://api.cybertektraining.com/teacher/department/{name}")
                .then().statusCode(200)
                .and().contentType(equalTo("application/json;charset=UTF-8"))
                .and().body("teachers.firstName",hasItems("Alexander","Marteen"));




    }
}