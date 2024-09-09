package restAssuredClass;

import io.restassured.RestAssured;
import jdk.jfr.Description;
import org.hamcrest.Matchers;
import org.junit.Test;
import restAssuredClass.Properties.PropertiesJson;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class VerbsTest extends PropertiesJson {

    @Description("Salvar usuário via POST")
    @Test
    public void deveSalvarUsuario(){
        given()
                .log().all()
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
                .body("{\"name\": \"Usuario Alterado\",\"age\": 80}")
                .when()
                .put("/users/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(1))
                .body("name",is("Usuario Alterado"))
                .body("age",is(80))
                .body("salary",is(1234.5678f))

        ;

    }
    @Description("Validação do metodo DELETE")
    @Test
    public void deveDeletarRegistro(){
        given()
                .log().all()
                .when()
                .delete("/users/1")
                .then()
                .log().all()
                .statusCode(204)
        ;

    }


    @Description("Validação do metodo DELETE usuario inexistente")
    @Test
    public void naoDeveDeletarRegistroInexistente(){
        given()
                .log().all()
                .when()
                .delete("/users/4")
                .then()
                .log().all()
                .statusCode(400)
                .body("error",is("Registro inexistente"))
        ;

    }
    @Description("Salvar usuário usando MAP via POST")
    @Test
    public void deveSalvarUtilizandoMap(){
        Map <String, Object> params = new HashMap<String, Object>();
        params.put("name","Usuario via map");
        params.put("age",25);

        given()
                .log().all()
                .body(params)
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("id", is(notNullValue()))
                .body("name",is("Usuario via map"))
                .body("age",is(25))

        ;

    }
}


