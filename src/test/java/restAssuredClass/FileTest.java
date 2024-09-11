package restAssuredClass;

import io.restassured.RestAssured;
import jdk.jfr.Description;
import org.hamcrest.Matchers;
import org.junit.Test;


import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class FileTest {
    @Description("Testes enviando sem arquivo")
    @Test
    public void deveObrigarEnvioArquivo(){
        given()
                .log().all()
                .when()
                    .post("https://restapi.wcaquino.me/upload")
                .then()
                    .log().all()
                    .statusCode(404)
                    .body("error", is("Arquivo não enviado"))

                ;
    }

    @Description("Testes enviando arquivo")
    @Test
    public void deveEnviarArquivo(){
        given()
                .log().all()
                .multiPart("arquivo", new File("src/main/resources/users.pdf"))
                .when()
                .post("https://restapi.wcaquino.me/upload")
                .then()
                .log().all()
                .statusCode(200)
                .body("name",is("users.pdf"))

        ;
    }
}
