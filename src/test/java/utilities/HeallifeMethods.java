package utilities;

import com.google.gson.JsonObject;
import hooks.api.HooksAPI;
import io.restassured.http.ContentType;
import io.restassured.internal.RestAssuredResponseOptionsGroovyImpl;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static stepdefinitions.api.HeallifeAPIStepdefinition.fullPath;

public class HeallifeMethods {

    public static Response postResponse (JsonObject reqBody,String fullPath){

        Response response = given()
                .spec(HooksAPI.spec)
                .contentType(ContentType.JSON)
                .header("Accept","application/json")
                .body(reqBody)
                .when()
                .post(fullPath);
        response.prettyPrint();

       return response;
    }
    public static Response getResponse (String fullPath){

        Response response = given()
                .spec(HooksAPI.spec)
                //.headers("Authorization","Bearer " + Authentication.generateToken(email, password))
                .headers("Authorization","Bearer " + HooksAPI.token)
                .contentType(ContentType.JSON)
                .header("Accept","application/json")
                .when()
                .get(fullPath);

        response.prettyPrint();

        return response;
    }

    public static void verifiesThatRecordHasIncludes(String id, String data) {
        Response response = given()
                .spec(HooksAPI.spec)
                //.headers("Authorization","Bearer " + Authentication.generateToken(email, password))
                .headers("Authorization", "Bearer " + HooksAPI.token)
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .when()
                .get(fullPath);
        JsonPath resJP = response.jsonPath();
        System.out.println(resJP.getList("lists"));
        List<Object> list = resJP.getList("lists");
        Object[] arrList = new Object[list.size()];
        arrList = list.toArray(arrList);
        System.out.println(Arrays.toString(arrList));
        //List<JsonObject> JsList=
        int index = 0;
        for (int a = 0; a < arrList.length; a++) {
            if (arrList[a].toString().contains(id)) {
                System.out.println("index no : " + a);
                index = a;
                break;
            }
        }


    }
}
