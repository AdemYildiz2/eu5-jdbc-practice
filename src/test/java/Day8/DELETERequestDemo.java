package Day8;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.Random;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import  static org.hamcrest.Matchers.*;

public class DELETERequestDemo {

    @BeforeClass
    public void beforeclass() {

        baseURI = ConfigurationReader.get("spartan_api_url");
    }

    @Test
    public void test1(){
        Random rd = new Random();
        int idToDelete = rd.nextInt(200)+1;
        System.out.println("This spartan id: "+ idToDelete+ " will be deleted.say good bye!");

        given().
                pathParam("id",idToDelete)
        .when()
                .delete("/api/spartans/{id}")
        .then()
                .statusCode(204).log().all();
    }
}
