package utilities;

import com.google.gson.JsonObject;
import hooks.api.HooksAPI;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

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




}
