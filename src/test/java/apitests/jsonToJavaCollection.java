package apitests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class jsonToJavaCollection {
    //converting jsonbody to java side is called de-serialization
    @BeforeClass
    public void beforeclass(){

        baseURI = ConfigurationReader.get("spartan_api_url");
    }

    //Ex- for one spartan we use just one map
    @Test
    public void SpartanToMap(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", "15")
                .when().get("/api/spartans/{id}");

        assertEquals(response.statusCode(),200);

        //We will convert(de-serialize) json response to java map,
        // for these we need a library(Gson or Jackson)-->(it may be called object mapper, json parser,databinding library..etc)
        Map<String, Object> jsonDataMap = response.body().as(Map.class);
        System.out.println("jsonDataMap = " + jsonDataMap);

        String name = (String) jsonDataMap.get("name"); //it needs casting from object to string level
        assertEquals(name,"Meta");

        //getting phone properly we need creating object and print,
        BigDecimal phone = new BigDecimal(String.valueOf((jsonDataMap.get("phone"))));

        System.out.println("phone = " + phone);
    }
    //Ex- for all spartan so we use list of map
    @Test
    public void allSpartansToListOfMap(){

        Response response = given().accept(ContentType.JSON)
                .when().get("/api/spartans");

        assertEquals(response.statusCode(),200);

        //we need to de-serialize(convert) JSON response to List of Maps
        List<Map<String,Object>> allSpartanList = response.body().as(List.class);
        System.out.println(allSpartanList);

        //print first name of second spartan
        System.out.println(allSpartanList.get(1).get("name"));

        //save spartan 3 in a map
        Map<String,Object>spartan3 = allSpartanList.get(2);

        System.out.println(spartan3);

    }

    //in this example our json body includes key and value structure(map) and our value is arrays,boolean and some integers
    // pay attention!! our items key has another list of map as a value in its array so we put it in to list
        /*
    "items": [
           { "region_id": 1,
             "region_name": "Europe",}
           {
            "region_id": 2,
            "region_name": "Americas",}
    ],
    "hasMore": false,
    "limit": 25,
    "offset": 0,
    "count": 6,
    "links": [
    ]
         */

    @Test
    public void regionToMap(){
        baseURI = ConfigurationReader.get("hr_api_url");

        Response response = when().get("/regions");

        assertEquals(response.statusCode(),200);

        //we de-serialize JSON response to map
        Map<String, Object>regionMap = response.body().as(Map.class);

        System.out.println(regionMap.get("count")); //6       ->one value
        System.out.println(regionMap.get("hasMore")); //false ->one value
        System.out.println(regionMap.get("items"));//[....]->more than one value it has another list of map inside(array of json objects)

        //our items key has another list of map as a value in its array so we put it in to list
        List<Map<String,Object>> itemList = (List<Map<String, Object>>) regionMap.get("items"); // it is casted object to list of map, just like object to string

        //print first region name
        System.out.println(itemList.get(0).get("region_name"));
    }
    //REMEMBER! IF no array no need to use list so firs ex we did not use list
}
