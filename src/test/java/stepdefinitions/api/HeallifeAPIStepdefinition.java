package stepdefinitions.api;

import hooks.api.HooksAPI;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;
import utilities.HeallifeMethods;

import java.util.List;

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

    @Then("Api kullanicisi {string} icin gonderdigi dogru get Request sonucunda donen status kodunun {int} oldugunu dogrular")
    public void apiKullanicisiIcinGonderdigiDogruGetRequestSonucundaDonenStatusKodununOldugunuDogrular(String arg0, int arg1) {
        response = given()
                .spec(HooksAPI.spec)
                .headers("Authorization","Bearer " + HooksAPI.token)
                .contentType(ContentType.JSON)
                .header("Accept","application/json")
                .when().get(fullPath);
        response.prettyPrint();
        //HeallifeMethods.getResponse(fullPath);
        Assert.assertEquals(basariliStatusKod,response.getStatusCode());
        JsonPath respJP = response.jsonPath();
        Assert.assertEquals("Success",respJP.get("message"));
    }

    @Then("Api kullanicisi {string} icin gonderdigi yanlis get Request sonucunda donen status kodunun {int} oldugunu dogrular")
    public void apiKullanicisiIcinGonderdigiYanlisGetRequestSonucundaDonenStatusKodununOldugunuDogrular(String arg0, int arg1) {
        response = given()
                .spec(HooksAPI.spec)
                .headers("Authorization","Bearer " + HooksAPI.token)
                .contentType(ContentType.JSON)
                .header("Accept","application/json")
                .when().get(fullPath);
        response.prettyPrint();
        //HeallifeMethods.getResponse(fullPath);
        Assert.assertEquals(basarisizStatusKod,response.getStatusCode());
        JsonPath respJP = response.jsonPath();
        Assert.assertEquals("failed",respJP.get("message"));
    }

    @Then("Response body icindeki list icerigi {string} olan kisinin isminin {string} soyisminin  {string} employe id'sinin {string} oldugunu dogrular")
    public void responseBodyIcindekiListIcerigiOlanKisininIsmininSoyismininEmployeIdSininOldugunuDogrular(String id, String name, String surname, String employee_id) {
        response = given()
                .spec(HooksAPI.spec)
                .headers("Authorization","Bearer " + HooksAPI.token)
                .contentType(ContentType.JSON)
                .header("Accept","application/json")
                .when().get(fullPath);
        JsonPath resJp = response.jsonPath();
        List<String> listIcerigi = resJp.getList("lists");
        //int index = listIcerigi.indexOf(id);
        for (int i = 0; i < listIcerigi.size(); i++) {
            if (id.equals(i)){
                Assert.assertEquals(name,resJp.getList("lists["+i+"].name"));
                Assert.assertEquals(surname,resJp.get("lists["+i+"].surname"));
                Assert.assertEquals(employee_id,resJp.get("lists["+i+"].employee_id"));
            }
        }
    }
}
