package crawling;

import com.google.gson.Gson;
import okhttp3.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NetSuiteApi {

    private static final String CRAWL_DATA_SAVE_URL = "";
    private static final OkHttpClient CLIENT = new OkHttpClient();
    private static final Gson GSON = new Gson();
    private static final String NETSUITE_AUTH = "";


    public static boolean saveCrawlData(List<PropertyDetails> propertyDetails) {

        try {
            List<Map<String, String>> payload = propertyDetails.stream().map(propertyDetail -> {
                Map<String, String> saleObject = new HashMap<>();
                saleObject.put("street_name", propertyDetail.getStreetName());
                saleObject.put("street_number", propertyDetail.getStreetNumber());
                saleObject.put("suburb_name", propertyDetail.getSuburbName());
                saleObject.put("state", propertyDetail.getState());
                saleObject.put("postal_code", propertyDetail.getPostalCode());
                saleObject.put("price", propertyDetail.getPrice());
                saleObject.put("date", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                saleObject.put("property_crawl_site", propertyDetail.getSite());
                return saleObject;
            }).collect(Collectors.toList());

            MediaType mediaType = MediaType.parse("application/json; charset=UTF-8");
            String jsonString = GSON.toJson(Collections.singletonMap("details", payload));
            RequestBody body = RequestBody.create(mediaType, jsonString);

            Request request = new Request.Builder()
                    .url(CRAWL_DATA_SAVE_URL)
                    .post(body)
                    .addHeader("Content-Type", "application/json; charset=UTF-8")
                    .addHeader("Authorization", NETSUITE_AUTH)
                    .build();
            Response response = CLIENT.newCall(request).execute();

            return response.isSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
