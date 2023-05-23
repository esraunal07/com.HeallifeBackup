package utilities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Authentication {

    private static RequestSpecification spec;

    public static String generateToken(String email,String password){

        spec = new RequestSpecBuilder().setBaseUri(ConfigReader.getProperty("base_url")).build();

        spec.pathParams("pp1","api","pp2","getToken");

        Map<String,Object> dataCredential = new HashMap<>();

        dataCredential.put("email",email);
        dataCredential.put("password",password);

        Response response = given()
                .spec(spec)
                .contentType(ContentType.JSON).header("Accept","application/json")
                .body(dataCredential)
                .when()
                .post("/{pp1}/{pp2}");

        JsonPath respJP = response.jsonPath();

        String token = respJP.getString("token");
        System.out.println(token);

        return token;
    }

}
