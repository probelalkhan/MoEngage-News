package net.simplifiedcoding.moengagenews.data.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public final class HTTPRequestHandler {

    public static String get(String requestURL) throws ApiException {
        URL url;
        StringBuilder sb;
        try {
            url = new URL(requestURL);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
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
