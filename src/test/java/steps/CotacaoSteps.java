package steps;

import constants.Constants;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.TestBase;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class CotacaoSteps extends TestBase {

    Constants constants = new Constants();
    private static String id_criado;
    private static String image_id;
    private Response response;
    public Response responseGet;

    @Dado("que eu crio uma nova cotação")
    public void criarNovaCotacao() {
        RestAssured.baseURI = constants.baseurl;
        File jsonFile = new File("src/test/resources/payloads/postCriarCotacao.json");
        response = given()
                .header("Content-Type", constants.contentType)
                .header("x-api-key", constants.apiKey)
                .contentType("application/json")
                .body(jsonFile)
                .when()
                .post("/votes")
                .then()
                .extract()
                .response();

        id_criado = response.jsonPath().getString("id");
        image_id = response.jsonPath().getString("message");
    }

    @Então("o status code deve ser {int}")
    public void validarStatusCode(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }

    @Então("eu extraio o id_cotacao e situacao_cotacao")
    public void extrairCampos() {
        id_criado = response.jsonPath().getString("id");
        System.out.println("Id Criado é : " + id_criado);
    }

    @E("que eu consulto a cotação criada")
    public void consultarCotacaoCriada() {
        RestAssured.baseURI = "https://api.thecatapi.com/v1/";
        responseGet = given()
                .header("Content-Type", constants.contentType)
                .header("x-api-key", constants.apiKey)
                .when()
                .get("/votes/" + id_criado)
                .then()
                .extract()
                .response();
    }

    @Então("o status code deve ser de {int}")
    public void validarStatusCodeGet(int statusCode) {
        image_id = response.jsonPath().getString("image_id");
        assertEquals(statusCode, responseGet.getStatusCode());
        assertEquals("teste",image_id);
        System.out.println("Image ID é " + image_id);
    }

    @Então("a situacao_cotacao deve ser {string}")
    public void validarSituacaoCotacao(String situacaoEsperada) {
        int i = 0;
            while (i<=2 && !responseGet.jsonPath().getString("image_id").equals(situacaoEsperada)) {
                try {
                    Thread.sleep(2000); // Aguarda 2 segundos antes de tentar novamente
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                consultarCotacaoCriada();
                i=i+1;
            }
            assertEquals(situacaoEsperada,responseGet.jsonPath().getString("image_id"));
    }
}