package Day6_POJO;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
public class Pojo_deserialize {

     //we converted(de-serialized) json body to java collection (list or map)
     //Here, We converts (de-serialized) json to java classes then used that class to get value
    //*number of class is based on object number in the the json. for ex Regions become 5 classes,
    //basically if your variable has json object you have/create class for it
    @Test
    public void oneSpartanPojo(){
        baseURI= ConfigurationReader.get("spartan_api_url");

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", "192")
                .when().get("/api/spartans/{id}");

        assertEquals(response.statusCode(),200);

        //JSON to POJO --> de serialize(convert) to java custom class
        //JSON to Our Spartan class object

        Spartan spartan292 = response.body().as(Spartan.class);
        System.out.println(spartan292);

        System.out.println("spartan15.getName() = " + spartan292.getName());
        System.out.println("spartan15.getPhone() = " + spartan292.getPhone());

        //assertion
        assertEquals(spartan292.getId(),"292");
        assertEquals(spartan292.getName(),"Meta");

    }
    @Test
    public void regionToPojo(){
        baseURI = ConfigurationReader.get("hr_api_url");

        Response response = when().get("/regions");

        assertEquals(response.statusCode(),200);

        //JSON to POJO
        Regions regions = response.body().as(Regions.class);

        System.out.println("regions.getHasMore() = " + regions.getHasMore());
        System.out.println("regions.getCount() = " + regions.getCount());

        System.out.println(regions.getItems().get(1).getRegionId());
        System.out.println(regions.getItems().get(1).getRegionName());

        //regions.getItems()--> is pointing the list so we can put it to list and use it easily
        List<Item> items = regions.getItems();
        System.out.println(items.get(4).getRegionId());

    }
   //Before as(map/list/className.class) we used to do it manually. Old part we do not do it anymore /here, No api
    @Test
    public void gson_example(){

        //JSON to Java collections or Pojo-->De-serialization

        String myJsonData = "{\n" +
                "    \"id\": 192,\n" +
                "    \"name\": \"Meta\",\n" +
                "    \"gender\": \"Female\",\n" +
                "    \"phone\": 1938695106\n" +
                "}";

        Gson gson=new Gson();

        Map<String,Object> map = gson.fromJson(myJsonData, Map.class);
        System.out.println(map);

        Spartan spartan292 = gson.fromJson(myJsonData,Spartan.class);
        System.out.println(spartan292);

        //-----------------SERIALIZATION--------------

        Spartan spartanEU = new Spartan(200, "Mike","Male",123123123); //here we just created a java data from cons

        String jsonSpartanEU = gson.toJson(spartanEU); //takes java info put the json format

        System.out.println(jsonSpartanEU);

    }
}
