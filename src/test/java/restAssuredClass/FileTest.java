package restAssuredClass;

import jdk.jfr.Description;
import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;



import java.io.*;

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
    @Description("Testes enviando arquivo maior do que suportado pela API")
    @Test
    public void naoDeveEnviarArquivo(){
        given()
                .log().all()
                .multiPart("arquivo", new File("src/main/resources/Planilha.xlsm"))
                .when()
                .post("https://restapi.wcaquino.me/upload")
                .then()
                .log().all()
                .time(lessThan(2000L))
                .statusCode(413)

        ;
    }

    @Description("Testes download de aquivo")
    @Test
    public void deveFazerDownload() throws IOException {
        byte[] image = given()
                .log().all()
                .when()
                .get("https://restapi.wcaquino.me/download")
                .then()
                .statusCode(200)
                .extract().asByteArray();
        File imagem = new File("src/main/resources/file.jpeg");
        OutputStream out = new FileOutputStream(imagem);
        out.write(image);
        out.close();

        System.out.println(imagem.length());
        assertThat(imagem.length(), lessThan(100000L));

        ;
    }
}
