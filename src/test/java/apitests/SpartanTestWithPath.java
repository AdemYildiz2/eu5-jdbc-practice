package apitests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
public class SpartanTestWithPath {

    @BeforeClass
    public void beforeClass(){

        baseURI="http://54.237.100.89:8000";

    }

     /*
   Given accept type is json
   And path param id is 10
   When user sends a get request to "api/spartans/{id}"
   Then status code is 200
   And content-type is "application/json;charset=UTF-8"
   And response payload values match the following:
           id is 10,
           name is "Lorenza",
           gender is "Female",
           phone is 3312820936
    */

    @Test
    public void getOneSpartan_path(){
        Response response = given().accept(ContentType.JSON)
                .when().pathParam("id", "10")
                .and().get("/api/spartans/{id}");


        assertEquals(response.contentType(),"application/json");
        assertEquals(response.statusCode(),200);

        //printing each key value in the json body/playload
        //we can use just path() OR we can use body().path() both correct
        System.out.println(response.path("id").toString());
        System.out.println(response.path("name").toString());
        System.out.println(response.body().path("gender").toString());
        System.out.println(response.body().path("phone").toString());

        //save json key values
        int id=response.path("id");
        String name = response.path("name");
        String gender = response.path("gender");
        long phone = response.path("phone");

        System.out.println("id= "+id);
        System.out.println("name= "+name);
        System.out.println("gender= "+gender);
        System.out.println("phone= "+phone);

        //assert one by one
        assertEquals(id,10);
        assertEquals(name,"Lorenza");
        assertEquals(gender,"Female");
        assertEquals(phone,3312820936l);

    }
    @Test
    public void getAllSpartanWithpath(){
        Response response = given().accept(ContentType.JSON)
                .when().get("/api/spartans");

        //verify status and content-type
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        //we can use index to get which spartan's info we want -->called Gpath syntax
        System.out.println(response.path("id[0]").toString()); //1 -->first spartan

        //index 0 start from first info of body/ index -1 start from last info
        //Here, Our spartans json body is in ARRAY format
        int firstId = response.path("id[0]");
        System.out.println("firstId = " + firstId);

        String firstName = response.path("name[0]");
        System.out.println("firstName = " + firstName);

        String firstNameOfLastSpartan = response.path("name[-1]");
        System.out.println("firstNameOfLastElement = " + firstNameOfLastSpartan);

        int idOfLastSpartan = response.path("id[-2]");
        System.out.println("idOfLastSpartan = " + idOfLastSpartan);


    }
}
