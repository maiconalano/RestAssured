package restAssuredClass;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class UserJsonTest {

    @Description("Teste de primeiro nível")
    @Test
    public void deveVerificarPrimeiroNivel(){
        given()
                .when()
                .get("http://restapi.wcaquino.me/users/1")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("name", containsString("Silva"))
                .body("age", greaterThan(18));

    }
    @Description("Teste de verificação primeiro nivel de outras formas")
    @Test
    public void deveValidarOutrasFormas(){
        Response response = RestAssured.request(Method.GET,"http://restapi.wcaquino.me/users/1");

        assertEquals("1",response.path("id").toString());

        JsonPath jpath = new JsonPath(response.asString());

        assertEquals(1, jpath.getInt("id"));



    }

    @Description("Teste do segundo nível")
    @Test
    public void deveValidarSegundoNivel(){
        given()
                .when()
                .get("http://restapi.wcaquino.me/users/2")
                .then()
                .statusCode(200)
                .body("name", containsString("Joaquina"))
                .body("endereco.rua", is("Rua dos bobos"));

    }

    @Description("Teste de lista nível")
    @Test
    public void deveValidarLista(){
        given()
                .when()
                .get("http://restapi.wcaquino.me/users/3")
                .then()
                .statusCode(200)
                .body("name", containsString("Ana"))
                .body("filhos",hasSize(2))
                .body("filhos[0].name",is("Zezinho"))
                .body("filhos[1].name",is("Luizinho"))
                .body("filhos.name",hasSize(2))
                .body("filhos.name",hasItem("Zezinho"))
                ;

    }

    @Description("Teste de lista nível")
    @Test
    public void deveValidarIdInexistente(){
        given()
                .when()
                .get("http://restapi.wcaquino.me/users/4")
                .then()
                .statusCode(404)
                .body("error",equalTo("Usuário inexistente"))
        ;

    }

    @Description("Teste de lista total")
    @Test
    public void deveValidarListaDeTodosUsers(){
        given()
                .when()
                .get("http://restapi.wcaquino.me/users")
                .then()
                .statusCode(200)
                .body("",hasSize(3))
                .body("name",hasItems("João da Silva", "Maria Joaquina"))
                .body("age[1]",is(25))
                .body("endereco[1].rua", is("Rua dos bobos"))
                .body("filhos.name.flatten()", hasItem("Zezinho"))
                .body("salary", contains(1234.5678f,2500,null))
        ;

    }

    @Description("Deve fazer verificação avançadas")
    @Test
    public void deveValidarAvancadas(){
        given()
                .when()
                .get("http://restapi.wcaquino.me/users")
                .then()
                .statusCode(200)
                .body("",hasSize(3))
                .body("age.findAll{it <= 25}.size()", is(2))
                .body("age.findAll{it <= 25 && it > 20}.size()", is(1))
                .body("findAll{it.age <= 25 && it.age > 20}.name", hasItem("Maria Joaquina"))
                .body("findAll{it.age <= 25}[-1].name", is("Ana Júlia"))
                .body("find{it.age <= 25}.name", is("Maria Joaquina"))
        ;

    }

}
