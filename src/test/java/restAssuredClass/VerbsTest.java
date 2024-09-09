package restAssuredClass;

import io.restassured.RestAssured;
import jdk.jfr.Description;
import org.hamcrest.Matchers;
import org.junit.Test;
import restAssuredClass.Properties.PropertiesJson;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class VerbsTest extends PropertiesJson {

    @Description("Salvar usuário via POST")
    @Test
    public void deveSalvarUsuario(){
        given()
                .log().all()
                .contentType("application/json")
                .body("{\"name\": \"Jose\",\"age\": 50}")
            .when()
                .post("/users")
            .then()
                .log().all()
                .statusCode(201)
                .body("id", is(notNullValue()))
                .body("name",is("Jose"))
                .body("age",is(50))

        ;

    }
    @Description("Validação do salvar usuário via POST")
    @Test
    public void naoDeveSalvarUsuarioSemNome(){
        given()
                .log().all()
                .contentType("application/json")
                .body("{\"age\": 50}")
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(400)
                .body("id", is(nullValue()))
                .body("error",is("Name é um atributo obrigatório"))

        ;

    }
    @Description("Validação alterar usuário via PUT")
    @Test
    public void deveAlterarUsuarioRegistroPut(){
        given()
                .log().all()
                .contentType("application/json")
                .body("{\"name\": \"Usuario Alterado\",\"age\": 80}")
                .when()
                .put("/users/1")
                .then()
                .log().all()
               // .statusCode(400)
               // .body("id", is(nullValue()))
               // .body("error",is("Name é um atributo obrigatório"))

        ;

    }
}


