package Day6_POJO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import  static org.hamcrest.Matchers.*;

public class HrPostRequest {
    @BeforeClass
    public void beforeclass() {

        baseURI = ConfigurationReader.get("hr_api_url");
    }
    @Test
    public void regionPost(){
        RegionPost regionPost = new RegionPost(116,"cool");

         given().log().all()
                 .accept(ContentType.JSON)
                 .and()
                 .contentType(ContentType.JSON)
                 .and()
                 .body(regionPost)
        .when()
                 .post("/regions/")
                 .then().log().all()
                 .statusCode(201)
                 .body("region_id",equalTo(116),
                         "region_name",equalTo("cool"));

    }
}
