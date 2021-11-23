package Day8;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class SSLTest {

    //some web sites has NO SSL certificate but you want to go on api test then we use relaxedHTTPSValidation() method
    @Test
    public void test1(){
        given()
                .relaxedHTTPSValidation()
                .when().get("https://untrusted-root.badssl.com/")
                .prettyPrint();


    }
    //some web sites has SSL certificate and if they need credentials so we use keyStore() to go on api testing
    @Test
    public void keyStore(){
        given()
                .keyStore("pathoffile","password")
                .when().get("myapi");

    }
}