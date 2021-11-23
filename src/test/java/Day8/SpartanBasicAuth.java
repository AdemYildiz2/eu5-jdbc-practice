package Day8;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import  static org.hamcrest.Matchers.*;

public class SpartanBasicAuth {
    @BeforeClass
    public void beforeclass() {

        baseURI = ConfigurationReader.get("spartan_api_url");
    }

    //Normally we can Not reach api directly we need authorization info
    //let's see how to sent thru restAssured our authorization info to api
     @Test
    public void test1(){
         given()
                 .accept(ContentType.JSON)
                 .and()
                 .auth().basic("admin","admin")   //gives permition
         .when()
                 .get("/api/spartans")
         .then().log().all()
                 .statusCode(200);
         //normally without auth().basic it should have given error 401--> Unauthorized
         //So we need to send authorization to api to so that we can reach till some point
    }
}
