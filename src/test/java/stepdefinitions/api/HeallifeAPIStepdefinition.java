package stepdefinitions.api;

import hooks.api.HooksAPI;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class HeallifeAPIStepdefinition {

    public static String fullPath;

    JSONObject reqBodyJson;
    int basariliStatusKod= 200;
    int basarisizStatusKod = 403;
    Response response ;


    @Given("Api kullanicisi {string} path parametreleri set eder")
    public void api_kullanicisi_path_parametreleri_set_eder(String rawPath) {
        String[] paths = rawPath.split("/");

        StringBuilder tempPath = new StringBuilder("/{");

        for (int i = 0; i < paths.length; i++) {

            String key = "pp" + i; //pp0 pp1 pp2 ...
            String value = paths[i].trim();

            HooksAPI.spec.pathParam(key,value);

            tempPath.append(key + "}/{");
        }

        tempPath.deleteCharAt(tempPath.lastIndexOf("{"));
        tempPath.deleteCharAt(tempPath.lastIndexOf("/"));

         System.out.println("tempPath : " + tempPath);

        fullPath = tempPath.toString();
    }

    @Then("Api kullanicisi staffList icin gonderdigi dogru get Request sonucunda donen status kodunun ikiyuz oldugunu dogrular")
    public void apiKullanicisiStaffListIcinGonderdigiDogruGetRequestSonucundaDonenStatusKodununIkiyuzOldugunuDogrular() {
    response = given()
                .spec(HooksAPI.spec)
                .headers("Authorization","Bearer " + HooksAPI.token)
                .contentType(ContentType.JSON)
                .header("Accept","application/json")
                .when().get(fullPath);
        response.prettyPrint();
        //HeallifeMethods.getResponse(fullPath);
        Assert.assertEquals(basariliStatusKod,response.getStatusCode());
    }

    @Then("Api kullanicisi staffList icin gonderdigi yanlis get Request sonucunda donen status kodunun dortyuzuc oldugunu dogrular")
    public void apiKullanicisiStaffListIcinGonderdigiYanlisGetRequestSonucundaDonenStatusKodununDortyuzucOldugunuDogrular() {
        //HeallifeMethods.getResponse(fullPath);
        response = given()
                .spec(HooksAPI.spec)
                .headers("Authorization","Bearer " + HooksAPI.token)
                .contentType(ContentType.JSON)
                .header("Accept","application/json")
                .when().get(fullPath);
        response.prettyPrint();
        Assert.assertEquals(basarisizStatusKod,response.getStatusCode());
    }
}
