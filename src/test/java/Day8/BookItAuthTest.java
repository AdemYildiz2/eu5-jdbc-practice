package Day8;

import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import  static org.hamcrest.Matchers.*;

public class BookItAuthTest {

    @BeforeClass
    public void before() {

        baseURI = "https://cybertek-reservation-api-qa2.herokuapp.com";
    }
    String accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI1NyIsImF1ZCI6InN0dWRlbnQtdGVhbS1sZWFkZXIifQ.a_N9URDBPGOMcDdEVoaMHsJtk3jOnig0v0SCtSWcsGE";

    @Test
    public void getAllCampuses(){
        Response response = given().header("Authorization",accessToken).
                when().get("/api/campuses");

        response.prettyPrint();
        System.out.println(response.statusCode());
        //422 because first, we could not put any authorization for this api
        //after putting authorization in header then it works
    }
}
