package stepdefinitions.api;

import hooks.api.HooksAPI;
import io.cucumber.java.en.Given;
import org.json.JSONObject;

public class HeallifeAPIStepdefinition {

    public static String fullPath;

    JSONObject reqBodyJson;

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

}
