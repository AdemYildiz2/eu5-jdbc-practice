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

public class SpartanTestWithJsonPath {

    @BeforeClass
    public void beforeclass() {

        baseURI = ConfigurationReader.get("spartan_api_url");
    }

     /*
          Given accept type is json
          And path param spartan id is 11
          When user sends a get request to /spartans/{id}
         Then status code is 200
         And content type is Json
         And   "id": 11,
               "name": "Nona",
              "gender": "Female",
              "phone": 7959094216
    */

    @Test
    public void test1() {
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", "11")
                .when().get("api/spartans/{id}");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        //WAY-1 WITH PATH we already know
        //Verify id and name WITH PATH -->no array here just one spartan so directly we can get value
        int id = response.path("id");
        String name = response.path("name");

        Assert.assertEquals(id,11);
        Assert.assertEquals(name,"Nona");

        //WAY-2 WITH JSONPATH --> like path that is used with response but jsonPath is more powerful
        //Assign response to jsonpath, then use methods of jsonpath object
        JsonPath jsonPath = response.jsonPath();

        int idJson=jsonPath.getInt("id");
        String nameJson=jsonPath.getString("name");
        String genderJson = jsonPath.getString("gender");
        long phoneJson = jsonPath.getLong("phone");

        System.out.println("idJson = " + idJson);
        System.out.println("nameJson = " + nameJson);
        System.out.println("genderJson = " + genderJson);
        System.out.println("phoneJson = " + phoneJson);

        //verification
        assertEquals(idJson,"11");
        assertEquals(nameJson,"Nona");
        assertEquals(genderJson,"Female");
        assertEquals(phoneJson,7959094216l);
    }
}