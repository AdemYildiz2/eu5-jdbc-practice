package Day8;

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import  static org.hamcrest.Matchers.*;

public class PUTRequestDemo {

    @BeforeClass
    public void beforeclass() {

        baseURI = ConfigurationReader.get("spartan_api_url");
    }
    //PUT request:Update the existing records,data in API
    @Test
    public void test1(){
        //Create one map for the put request json body
        Map<String,Object> putRequestMap = new HashMap<>();
        putRequestMap.put("name","PutName");
        putRequestMap.put("gender","Male");
        putRequestMap.put("phone",1231231231l);

        given().log().all()
                .contentType(ContentType.JSON)    //no need accept type because we will NOT have json respond for this put request
                .and()
                .pathParam("id",80)
                .and()
                .body(putRequestMap).
        when()
                .put("/api/spartans/{id}")
                .then().log().all()
                .statusCode(204);

        //PUT request ENDED


        //IF we want to get request and verify we can do it easily. We have id,name...etc to do this

    }
    //PATCH request:Partial update of existing data
    @Test
    public void PatchTest(){
        //Create one map for the patch request json body
        Map<String,Object> patchRequestMap = new HashMap<>();
        patchRequestMap.put("name","JT");

        given().log().all()
                .contentType(ContentType.JSON)    //no need accept type because we will NOT have json respond for this patch request
                .and()
                .pathParam("id",80)
                .and()
                .body(patchRequestMap).
        when()
                .patch("/api/spartans/{id}")
                .then().log().all()
                .statusCode(204);

        //PUT request ENDED

        //IF we want to get request and verify we can do it easily. We have id,name...etc to do this

    }
}
