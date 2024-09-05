package restAssuredClass;
import io.restassured.path.xml.element.Node;
import jdk.jfr.Description;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UsersXMLTest {

    @Description("Realizando testes com XML")
    @Test
    public void devoTrabalharComXml(){
        given()
                .when()
                .get("http://restapi.wcaquino.me/usersXML/3")
                .then()
                .statusCode(200)
                .rootPath("user")
                .body("name", is("Ana Julia"))
                .body("@id",is("3"))
                .rootPath("user.filhos")
                .body("name.size()", is(2))
                .detachRootPath("filhos")
                .body("filhos.name[0]", is("Zezinho"))
                .body("filhos.name[1]", is("Luizinho"))
                .appendRootPath("filhos")
                .body("name", hasItem("Luizinho"))
                .body("name", hasItems("Zezinho","Luizinho"))
                ;

    }

    @Description("Realizando testes avançados com XML")
    @Test
    public void devoTrabalharComXMLAvancado(){
        given()
                .when()
                .get("http://restapi.wcaquino.me/usersXML")
                .then()
                .statusCode(200)
                .rootPath("user.user")
                .body("size()", is(3))
                .body("findAll{it.age.toInteger() <= 25}.size()",is(2))
                .body("@id",hasItems("1","2","3"))
                .body("find{it.age.toInteger() == 25}.name",is("Maria Joaquina"))
                .body("findAll{it.name.toString().contains('n')}.name",hasItems("Maria Joaquina","Ana Julia"))
                .body("salary.find{it != null}.toDouble()",is(1234.5678d))
                .body("age.collect{it.toInteger()*2}",hasItems(40,50,60))
                .body("name.findAll{it.toString().startsWith('Maria')}.collect{it.toString().toUpperCase()}",is("Maria Joaquina".toUpperCase()))
        ;

    }

    @Description("Realizando testes avançados com XML mesclando XPath com Java")
    @Test
    public void devoTrabalharComXMLAvancadoComJava(){
        ArrayList <Node> nomes = given()
                .when()
                .get("http://restapi.wcaquino.me/usersXML")
                .then()
                .statusCode(200)
                .extract().path("users.user.name.findAll{it.toString().contains('n')}");

        Assert.assertEquals(2, nomes.size());
        Assert.assertEquals("Maria Joaquina".toUpperCase(),nomes.get(0).toString().toUpperCase());
        Assert.assertTrue("Ana Julia".equalsIgnoreCase(nomes.get(1).toString()));

    }


}
