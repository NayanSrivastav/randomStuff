package Rx;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;

public class RxSample {


    public static void main(String[] args) throws Throwable {
        String x = "";
        try (BufferedReader br = new BufferedReader(new FileReader("/Users/nayan/Documents/backend/testAF/src/main/resources/banks"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            x = sb.toString();
        }

        JSONArray array = new JSONArray(x);
        JSONArray newArr = new JSONArray();
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            JSONObject newObj = new JSONObject();
            newObj.put("id", obj.get("id"));
            newObj.put("name", obj.get("name"));
            newObj.put("logo", obj.get("logo"));

            newArr.put(newObj);
        }

        System.out.println(newArr.toString());
    }


}
