package restAssuredClass.Properties;

import io.restassured.RestAssured;
import jdk.jfr.Description;
import org.junit.BeforeClass;

import static io.restassured.RestAssured.*;

public class PropertiesJson {
    @Description("Setup da URL Json")
    @BeforeClass
   public static void baseUrl(){
        baseURI = "http://restapi.wcaquino.me";
        //port = 443;
        //basePath = "";

        requestSpecification = given().contentType("application/json");
   }
}