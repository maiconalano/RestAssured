package restAssuredClass;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jdk.jfr.Description;
import org.hamcrest.Matchers;
import org.junit.Test;
import restAssuredClass.Properties.PropertiesJson;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class EnvioDadosTest extends PropertiesJson {

    @Description("Enviando valor via Query")
    @Test
    public void deveEnviarValorViaQuery(){
        given()
                .log().all()
                .when()
                .get("/v2/users?format=json")
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                ;
    }

    @Description("Enviando valor via QueryParam")
    @Test
    public void deveEnviarValorViaQueryParam(){
        given()
                .log().all()
                .queryParam("format","xml")
                .when()
                .get("/v2/users")
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.XML)
                .contentType(containsString("utf-8"))
        ;
    }

    @Description("Enviando valor via Header")
    @Test
    public void deveEnviarValorViaHeader(){
        given()
                .log().all()
                .accept(ContentType.JSON)
                .when()
                    .get("/v2/users")
                .then()
                    .log().all()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
        ;
    }
}
