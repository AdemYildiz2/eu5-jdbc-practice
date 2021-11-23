package Day8;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
import  static org.hamcrest.Matchers.*;

public class JsonSchemaValidationDemo {

    @BeforeClass
    public void beforeclass() {

        baseURI = ConfigurationReader.get("spartan_api_url");
    }

    @Test
    public void JsonSchemaValidationForSpartan(){
        given()
                .accept(ContentType.JSON)
                .and()
                .pathParam("id",41)
                .and()
        .when()
                .get("/api/spartans/{id}")
        .then()
                .statusCode(200)
                .and()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SingleSpartanSchema.json"));
    }

}
