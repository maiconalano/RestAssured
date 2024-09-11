package restAssuredClass;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jdk.jfr.Description;
import org.junit.Test;
import restAssuredClass.Properties.PropertiesJson;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class HTML extends PropertiesJson {
    @Description("Fazer buscas com HTML")
    @Test
    public void deveFazerBuscasComHtml(){
        given()
                .log().all()
                .when()
                .get("/v2/users")
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.HTML)
                .body("html.body.div.table.tbody.tr.size()", is(3))
        ;


    }
}
