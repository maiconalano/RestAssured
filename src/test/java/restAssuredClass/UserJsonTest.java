package restAssuredClass;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.BeforeClass;
import org.junit.Test;
import restAssuredClass.Properties.PropertiesJson;

import java.util.ArrayList;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class UserJsonTest extends PropertiesJson {

    @Description("Teste de primeiro nível")
    @Test
    public void deveVerificarPrimeiroNivel(){


        given()
                .when()
                .get("/users/1")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("name", containsString("Silva"))
                .body("age", greaterThan(18));

    }
    @Description("Teste de verificação primeiro nivel de outras formas")
    @Test
    public void deveValidarOutrasFormas(){
        Response response = request(Method.GET,"http://restapi.wcaquino.me/users/1");

        assertEquals("1",response.path("id").toString());

        JsonPath jpath = new JsonPath(response.asString());

        assertEquals(1, jpath.getInt("id"));



    }

    @Description("Teste do segundo nível")
    @Test
    public void deveValidarSegundoNivel(){
        given()
                .when()
                .get("/users/2")
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
                .get("/users/3")
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
                .get("/users/4")
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
                .get("/users")
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
                .get("/users")
                .then()
                .statusCode(200)
                .body("",hasSize(3))
                .body("age.findAll{it <= 25}.size()", is(2))
                .body("age.findAll{it <= 25 && it > 20}.size()", is(1))
                .body("findAll{it.age <= 25 && it.age > 20}.name", hasItem("Maria Joaquina"))
                .body("findAll{it.age <= 25}[-1].name", is("Ana Júlia"))
                .body("find{it.age <= 25}.name", is("Maria Joaquina"))
                .body("findAll{it.name.contains('n')}.name",hasItems("Maria Joaquina","Ana Júlia") )
                .body("name.collect{it.toUpperCase()}", hasItem("MARIA JOAQUINA"))
                .body("name.findAll{it.startsWith('Maria')}.collect{it.toUpperCase()}.toArray()", allOf(arrayContaining("MARIA JOAQUINA"), arrayWithSize(1)))
                .body("age.collect{it*2}", hasItems(60,50,40))
                .body("id.max()", is(3))
                .body("salary.min()", is(1234.5678f))
                .body("salary.findAll{it != null}.sum()",is(closeTo(3734.5678f, 0.001)))
                .body("salary.findAll{it != null}.sum()", allOf(greaterThan(3000d), lessThan(5000d)))
        ;

    }

    @Description("Utilizando o teste acima JsonPath para JAVA")
    @Test
    public void devoUnirJsonPathComJava(){
        ArrayList<String> names =
        given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract().path("name.findAll{it.startsWith('Maria')}")
                ;
        assertEquals(1,names.size());
        assertTrue(names.get(0).equalsIgnoreCase("maria joaquina"));
        assertEquals(names.get(0).toUpperCase(),"maria joaquina".toUpperCase());



    }
}
