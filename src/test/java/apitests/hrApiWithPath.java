package apitests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.baseURI;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class hrApiWithPath {

    @BeforeClass
    public void beforeclass(){

        baseURI= ConfigurationReader.get("hr_api_url");
    }

    @Test
    public void getCountriesWithPath(){

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"region_id\":2}")
                .when().get("/countries");

        assertEquals(response.statusCode(),200);

        //1- print values of
        //    "hasMore": false,
        //    "limit": 25,
        //    "offset": 0,
        //    "count": 5
        // for these -->no array in json body so we can use directly key and value to get them
        System.out.println("response.path(\"limit\") = " + response.path("limit"));  //25
        System.out.println("response.path(\"hasMore\") = " + response.path("hasMore"));

        //2-print for these item values in ARRAY specifically so here we use Gpath index
        // (.)-->to go child value and index--> start from 0 , if value in the array
        String firstCountryId = response.path("items.country_id[0]");
        System.out.println("firstCountryId = " + firstCountryId);

        String secondCountryName = response.path("items.country_name[1]");
        System.out.println("secondCountryName = " + secondCountryName);

        //get the href value of under the Canada
        //because href an element is in array as a list so we have to give index number even if we have one href in array
        String link2 =response.path("items.links[2].href[0]");
        System.out.println("link2 = " + link2);

        //EX-1 FOR REAL TEST CASES
        // get all countries -->are in the first array in json as list
        List<String> countryNames = response.path("items.country_name");
        System.out.println("countryNames = " + countryNames);

        //assert that all regions'ids are equal to 2
        List<Integer>regionsID = response.path("items.region_id");
        System.out.println("regionsID = " + regionsID);

        for (int id : regionsID) {
                assertEquals(id,2);

        }
    }

    //EX-2 FOR REAL TEST CASES
    // make sure we have only IT_PROG as a job_id
    @Test
    public void test2() {
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"job_id\":\"IT_PROG\"}")
                .when().get("/employees");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");
        //VERIFY old way we did before we learn real proper one
        assertTrue(response.body().asString().contains("IT_PROG"));

        //*VERIFY new
        // All job_id are the array elements in the items so we get it in list then verify
       List<String>jobIds = response.path("items.job_id");
        for (String jobId : jobIds) {
            System.out.println("jobId = " + jobId);
            assertEquals(jobId,"IT_PROG");
        }


    }
}
