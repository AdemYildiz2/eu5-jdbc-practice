package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class simpleGetRequest {
    String hrUrl = "http://54.237.100.89:1000/ords/hr/regions";

    @Test
    public void test1(){
        //connection
        Response response = RestAssured.get(hrUrl);

        //print status code
        System.out.println(response.statusCode());

        //print the json body
        response.prettyPrint();
    }

    /*Test case/Scenario 1
    Given accept type is json
    When user sends get request to regions endpoint
    Then response status code must be 200
    and body is json format
     */

    //1st way
    @Test
    public void test2(){
        //request part is saved in response
        Response response = RestAssured.given().accept(ContentType.JSON)
                            .when().get(hrUrl);

        //verify response status code is 200
        Assert.assertEquals(response.getStatusCode(),200);

        //verify content-type is application/json
        System.out.println(response.contentType());

        Assert.assertEquals(response.contentType(),"application/json");
    }
    //2nd way
    @Test
    public void test3(){

         RestAssured.given().accept(ContentType.JSON)
                .when().get(hrUrl).then()
                 .assertThat().statusCode(200)
                 .and().contentType("application/json");

         //after then() part is the same as assertion part in test2
    }

     /*Test case/Scenario 2
    Given accept type is json
    When user sends get request to regions/2
    Then response status code must be 200
    and body is json format
    and response body contains Americas
     */

    @Test
    public void test4(){
        //request part is saved in response
        //we imported our restassured static so that we don't have to write each time (RestAssured.)
        Response response = given().accept(ContentType.JSON)
                .when().get(hrUrl+"/2");

        //verify status code
        Assert.assertEquals(response.getStatusCode(),200);

        //verify content-type
        Assert.assertEquals(response.contentType(),"application/json");

        //Verify body contains Americas
        Assert.assertTrue(response.body().asString().contains("Americas"));
    }
}
