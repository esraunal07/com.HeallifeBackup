package stepdefinitions.api;

import hooks.api.HooksAPI;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Assert;
import utilities.HeallifeMethods;

import java.util.List;
import java.util.regex.Matcher;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class HeallifeAPIStepdefinition {

    public static String fullPath;


    JSONObject reqBodyJson;
    int basariliStatusKod= 200;
    int basarisizStatusKod = 403;
    public String exception;
    String basarisizExceptionStatusKod ="403";
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
        assertEquals(basariliStatusKod,response.getStatusCode());
        JsonPath respJP = response.jsonPath();
        assertEquals("Success",respJP.get("message"));
    }

    @Then("Api kullanicisi {string} icin gonderdigi yanlis get Request sonucunda donen status kodunun {int} oldugunu dogrular")
    public void apiKullanicisiIcinGonderdigiYanlisGetRequestSonucundaDonenStatusKodununOldugunuDogrular(String arg0, int arg1) {
        try {
            response = given()
                    .spec(HooksAPI.spec)
                    .headers("Authorization","Bearer" + HooksAPI.token)
                    .contentType(ContentType.JSON)
                    .header("Accept","application/json")
                    .when().get(fullPath);
            response.prettyPrint();
        } catch (Exception e) {
            exception = e.getMessage();
        }

        System.out.println(exception);
        Assert.assertTrue(exception.contains(basarisizExceptionStatusKod));
        Assert.assertTrue(exception.contains("Forbidden"));
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
                assertEquals(name,resJp.getList("lists["+i+"].name"));
                assertEquals(surname,resJp.get("lists["+i+"].surname"));
                assertEquals(employee_id,resJp.get("lists["+i+"].employee_id"));
            }
        }
    }




    @Then("Api kullanicisi visitors_purpose, description bilgileriyle yeni bir visitor purpose kaydi olusturur")
    public void api_kullanicisi_visitors_purpose_description_bilgileriyle_yeni_bir_visitor_purpose_kaydi_olusturur() {
        reqBodyJson= new JSONObject();
        reqBodyJson.put("visitors_purpose","kalbi kirilmis");
        reqBodyJson.put("description","yapilan tetkikler neticesinde hastanin kalbinin kirildigi anlasılmıstır");

        response = given()
                .spec(HooksAPI.spec)
                .headers("Authorization","Bearer "+HooksAPI.token)
                .contentType(ContentType.JSON)
                .when()
                .body(reqBodyJson.toString())
                .post(fullPath);

       /* JsonPath resJPath= response.jsonPath();
        System.out.println(resJPath.get("lists.id").toString());
*/

    }
    @Then("Api kullanicisi visitorsPurposeAdd donen status code in {int} oldugu ve response body deki message bilgisinin Success oldugu dogrulanmali")
    public void api_kullanicisi_visitorsPurposeAdd_donen_status_code_in_oldugu_ve_response_body_deki_message_bilgisinin_success_oldugu_dogrulanmali(Integer int1) {
        response.then().assertThat().statusCode(basariliStatusKod).body("message", Matchers.equalTo("Success"));
    }

    @Then("Api kullanicisi visitorsPurposeAdd gecersiz authorization bilgileri ile gecerli data gonderir ve donen status code in {int} oldugu ve response body deki message bilgisinin Failed oldugu dogrulanmali")
    public void api_kullanicisi_visitorsPurposeAdd_gecersiz_authorization_bilgileri_ile_gecerli_data_gonderir_ve_donen_status_code_in_oldugu_ve_response_body_deki_message_bilgisinin_failed_oldugu_dogrulanmali(Integer int1) {
        reqBodyJson= new JSONObject();
        reqBodyJson.put("visitors_purpose","kalbi kirilmis");
        reqBodyJson.put("description","yapilan tetkikler neticesinde hastanin kalbinin kirildigi anlasılmıstır");

        response = given()
                .spec(HooksAPI.spec)
                .contentType(ContentType.JSON)
                .when()
                .body(reqBodyJson.toString())
                .post(fullPath);

        response.then().assertThat().statusCode(basarisizStatusKod).body("message", Matchers.equalTo("failed"));

    }
    @Then("Api kullanicisi visitorsPurposeAdd gecerli authorization bilgileri ile gecersiz data gonderir ve donen status code in {int} oldugu ve response body deki message bilgisinin Failed oldugu dogrulanmali")
    public void api_kullanicisi_visitorsPurposeAdd_gecerli_authorization_bilgileri_ile_gecersiz_data_gonderir_ve_donen_status_code_in_oldugu_ve_response_body_deki_message_bilgisinin_failed_oldugu_dogrulanmali(Integer int1) {
        reqBodyJson= new JSONObject();
        reqBodyJson.put("visitors","kalbi kirilmis");
        reqBodyJson.put("kalp","yapilan tetkikler neticesinde hastanin kalbinin kirildigi anlasılmıstır");

        response = given()
                .spec(HooksAPI.spec)
                .headers("Authorization","Bearer "+HooksAPI.token)
                .contentType(ContentType.JSON)
                .when()
                .body(reqBodyJson.toString())
                .post(fullPath);

        response.then().assertThat().statusCode(basarisizStatusKod).body("message", Matchers.equalTo("failed"));

    }

    @Then("Api kullanicisi visitorsPurposeList datalari ceker")
    public void api_kullanicisi_visitors_purpose_list_datalari_ceker() {
        response =  given()
                .spec(HooksAPI.spec)
                .headers("Authorization", "Bearer " + HooksAPI.token)
                .contentType(ContentType.JSON)
                .when()
                .get(fullPath);

    }
    @Then("Api kullanicisi ekledigi purpose un visitorsPurposeList te oldugunu dogrular")
    public void api_kullanicisi_ekledigi_purpose_un_visitors_purpose_list_te_oldugunu_dogrular() {
        JsonPath resJp = response.jsonPath();

        assertEquals("kalbi kirilmis", resJp.get("lists[54].visitors_purpose") );
    }


    // -------------------------TC_01 _ US_15 ------------------------------------
    @Then("Api kullanicisi {string} icin gonderdigi dogru id iceren bir GET body sonucunda dönen status code in ikiyuz oldugunu dogrular ve response body deki message bilgisinin Success oldugu dogrulanmali")
    public void apiKullanicisiIcinGonderdigiDogruIdIcerenBirGETBodySonucundaDönenStatusCodeInIkiyuzOldugunuDogrularVeResponseBodyDekiMessageBilgisininSuccessOlduguDogrulanmali(String arg0) {

        reqBodyJson = new JSONObject();
        reqBodyJson.put("id", "4");
        response = given()
                .spec(HooksAPI.spec)
                .headers("Authorization", "Bearer " + HooksAPI.token)
                .contentType(ContentType.JSON)
                .when()
                .body(reqBodyJson.toString()).get(fullPath);
        response.prettyPrint();
        assertEquals(basariliStatusKod, response.getStatusCode());

        JsonPath respJP = response.jsonPath();
        assertEquals("Success", respJP.get("message"));

    }

    // -------------------------TC_02 _ US_15 ------------------------------------
    @Then("Api kullanicisi {string} icin gonderdigi gecersiz id iceren bir GET body gönderildiginde dönen status codein {int} oldugu ve response bodydeki message bilgisinin {string} oldugu dogrulanmali")
    public void apiKullanicisiIcinGonderdigiGecersizIdIcerenBirGETBodyGönderildigindeDönenStatusCodeinOlduguVeResponseBodydekiMessageBilgisininOlduguDogrulanmali(String arg0, int arg1, String arg2) {
        reqBodyJson = new JSONObject();
        reqBodyJson.put("id", "25");
        response = given()
                .spec(HooksAPI.spec)
                .headers("Authorization", "Bearer " + HooksAPI.token)
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .body(reqBodyJson.toString())
                .when().get(fullPath);
        response.prettyPrint();

        org.testng.Assert.assertEquals(basarisizStatusKod, response.getStatusCode());
        JsonPath respJP = response.jsonPath();
        org.testng.Assert.assertEquals("failed", respJP.get("message"));
    }

    // -------------------------TC_03 _ US_15 ------------------------------------

    @Then("Api kullanicisi response body icindeki {string} {string} {string} {string} datalarini dogrular")
    public void apiKullanicisiResponseBodyIcindekiDatalariniDogrular(String id, String name, String is_blood_group, String created_at) {

        JSONObject reqBodyJson = new JSONObject();
        reqBodyJson.put("id","4");
        response = given()
                .spec(HooksAPI.spec)
                .contentType(ContentType.JSON)
                .header("Authorization","Bearer " + HooksAPI.token)
                .when()
                .body(reqBodyJson.toString())
                .get(fullPath);
        response.prettyPrint();

        JsonPath respJP = response.jsonPath();
        assertEquals(respJP.get("lists.id"),id);
        assertEquals(respJP.get("lists.name"),name);
        assertEquals(respJP.get("lists.is_blood_group"),is_blood_group);
        assertEquals(respJP.get("lists.created_at"),created_at);



    }

    @Then("Response body icindeki id, visitors_purpose, description, created_at dogrula")
    public void response_body_icindeki_id_visitors_purpose_description_created_at_dogrula() {

        JSONObject reqBodyJson = new JSONObject();
        reqBodyJson.put("id","4");
        response = given()
                .spec(HooksAPI.spec)
                .contentType(ContentType.JSON)
                .header("Authorization","Bearer " + HooksAPI.token)
                .when()
                .body(reqBodyJson.toString())
                .get(fullPath);
        response.prettyPrint();

        JsonPath respJP = response.jsonPath();
        assertEquals(respJP.get("lists.id"),"4");
        assertEquals(respJP.get("lists.visitors_purpose"),"Visit");
        assertEquals(respJP.get("lists.description"),"Visitor centers used to provide fairly basic information about the place, corporation or event they are celebrating, acting more as the entry way to a place. The role of the visitor center has been rapidly evolving over the past 10 years to become more of an experience and to tell the story of the place or brand it represents. Many have become destinations and experiences in their own right.");
        assertEquals(respJP.get("lists.created_at"),"2021-10-29 01:25:09");
    }


    //////////////Esra US_01///////////////////////////////////
    @Then("Api kullanicisi id'si {int} olan kaydin patient_name: {string}, expected datasi hazirlanir")
    public void apiKullanicisiIdSiOlanKaydinPatient_nameExpectedDatasiHazirlanir(int id, String name) {
        /*{
        "id": "1",
                "patient_name": "John Smith",
                "patient_id": "6"
    }
 */
        JSONObject expecteddataJSon = new JSONObject();
        expecteddataJSon.put("id","id");
        expecteddataJSon.put("patient_name",name);

        response = given()
                .spec(HooksAPI.spec)
                .headers("Authorization","Bearer "+HooksAPI.token)
                .contentType(ContentType.JSON)
                .when()
                .body(expecteddataJSon.toString())
                .get(fullPath);
        response.prettyPrint() ;
    }

    @Then("Api kullanicisi Response body List gormek icin Get request gonderir")
    public void apiKullanicisiResponseBodyListGormekIcinGetRequestGonderir() {
        response =  given()
                .spec(HooksAPI.spec)
                .headers("Authorization", "Bearer " + HooksAPI.token)
                .contentType(ContentType.JSON)
                .when()
                .get(fullPath);
        response.prettyPrint();
    }
    @Then("Api kullanicisi  donen response body icindeki id'si {string} olan kaydin patient_name: {string}  oldugunu dogrular")
    public void apiKullanicisiDonenResponseBodyIcindekiIdSiOlanKaydinPatient_nameOldugunuDogrular(String arg0, String arg1) {
        JsonPath resJp = response.jsonPath();
        assertEquals(arg0, resJp.get("lists[0].id") );
        assertEquals(arg1, resJp.get("lists[0].patient_name") );
    }

    @And("Api kullanicisi donen response bodysinin id'si {string} olanin patient_id: {string} oldugu dogrular")
    public void apiKullanicisiDonenResponseBodysininIdSiOlaninPatient_idOlduguDogrular(String arg0, String arg1) {
        JsonPath resJp = response.jsonPath();
        assertEquals(arg0, resJp.get("lists[10].id") );
        assertEquals(arg1, resJp.get("lists[10].patient_id") );
    }
}


