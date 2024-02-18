package de.expeditionengineer.app;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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
    private List<CurrencyRate> listOfCurrencyRatesObjs;
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
        String apiURL = setAPIURL(test);
        System.out.println("Doing a fetch request to the URL: " + apiURL);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiURL))
                .build();
        try {
            extractRatesFromJson(client, request);

        } catch (Exception e) {
            System.out.println("An error occured while fetching the exchange data. Maybe the API-Key is invalid?");
            return "error";
        }
        return "success";
    }

    private String setAPIURL(Boolean test) {
        if (test) {
            return "http://127.0.0.1:8000";
        }
        else {
            return "https://v6.exchangerate-api.com/v6/" + exchangeAPIKey + "/latest/EUR";
        }
    }

    private void extractRatesFromJson(HttpClient client, HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(response.body());
        JsonObject jsonobj = root.getAsJsonObject();
        JsonObject listOfCurrencyRatesWithCurrencyNames = jsonobj.getAsJsonObject("conversion_rates");
        listOfCurrencyRatesObjs = new ArrayList<>();

        for (String key: listOfCurrencyRatesWithCurrencyNames.keySet()) {
            listOfCurrencyRatesObjs.add(new CurrencyRate(key, listOfCurrencyRatesWithCurrencyNames.get(key).getAsDouble()));
        }
    }

    public double getRate(String currency) {
        for (CurrencyRate rateObj : listOfCurrencyRatesObjs) {
            if (rateObj.getCurrency().equals(currency)) {
                return rateObj.getRate();
            }
        }
        throw new IllegalArgumentException("Currency not found: " + currency);
    }
}
