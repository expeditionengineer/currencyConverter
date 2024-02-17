package de.expeditionengineer.app;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;


/**
 * Hello world!
 *
 */
public class CurrencyConverter 
{
    private String exchangeAPIKey;
    public CurrencyConverter() {

        Properties prop = new Properties();
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(".env");
            prop.load(fileInputStream); 
            exchangeAPIKey = prop.getProperty("API-Key");
        } catch (IOException e) {
            System.out.println("API-Key couldnt be loaded from .env-file. Is a .env-file present in the current directory?");
        }
    }
    
    public String loadExchangeData(Boolean test) {
        String apiURL = "";
        if (test) {
            apiURL = "http://127.0.0.1:8000";
        }
        else {
            apiURL = "https://v6.exchangerate-api.com/v6/" + exchangeAPIKey + "/latest/EUR";
        }
        System.out.println("Doing a fetch request to the URL: " + apiURL);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiURL))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(response.body());
            JsonObject jsonobj = root.getAsJsonObject();
            return jsonobj.get("result").getAsString();

        } catch (IOException | InterruptedException e) {
            System.out.println("An error occured while fetching the exchange data. Maybe the API-Key is invalid?");
            return "error";
        }
    }

}
