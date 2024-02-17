package de.expeditionengineer.app;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

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
    
    public void loadExchangeData() {
        String apiURL = "http://127.0.0.1:8000";
        System.out.println("Doing a fetch request to the URL: " + apiURL);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiURL))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
