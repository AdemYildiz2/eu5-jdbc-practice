package apitests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class hrApiWithJsonPath {

    @BeforeClass
    public void beforeclass(){

        baseURI= ConfigurationReader.get("hr_api_url");
    }
       //Using Gpath syntax
    @Test
    public void test1(){
        Response response = get("/countries"); //we can do without given

        //assign response to jsonpath
        JsonPath jsonPath= response.jsonPath();

        String secondCountryName = jsonPath.getString("items.country_name[1]");
        System.out.println("secondCountryName = " + secondCountryName);

        //get all country id
        List<String> countryIDs= jsonPath.getList("items.country_id");
        System.out.println(countryIDs);

        //Get all country name where their region id is equal to 2 --> we did this filter before, with query param
        //***But if we don't have any Filter functionality in my Api
        //we can do it with jsonPath and path method with Gpath syntax as well
        List<String> countryNameWithRegionId2= jsonPath.getList("items.findAll {it.region_id==2}.country_name");
        System.out.println(countryNameWithRegionId2);
        //here {it.region_id==2} like if iterator statement

    }
    @Test
    public void test2(){
        Response response = given().queryParam("limit", "107")
                .when().get("employees");

        JsonPath jsonPath = response.jsonPath();

        //get me all email of employees who is working as IT_PROG
        //here email and other key can be changed dynamically
        List<String> firstNames = jsonPath.getList("items.findAll {it.job_id==\"IT_PROG\"}.email");
        System.out.println(firstNames);

        //get me all firstname of employees who is making more than 10000
        List<String> firstNames2 = jsonPath.getList("items.findAll {it.salary > 10000}.first_name");
        System.out.println(firstNames2);

        //get me all firstname of employees who is making highest salary(for minimum we use 'min')
        String kingName = jsonPath.getString("items.max{it.salary}.first_name");
        System.out.println(kingName);
    }
}
