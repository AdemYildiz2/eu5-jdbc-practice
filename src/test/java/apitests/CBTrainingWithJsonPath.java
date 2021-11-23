package apitests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;


public class CBTrainingWithJsonPath {

    @BeforeClass
    public void beforeclass(){

        baseURI = ConfigurationReader.get("cbt_api_url");
    }

    @Test
    public void test1(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", "21602")
                .when().get("/student/{id}");

        //verify status code
        Assert.assertEquals(response.statusCode(),200);

        //assign response to jsonpath
        JsonPath jsonPath = response.jsonPath();

        //get values from jsonpath
        //if we don't get all first name as a list then we need to specify with index
        String firstName = jsonPath.getString("students.firstName[0]");
        System.out.println(firstName);

        String lastName = jsonPath.getString("students.lastName[0]");
        System.out.println(lastName);

        //get phone number-->Here phone is NOT in the array so no needs index to get value
        String phoneNumber = jsonPath.getString("students.contact[0].phone");
        System.out.println(phoneNumber);

        //get me city and zip code, do assertion
        String city = jsonPath.getString("students.company[0].address.city");
        System.out.println(city);
        assertEquals(city,"Chicago");

        int zipCode = jsonPath.getInt("students.company[0].address.zipCode");
        System.out.println(zipCode);
        assertEquals(zipCode,60606);

        //EXTRA-differences Between path and jsonpath
        //1-difference
        //-->jsonpath does NOT fail even though it is array list it casted to String
        String firstName2 = jsonPath.getString("students.firstName");
        System.out.println("firstName2 = " + firstName2); //[Vera]

        //because of array response.path FAILS it says ArrayList cannot be cast to java.lang.String
        String firstName3 = response.path("students.firstName");
        System.out.println("firstName3 = " + firstName3);

        //2-difference
        //jsonPath does not fail even though it is integer it casted to string
        String zipCode2 = jsonPath.getString("students.company[0].address.zipCode");
        System.out.println("zipCode2 = " + zipCode2); //60606

        //response.path FAILS it says Integer cannot be cast to java.lang.String
        String zipCode3 = response.path("students.company[0].address.zipCode");
        System.out.println("zipCode3 = " + zipCode3);

    }
}
