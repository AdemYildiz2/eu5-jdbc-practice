package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class SpartanGetRequest {

    String spartanUrl = "http://54.237.100.89:8000";

    @Test
    public void test1(){

        Response response = given().get(spartanUrl+"/api/spartans");

        System.out.println(response.getStatusCode());

        response.prettyPrint();

    }
    /* TASK
    When users send a get request to /api/spartans/3
    Then status code should be 200
    And content type should be application/json;charset=UTF-8
    and json body should contain Fidole
     */

    @Test
    public void test2(){
        Response response = when().get(spartanUrl+"/api/spartans/3");

        //verify status code
        Assert.assertEquals(response.getStatusCode(),200);

        //verify content type
        Assert.assertEquals(response.contentType(),"application/json;charset=UTF-8");

        //verify fidole
       Assert.assertTrue(response.body().asString().contains("Fidole"));

    }
    /* TEST
        Given no headers provided
        When Users sends GET request to /api/hello
        1- Then response status code should be 200
        2- And Content type header should be “text/plain;charset=UTF-8”
        3- And header should contain date
        4- And Content-Length should be 17
        5- And body should be “Hello from Sparta"
        */

    @Test
    public void helloTest(){
        //request
        Response response = when().get(spartanUrl + "/api/hello");

        //1- verify status code
        Assert.assertEquals(response.getStatusCode(),200);

        //2- verify content-type
        Assert.assertEquals(response.contentType(),"text/plain;charset=UTF-8");

        //3- verify we have headers named date(it checks if we have that header in the response)
         Assert.assertTrue(response.headers().hasHeaderWithName("Date"));

         //We can get any header passing as a key as well
        System.out.println(response.header("Content-Length"));
        System.out.println(response.header("Date"));

        //4- Assert content-length is 17
        Assert.assertEquals(response.header("Content-Length"), "17");

        //5-verify hello from sparta
        Assert.assertEquals(response.getBody().asString(),"Hello from Sparta");

    }
}
