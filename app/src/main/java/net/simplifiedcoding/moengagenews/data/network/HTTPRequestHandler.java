package net.simplifiedcoding.moengagenews.data.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public final class HTTPRequestHandler {

    private static final String BASE_URL = "https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/";

    public static String get(String endPoint) throws ApiException {
        URL url;
        StringBuilder sb;
        try {
            url = new URL(BASE_URL + endPoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                sb = new StringBuilder();
                String response;
                while ((response = br.readLine()) != null) {
                    sb.append(response);
                }
            } else {
                throw new ApiException("Response Code: " + responseCode);
            }
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
        return sb.toString();
    }
}
